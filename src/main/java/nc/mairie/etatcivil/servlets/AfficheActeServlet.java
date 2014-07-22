package nc.mairie.etatcivil.servlets;

import java.io.*;

import nc.mairie.etatcivil.Acte;
import nc.mairie.technique.Transaction;

/**
 * Insérez la description du type ici.
 * Date de création : (04/03/2004 13:01:47)
 * @author: Administrator
 */
public class AfficheActeServlet extends SuperServlet {

/**
	 * 
	 */
	private static final long serialVersionUID = -6750248832268683529L;
/**
 * Cette méthode a été importée à partir d'un fichier .class.
 * Aucun code source disponible.
 */
public void init() throws javax.servlet.ServletException {
	// insert code to initialize the servlet here
	initialiseParametreInitiaux();

}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param response Object that encapsulates the response from the servlet
 */
private void performAffichePDF(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse res, String idPDF) 
	throws Exception{

		
		Acte acte = Acte.parse(idPDF);

		String path = (String)getMesParametres().get("REP_ECETATCIVIL");
		
		File fichier = new File(path+"/"+acte.getNomDossier()+"/"+acte.getNomFichier());

		//Si le fichier n'existe pas
		if (! fichier.exists()) {

			//on veut du pdf
			PrintWriter out = res.getWriter();
			res.setContentType("text/html");
			out.println("L'acte "+ idPDF +" n'a pas été scannée ou le fichier associé est introuvable.");
			return;
			
		}

		//on envoi du pdf
		res.setContentType("application/pdf");
			
		byte[] bytearray = new byte[(int)fichier.length()];
		res.setContentLength(bytearray.length);

		FileInputStream is = new FileInputStream(fichier);
		is.read(bytearray);
		is.close();
		 
		OutputStream os = res.getOutputStream();
		os.write(bytearray);
		os.flush();
}
/**
 * Process incoming requests for information
 * 
 * @param request Object that encapsulates the request to the servlet 
 * @param res Object that encapsulates the response from the servlet
 */
public void performTask(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse res) 
	throws IOException{

	try {
//		javax.servlet.http.HttpSession session = request.getSession();
		
		//Récup de l'ip appelante
		String ip = request.getRemoteAddr().trim();

		//recup de l'Id Pdf correnpondant à l'IP
		String idPDF = null;
		try {
			//on récupère l'Id de la table.
			idPDF = recupereIdPDF(request, ip);
			if (idPDF == null) {
				throw new Exception("Impossible de récupérer l'id de l'acte avec l'IP "+ ip);
			}
//			session.setAttribute("IDPDF", idPDF);
		} catch (Exception e) {
			//Pas trouvé, donc on récupère de la session
//			idPDF = (String)session.getAttribute("IDPDF");
			if (idPDF == null) {
				//on veut du pdf
				PrintWriter out = res.getWriter();
				res.setContentType("text/html");
				out.println("Impossible de récupérer l'id de l'acte avec l'IP "+ ip);
				out.flush();
				return;
			}
		}

		//on efface la demande de la table
//		supprimerIdPDF(request, ip);

		//on Affiche le pdf
		performAffichePDF(request, res, idPDF);
		
	} catch(Exception e) {
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("Exception FDF :");
		out.println(e.toString());
		e.printStackTrace(out);
	}
}
/**
 * Insérez la description de la méthode à cet endroit.
 *  Date de création : (22/02/2002 10:51:46)
 * @return fr.averse.servlets.Contexte
 */
private String recupereIdPDF(javax.servlet.http.HttpServletRequest request, String IPAddr) throws Exception{

	String result=null;
	
	String admin = (String)getMesParametres().get("HOST_SGBD_ADMIN");
	String pwd = (String)getMesParametres().get("HOST_SGBD_PWD");
	String serveur =(String)getMesParametres().get("HOST_SGBD");
	
	nc.mairie.technique.UserAppli aUser = new nc.mairie.technique.UserAppli(admin, pwd, serveur);
	
	Transaction t = new Transaction(aUser);
	java.sql.Connection conn = t.getConnection();;
	java.sql.Statement st = null;
	java.sql.ResultSet rs = null;

	try {
		st = conn.createStatement();
		rs = st.executeQuery("select * from mairie.dctpdf where ipaddr = '"+IPAddr+"'");
		if (rs.next())	result = rs.getString("IDPDF");
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	} finally {
		rs.close();
		st.close();
		t.rollbackTransaction();
		t.fermerConnexion();
		
	}
	
	return result;
}
/**
 * Insérez la description de la méthode à cet endroit.
 *  Date de création : (22/02/2002 10:51:46)
 */
@SuppressWarnings("unused")
private void supprimerIdPDF(javax.servlet.http.HttpServletRequest request, String IPAddr) throws Exception{

	
	String admin = (String)getMesParametres().get("HOST_SGBD_ADMIN");
	String pwd = (String)getMesParametres().get("HOST_SGBD_PWD");
	String serveur =(String)getMesParametres().get("HOST_SGBD");
	
	nc.mairie.technique.UserAppli aUser = new nc.mairie.technique.UserAppli(admin, pwd, serveur);
	
	Transaction t = new Transaction(aUser);
	java.sql.Connection conn = t.getConnection();;
	java.sql.PreparedStatement st = null;

	try {
		st = conn.prepareStatement("delete from mairie.dctpdf where ipaddr = ?");
		st.setString(1,IPAddr);

		st.executeUpdate();

		t.commitTransaction();
		
	} catch (Exception e) {
		t.rollbackTransaction();
		e.printStackTrace();
		throw e;
	} finally {
		st.close();
		conn.close();
		t.fermerConnexion();

	}
	
	
}
}
