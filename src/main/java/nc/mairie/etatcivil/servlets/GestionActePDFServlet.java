package nc.mairie.etatcivil.servlets;

import com.lowagie.text.pdf.*;
import com.lowagie.text.*;

import java.util.*;
import java.util.List;
import java.io.*;

/**
 * Insérez la description du type ici.
 * Date de création : (04/03/2004 13:01:47)
 * @author: Administrator
 */
public class GestionActePDFServlet extends SuperServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4700325767933958215L;
	private String voirPDFUrl = null;
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void affectePDFCourant(javax.servlet.http.HttpServletRequest request, String nomFichier) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	bean.setCurrentPDF(nomFichier == null || nomFichier.length() == 0 ? null : nomFichier);
		
}
/*
	Concatène 2 pdf en 1
*/
@SuppressWarnings("unchecked")
private boolean concatenePDF(String [] args) throws Exception {

	String texte = "";
	
	boolean fichierManquant = false;
	for (int i = 0; i < args.length -1; i++){
		if (! new File(args[i]).exists()) {
			fichierManquant = true;
			texte+= "Fichier "+args[i]+" inexistant.\n";
		}
	}

	if (fichierManquant) throw new Exception(texte);
	

    int pageOffset = 0;
    @SuppressWarnings("rawtypes")
	ArrayList master = new ArrayList();
    int f = 0;
    String outFile = args[args.length - 1];
    Document document = null;
    PdfCopy writer = null;
    while (f < args.length - 1) {
        // we create a reader for a certain document
        PdfReader reader = new PdfReader(args[f]);
        reader.consolidateNamedDestinations();
        // we retrieve the total number of pages
        int n = reader.getNumberOfPages();
        List<?> bookmarks = SimpleBookmark.getBookmark(reader);
        if (bookmarks != null) {
            if (pageOffset != 0)
                SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset, null);
            master.addAll(bookmarks);
        }
        pageOffset += n;
        //            logger.info("There are " + n + " pages in " + args[f]);

        if (f == 0) {
            // step 1: creation of a document-object
            document = new Document(reader.getPageSizeWithRotation(1));
            // step 2: we create a writer that listens to the document
            writer = new PdfCopy(document, new java.io.FileOutputStream(outFile));
            // step 3: we open the document
            document.open();
        }
        // step 4: we add content
        PdfImportedPage page;
        for (int i = 0; i < n;) {
            ++i;
            page = writer.getImportedPage(reader, i);
            writer.addPage(page);
         //   logger.info("Processed page " + i);
        }
        PRAcroForm form = reader.getAcroForm();
        if (form != null)
            writer.copyAcroForm(reader);
        f++;
    }
    if (master.size() > 0)
        writer.setOutlines(master);
    // step 5: we close the document
    document.close();

    return true;
}


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
private GestionActePDFBean initialiseBean(javax.servlet.http.HttpServletRequest request) throws Exception {
	GestionActePDFBean aBean = new GestionActePDFBean(getVoirPDFUrl(request));

	//init des PDF
	initialisePDF(aBean);
	initialisePDFChoisis(aBean);
	
	//on met dans la session	
	request.getSession().setAttribute("gestionActePDFBean",aBean);

	return aBean;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:53:56)
 */
private void initialisePDF(GestionActePDFBean bean) {

	String repOrg = (String)getMesParametres().get("REP_PDF");

	File [] files = new File(repOrg).listFiles();
		
	ArrayList<String> liste = new ArrayList<String>();
	for (int i = 0; i < files.length; i++){
		liste.add(files[i].getName());
	}
	java.util.Collections.sort(liste);
	//bean.setLB_LESPDF((String [])liste.toArray(new String[0]));
	bean.setArrayLesPDF(liste);

}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:53:56)
 */
