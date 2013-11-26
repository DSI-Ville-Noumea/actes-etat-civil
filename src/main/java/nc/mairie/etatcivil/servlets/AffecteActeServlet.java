package nc.mairie.etatcivil.servlets;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import nc.mairie.etatcivil.Acte;
import nc.mairie.technique.FormateListe;
import nc.mairie.technique.Transaction;
import nc.mairie.technique.Services;

import org.apache.commons.io.FileUtils;

/**
 * Insérez la description du type ici.
 * Date de création : (04/03/2004 13:01:47)
 * @author: Administrator
 */
public class AffecteActeServlet extends SuperServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8430389319227187504L;
	private String voirPDFUrl = null;
	
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 16:19:54)
 * @return java.lang.String
 */
private java.lang.String getVoirPDFUrl(javax.servlet.http.HttpServletRequest request) {
	if (voirPDFUrl == null) {
		voirPDFUrl = request.getScheme()+"://"+
					request.getServerName()+
					(request.getServerPort() == 80 ? "" : ":"+request.getServerPort())+
					request.getContextPath()+"/"+getClass().getName().substring(getClass().getName().lastIndexOf(".")+1)+"?VOIRPDF=1";
	}
	return voirPDFUrl;
}
/**
 * Cette méthode a été importée à partir d'un fichier .class.
 * Aucun code source disponible.
 */
public void init() throws javax.servlet.ServletException {
	// insert code to initialize the servlet here
	initialiseParametreInitiaux();

	//init des user habilités
	initialiseListeUserHabilites();
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:53:56)
 */
private void initialiseActes(javax.servlet.http.HttpServletRequest request, AffecteActeBean bean) throws Exception{

	Transaction t = new Transaction(getUserAppli(request));

	java.sql.Connection conn = t.getConnection();
	java.sql.Statement st = conn.createStatement();
	java.sql.ResultSet rs = st.executeQuery("select * from mairie.dcecsc01 order by MAIRIE,CETAT,STATUT,TYPACT,ANNACT,NUMACT");
	
	Hashtable<String, FormateListe> h = bean.getHashActes();

	ArrayList<String> array = new ArrayList<String>();

	//"  18     01     P     1   2001 00005"
	int [] tailles = {8,7,5,3,5};
	nc.mairie.technique.FormateListe listeIndice = new nc.mairie.technique.FormateListe(tailles);
	
	HashSet<String> hSet = new HashSet<String>();
	
	array.add("");
	String [] lignevide = {"","","","","",""};
	listeIndice.ajouteLigne(lignevide);

	while (rs.next()){
		String mairie = rs.getString("MAIRIE");
		String etab = rs.getString("CETAT");
		String statut = rs.getString("STATUT");
		String type = rs.getString("TYPACT");
		String annee = rs.getString("ANNACT");
		String num = rs.getString("NUMACT");
		
		Acte acte = new Acte(mairie,
					etab,
					statut,
					type,
					annee,
					num);

		String key = acte.getNomFichierSansNumSansExt();
	
		//Ca c pour la liste des num
		FormateListe fl = (FormateListe)h.get(key);
		if (fl == null) {
			int [] tFl = {5};
			fl = new FormateListe(tFl);
			String [] lfVide = {""};
			fl.ajouteLigne(lfVide);
		}
		String ligneFl []= {Services.lpad(num,5,"0")};
		fl.ajouteLigne(ligneFl);
		h.put(key, fl);

		//Liste des familles d'acte
		if (! hSet.contains(key)) {
			hSet.add(key);
			array.add(key);
			String [] ligne = {Services.lpad(mairie,4," "),Services.lpad(etab,2,"0"),statut,type,annee};
			listeIndice.ajouteLigne(ligne);
		}
		
	}
	
	
	rs.close();
	st.close();
	conn.commit();
	conn.close();	

	bean.setLB_ACTES(listeIndice.getListeFormatee());
	bean.setLB_INDICE_ACTES((String [])array.toArray(new String[0]));
	bean.setLB_ACTES_NUM(null);


}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:53:56)
 */
private AffecteActeBean initialiseBean(javax.servlet.http.HttpServletRequest request) throws Exception {
	AffecteActeBean aBean = new AffecteActeBean(getVoirPDFUrl(request));

	//init des images et des actes
	initialiseImages(aBean);
	initialiseActes(request, aBean);

	//on met dans la session	
	request.getSession().setAttribute("affecteActeBean",aBean);

	return aBean;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:53:56)
 */