private void initialisePDFChoisis(GestionActePDFBean bean) {

	bean.setArrayLesPDFChoisis(null);

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_BAS(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	String lePDFDroit = (String)request.getParameter("LB_LESPDFCHOISIS");

	//Si vide alors erreur
	if (lePDFDroit == null) {
		bean.setMessageErreur("Vous devez sélectionner un PDF dans la liste de droite");
		return;
	}

	//On récupère l'index
	int index = bean.getArrayLesPDFChoisis().indexOf(lePDFDroit);
	//Si dernier, on ne fait rien
	if (index == bean.getArrayLesPDFChoisis().size() - 1) {
		bean.setMessageErreur("Ce PDF est déjà en bas de liste");
		return;
	}

	//on déplace
	bean.getArrayLesPDFChoisis().remove(index);
	bean.getArrayLesPDFChoisis().add(index+1, lePDFDroit);
	bean.setLB_LESPDFCHOISIS_SELECT(lePDFDroit);
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_DROIT(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	String lePDFGauche = (String)request.getParameter("LB_LESPDF");

	//On désélectionne à droite
	bean.setLB_LESPDFCHOISIS_SELECT(null);

	//Si vide alors erreur
	if (lePDFGauche == null || bean.getArrayLesPDF().size() == 0) {
		bean.setMessageErreur("Vous devez sélectionner un PDF dans la liste de gauches");
		return;
	}

	//On enlève le PDF de la liste de gauche
	bean.getArrayLesPDF().remove(lePDFGauche);
	bean.setLB_LESPDF_SELECT(null);

	//On le met à droire
	bean.getArrayLesPDFChoisis().add(lePDFGauche);
	bean.setLB_LESPDFCHOISIS_SELECT(lePDFGauche);
		
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_FUSIONNER(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");
	
	String repOrg = (String)getMesParametres().get("REP_PDF");

	//Si moins de 2 ichiers alors erreur
	if (bean.getArrayLesPDFChoisis().size() <2 ) {
		bean.setMessageErreur("Il faut au moins 2 PDF dans la liste de droite pour fusionner.");
		return;
	}

	//Construction du param
	@SuppressWarnings("unchecked")
	ArrayList<String> array = (ArrayList<String>) bean.getArrayLesPDFChoisis().clone();
	String res = "fusion_"+array.get(0);
	array.add(res);
	String [] lesPDF = (String [])array.toArray(new String[0]);
	for (int i = 0; i < lesPDF.length; i++){
		lesPDF[i]= repOrg+"/"+lesPDF[i];
	}

	//Concaténation
	try {
		concatenePDF(lesPDF);
	
		//si concaténation effectuéé, on vire les autres docs
		if (new File (repOrg+"/"+res).exists()) {
			for (int i = bean.getArrayLesPDFChoisis().size() -1; i >=0 ; i--){
				new File(repOrg+"/"+(String)bean.getArrayLesPDFChoisis().get(i)).delete();
				bean.getArrayLesPDFChoisis().remove(i);
			}
		} else {
			bean.setMessageErreur("Impossible de concaténer");
			return;
		}
	} catch (Exception e) {
		bean.setMessageErreur("Impossible de concaténer : "+e);
	}	

	//On ne sélectionne rien à droite
	bean.setLB_LESPDFCHOISIS_SELECT(null);

	//sélection du doc scindé à gauche
	bean.setLB_LESPDF_SELECT(res);
	bean.setCurrentPDF(res);

	//init de la liste des PDF
	initialisePDF(bean);

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_GAUCHE(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	String lePDFDroit = (String)request.getParameter("LB_LESPDFCHOISIS");

	//On désélectionne à gauche
	bean.setLB_LESPDF_SELECT(null);

	//Si vide alors erreur
	if (lePDFDroit == null || bean.getArrayLesPDFChoisis().size() == 0) {
		bean.setMessageErreur("Vous devez sélectionner un PDF dans la liste de droite");
		return;
	}

	//On enlève le PDF de la liste de droite
	bean.getArrayLesPDFChoisis().remove(lePDFDroit);
	bean.setLB_LESPDFCHOISIS_SELECT(null);

	//On le met à gauche
	bean.getArrayLesPDF().add(lePDFDroit);
	java.util.Collections.sort(bean.getArrayLesPDF());
	bean.setLB_LESPDF_SELECT(lePDFDroit);
		
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_HAUT(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	String lePDFDroit = (String)request.getParameter("LB_LESPDFCHOISIS");

	//Si vide alors erreur
	if (lePDFDroit == null) {
		bean.setMessageErreur("Vous devez sélectionner un PDF dans la liste de droite");
		return;
	}

	//On récupère l'index
	int index = bean.getArrayLesPDFChoisis().indexOf(lePDFDroit);
	//Si premier, on ne fait rien
	if (index == 0) {
		bean.setMessageErreur("Ce PDF est déjà en haut de liste");
		return;
	}

	//on déplace
	bean.getArrayLesPDFChoisis().remove(index);
	bean.getArrayLesPDFChoisis().add(index-1, lePDFDroit);
	bean.setLB_LESPDFCHOISIS_SELECT(lePDFDroit);
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_LESPDF(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");
	String lePDFGauche = (String)request.getParameter("LB_LESPDF");

	bean.setLB_LESPDFCHOISIS_SELECT(null);
	affectePDFCourant(request, lePDFGauche);
		
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_LESPDFCHOISIS(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");
	String lePDFDroit = (String)request.getParameter("LB_LESPDFCHOISIS");

	bean.setLB_LESPDF_SELECT(null);
	affectePDFCourant(request, lePDFDroit);
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_SCINDER(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");
	
	String repOrg = (String)getMesParametres().get("REP_PDF");

	//Au moins 1 fichier sinon erreur
	if (bean.getArrayLesPDFChoisis().size() <1 ) {
		bean.setMessageErreur("Il faut au moins 1 PDF dans la liste de droite pour scinder.");
		return;
	}

	//Scinder
	String texte = "";
	for (int i = bean.getArrayLesPDFChoisis().size() - 1 ; i >=0 ; i--){

		String nomFichierIN = repOrg+"/"+bean.getArrayLesPDFChoisis().get(i);
		try {
			if (scindePDF(nomFichierIN)) {
				new File(repOrg+"/"+bean.getArrayLesPDFChoisis().get(i)).delete();
			}
		} catch (Exception e) {
			texte+=e;
		} finally {
			bean.getArrayLesPDFChoisis().remove(i);
		}
		
	}

	//si erreur
	if (texte.length() > 0) {
		bean.setMessageErreur(texte);
	}
	
	//On ne sélectionne rien à droite
	bean.setLB_LESPDFCHOISIS_SELECT(null);

	//On ne sélectionne rien à gauche
	bean.setLB_LESPDF_SELECT(null);

	//RAZ du current PDF
	bean.setCurrentPDF(null);

	//init de la liste des PDF
	initialisePDF(bean);

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_SUPPRIMER(javax.servlet.http.HttpServletRequest request) throws Exception {

	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");
	
	String repOrg = (String)getMesParametres().get("REP_PDF");

	//Si vide alors erreur
	if (bean.getArrayLesPDFChoisis().size() == 0) {
		bean.setMessageErreur("Aucun PDF dans la liste de droite");
		return;
	}

	//Parcours des fichiers de droite et suppression
	for (int i = bean.getArrayLesPDFChoisis().size()-1; i >=0 ; i--){
		String nomFichier = (String)bean.getArrayLesPDFChoisis().get(i);
		File fichier = new File(repOrg+"/"+nomFichier);
		if (! fichier.delete()) {
			bean.setMessageErreur("Impossible de supprimer "+nomFichier);
			return;
		}
		bean.getArrayLesPDFChoisis().remove(i);
	}

	//On ne sélectionne rien
	bean.setLB_LESPDFCHOISIS_SELECT(null);
	if (! bean.getArrayLesPDF().contains(bean.getCurrentPDF())) {
		bean.setCurrentPDF(null);
	}

	//init de la liste des PDF
	initialisePDF(bean);
	
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
public void performPB_VOIRPDF(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

	String repOrg = (String)getMesParametres().get("REP_PDF");
	
	GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

	String currentPDF = bean.getCurrentPDF();
	if (currentPDF == null)
		return;

	File fichier = new File(repOrg+"/"+currentPDF);
	//si existe
	if (currentPDF.length() >0 && fichier.exists()) {

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
		GestionActePDFBean bean = (GestionActePDFBean) request.getSession().getAttribute("gestionActePDFBean");

		if (!controlerHabilitation(request)) {
			javax.servlet.ServletContext sc= getServletContext();
			javax.servlet.RequestDispatcher rd = sc.getRequestDispatcher("/GestionActePDF.jsp");
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
			
		String JSP = "GestionActePDF.jsp";

		//Efface message erreur
		bean.setMessageErreur(null);

		//Récup des listes SSI on arrive du formulaire
		if (request.getParameterNames().hasMoreElements()) {
			bean.setLB_LESPDF_SELECT(request.getParameter("LB_LESPDF"));
			bean.setLB_LESPDFCHOISIS_SELECT(request.getParameter("LB_LESPDFCHOISIS"));
		}
		
		if (request.getParameter("PB_SUPPRIMER") != null) {
			performPB_SUPPRIMER(request);
		} else if (request.getParameter("PB_FUSIONNER") != null) {
			performPB_FUSIONNER(request);
		} else if (request.getParameter("PB_SCINDER") != null) {
			performPB_SCINDER(request);
		} else if (request.getParameter("PB_DROITE") != null) {
			performPB_DROIT(request);
		} else if (request.getParameter("PB_GAUCHE") != null) {
			performPB_GAUCHE(request);
		} else if (request.getParameter("PB_HAUT") != null) {
			performPB_HAUT(request);
		} else if (request.getParameter("PB_BAS") != null) {
			performPB_BAS(request);
		} else if (request.getParameter("PB_LESPDF") != null) {
			performPB_LESPDF(request);
		} else if (request.getParameter("PB_LESPDFCHOISIS") != null) {
			performPB_LESPDFCHOISIS(request);
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
/*
	Scinde 1 pdf en n PDF
*/
private boolean scindePDF(String nomFichierIN) throws Exception {
	
	//Si fichier manquant
	File pdfFile = new File(nomFichierIN);
	if (! pdfFile.exists()) {
		throw new Exception("Fichier "+nomFichierIN+" inexistant.\n");
	}

	try {
		// Création du reader
		PdfReader reader = new PdfReader(nomFichierIN);
		// récup du nombre de pages
		int n = reader.getNumberOfPages();
		if (n<=1) {
			throw new Exception("Inutile de scinder "+pdfFile.getName()+", il n'a que "+n+" pages");
		}

		//Création de chaque doc
		for (int curPage = 1; curPage <=n ; curPage++){
			//Création du document
			Document document1 = new Document(reader.getPageSizeWithRotation(curPage));

			//création du writer
			String nomFichierOUT =(	nomFichierIN.toUpperCase().indexOf(".PDF") == -1 ? 
									nomFichierIN : 
									nomFichierIN.substring(0,nomFichierIN.toUpperCase().lastIndexOf(".PDF"))
								  ) + "_page_"+curPage+".PDF";
			PdfWriter writer1 = PdfWriter.getInstance(document1, new FileOutputStream(nomFichierOUT));

			//Ouverture du doc
			document1.open();
			PdfContentByte cb1 = writer1.getDirectContent();
			PdfImportedPage page;
			int rotation;

			// remplissage du doc
			document1.setPageSize(reader.getPageSizeWithRotation(curPage));
			document1.newPage();
			page = writer1.getImportedPage(reader, curPage);
			rotation = reader.getPageRotation(curPage);
			if (rotation == 90 || rotation == 270) {
				cb1.addTemplate(page, 0, -1f, 1f, 0, 0, reader.getPageSizeWithRotation(curPage).getHeight());
			} else {
				cb1.addTemplate(page, 1f, 0, 0, 1f, 0, 0);
			}
			
			// fermeture du doc
			document1.close();
		}
	} catch (Exception e) {
		throw (e);
	}
 
	return true; 
}
}