private void initialiseImages(AffecteActeBean bean) {

	String repOrg = (String)getMesParametres().get("REP_PDF");

	File [] files = new File(repOrg).listFiles();
		
	ArrayList<String> liste = new ArrayList<String>();
	liste.add("");
	for (int i = 0; i < files.length; i++){
		liste.add(files[i].getName());
	}
	java.util.Collections.sort(liste);
	bean.setLB_IMAGES((String [])liste.toArray(new String[0]));

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
private boolean performAffecterImage(javax.servlet.http.HttpServletRequest request, String acteName) throws Exception {

	String repOrg = (String)getMesParametres().get("REP_PDF");
	String repDest = (String)getMesParametres().get("REP_ECETATCIVIL");

	AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");

	String imageName = request.getParameter("LESIMAGES");

	//on prend l'acte associé à l'extension
	Acte acte = Acte.parse(acteName);

	//Fichier org
	File fichierOrg = new File(repOrg+"/"+imageName);

	//On teste la taille du fichier
	int tailleMax = Integer.parseInt((String)getMesParametres().get("SIZE_MAXI_PDF"));
	if (fichierOrg.length()/1000 > tailleMax) {
		bean.setMessageErreur("Le fichier "+fichierOrg.getName()+" est trop volumineux. Veuillez le scanner à nouveau.");
		return false;
	}
	
	//on teste si le dossier existe sinon on le crée
	File dossierDest = new File(repDest+"/"+acte.getNomDossier());
	if (! dossierDest.exists()) {
		if (!dossierDest.mkdirs()) {
			bean.setMessageErreur("Imposible de créer le dossier : "+dossierDest);
			return false;
		};
	}

	//On crée le fichier dest
	File fichierDest = new File(dossierDest.getPath()+"/"+acte.getNomFichier());

	//S'il existe, on le vire
	if (fichierDest.exists()) {
		if (! fichierDest.delete()) {
			bean.setMessageErreur("Imposible de supprimer le fichier qui existe déjà : "+fichierDest.getName());
			return false;
		}
	}

	//On copie le fichier
	FileUtils.copyFile(fichierOrg, fichierDest);
	
	//on teste que la copie est OK
	if (!fichierDest.exists()) {
		bean.setMessageErreur("Imposible de copier le fichier "+fichierOrg+" vers "+fichierDest);
		return false;
	}
	
	//on supprime le fichier org
	if (!fichierOrg.delete()) {
		bean.setMessageErreur("Imposible de supprimer le fichier "+fichierOrg);
		return false;
	}
	
//	//On renomme le fichier pour faire la copie
//	if (! fichierOrg.renameTo(fichierDest)) {
//		bean.setMessageErreur("Imposible de renommer le fichier (occupé ou innacessible) "+imageName+" en "+fichierDest.getName());
//		return false;
//	}
	return true;
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_AFFECTER(javax.servlet.http.HttpServletRequest request) throws Exception {

	//Recup du bean
	AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");
	
	String imageName = request.getParameter("LESIMAGES");
	//Si pas de pdf sélectionné
	if (imageName == null || imageName.length() == 0) {
		bean.setMessageErreur("Sélectionner un PDF");
		return;
	}

	
	String debut = request.getParameter("LESDEBUTACTES");
	String acteName = bean.getLB_INDICE_ACTES()[Integer.parseInt(debut)];
	String num = request.getParameter("LESNUMACTES");
	acteName+=bean.getLB_ACTES_NUM()[Integer.parseInt(num)].replaceAll(" ",  " ").trim();
	
	
	//Si pas d'acte sélectionné
	if (acteName == null || acteName.length() == 0) {
		bean.setMessageErreur("Sélectionner un acte");
		return;
	}

	Transaction t = new Transaction(getUserAppli(request));
	java.sql.Connection conn = t.getConnection();
	conn.setAutoCommit(false);

	String dateDuJour = Services.convertitDate(Services.dateDuJour(), "dd/MM/yyyy", "yyyyMMdd");

	String aMairie = acteName.substring(0,2);
	String aEtablissement = acteName.substring(2,4);
	String aStatut = acteName.substring(4,5);
	String aTypeActe= acteName.substring(5,6);
	String aAnneeActe= acteName.substring(6,10);
	String aNumeroActe= acteName.substring(10,15);

	java.sql.PreparedStatement st = conn.prepareStatement("update mairie.DCECSC set dtescn = ? "+
						" where MAIRIE = ? and CETAT = ? and STATUT = ? and TYPACT = ? and ANNACT = ? and NUMACT = ?")	;
	st.setInt(1, Integer.parseInt(dateDuJour));
	st.setInt(2, Integer.parseInt(aMairie));
	st.setInt(3, Integer.parseInt(aEtablissement));
	st.setString(4, aStatut);
	st.setString(5, aTypeActe);
	st.setInt(6, Integer.parseInt(aAnneeActe));
	st.setInt(7, Integer.parseInt(aNumeroActe));
	st.executeUpdate();

	boolean affecteImageOK = false;
	
	//On essaie d'affecter l'image
	try {
		affecteImageOK = performAffecterImage(request, acteName);
	} catch (Exception e) {
		//Si exception, on annule le commit
		affecteImageOK = false;
		bean.setMessageErreur("Erreur détectée pendant l'affectation : "+e)	;
		e.printStackTrace();
	}

	//Pas d'exception alors commit
	if (affecteImageOK) {
		st.close();
		conn.commit();
		conn.close();
		//reinit du bean
		initialiseBean(request);
	} else {
		bean.setMessageErreur("Erreur détectée pendant l'affectation."+bean.getMessageErreur())	;
		st.close();
		conn.rollback();
		conn.close();
	}
	
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public static void performPB_LESIMAGES(javax.servlet.http.HttpServletRequest request) throws Exception {

	AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");

	String imageName = request.getParameter("LESIMAGES");
	String debutacte = request.getParameter("LESDEBUTACTES");
	String numacte = request.getParameter("LESNUMACTES");

	bean.setCurrentPDF(imageName);
	bean.setLB_IMAGES_SELECT(imageName);
	bean.setLB_ACTES_SELECT(debutacte);
	bean.setLB_ACTES_NUM_SELECT(numacte);
	
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_DEBUTACTE(javax.servlet.http.HttpServletRequest request) throws Exception {

	AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");

	String debut = request.getParameter("LESDEBUTACTES");
	
	String key = bean.getLB_INDICE_ACTES()[Integer.parseInt(debut)]; 

	FormateListe fl = (FormateListe)bean.getHashActes().get(key);
	
	if (fl != null ){
		bean.setLB_ACTES_NUM(fl.getListeFormatee());
		bean.setLB_ACTES_NUM_SELECT("");
	} else {
		bean.setLB_ACTES_NUM(null);
		bean.setLB_ACTES_NUM_SELECT("");
	}
	bean.setLB_ACTES_SELECT(debut);
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_RAFRAICHIR(javax.servlet.http.HttpServletRequest request) throws Exception {

	initialiseBean(request);

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_VOIRPDF(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

	String repOrg = (String)getMesParametres().get("REP_PDF");
	
	AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");

	String imageName = bean.getCurrentPDF();
	if (imageName == null)
		return;

	File fichier = new File(repOrg+"/"+imageName);
	//si existe
	if (imageName.length() >0 && fichier.exists()) {

		response.setHeader("Cache-Control", "no-Cache");
		response.setHeader("Pragma", "No-cache");
		response.setContentType("application/pdf");


		byte[] bytearray = new byte[(int)fichier.length()];
		response.setContentLength(bytearray.length);		

		FileInputStream is = new FileInputStream(fichier);
		is.read(bytearray);
		is.close();
		 
		OutputStream os = response.getOutputStream();
		os.write(bytearray);
		os.flush();

	} else {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("");
	}


}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) 
	throws IOException{

	try {

		//Récup du bean s'il n'existe pas
		AffecteActeBean bean = (AffecteActeBean) request.getSession().getAttribute("affecteActeBean");
		
		if (!controlerHabilitation(request)) {
			javax.servlet.ServletContext sc= getServletContext();
			javax.servlet.RequestDispatcher rd = sc.getRequestDispatcher("/AffecteActe.jsp");
			rd.forward(request,response);
			return;
		}

		//Si le bean n'est pas dans la session ou si le dernier bean n'est pas le couyrant alors reset du bean
		if (bean == null || ! request.getSession().getAttribute("lastBean").equals(bean.getClass().getName())) {
			bean = initialiseBean(request);
			request.getSession().setAttribute("lastBean",bean.getClass().getName());
		}
		
		//Si demande de voirPDF
		if (request.getParameter("VOIRPDF") != null) {
			performPB_VOIRPDF(request, response);
			return;
		}

		String JSP = "AffecteActe.jsp";

		//Efface message erreur
		bean.setMessageErreur(null);

		//Récup des listes SSI on arribe du formulaire
		if (request.getParameterNames().hasMoreElements()) {
			bean.setLB_IMAGES_SELECT(request.getParameter("LESIMAGES"));
			bean.setLB_ACTES_SELECT(request.getParameter("LESACTES"));
		}
		
		if (request.getParameter("PB_AFFECTER") != null) {
			performPB_AFFECTER(request);
		} else if (request.getParameter("PB_RAFRAICHIR") != null) {
			performPB_RAFRAICHIR(request);
		} else if (request.getParameter("PB_LESIMAGES") != null) {
			performPB_LESIMAGES(request);
		} else if (request.getParameter("PB_DEBUTACTE") != null) {
			performPB_DEBUTACTE(request);
		}


		//On forwarde la JSP du process en cours
		javax.servlet.ServletContext sc= getServletContext();
		javax.servlet.RequestDispatcher rd = null;
		rd = sc.getRequestDispatcher("/"+JSP);
		rd.forward(request, response);
		
	} catch(Exception e) {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Exception interceptée : "+ e.toString());
		e.printStackTrace(out);
	}
}
}
