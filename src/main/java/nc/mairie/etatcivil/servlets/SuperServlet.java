package nc.mairie.etatcivil.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;

import org.apache.commons.codec.binary.Base64;

import nc.mairie.technique.MairieLDAP;
import nc.mairie.technique.Transaction;
import nc.mairie.technique.UserAppli;
import nc.mairie.technique.VariableGlobale;

public abstract class SuperServlet extends HttpServlet {

	/**
	 * 
	 */
	private final static Logger logger = Logger.getLogger(SuperServlet.class.getName());
	private static final long serialVersionUID = 5203677608940382334L;
	private Hashtable<String, String> mesParametres = new Hashtable<String, String>();
	private static ArrayList<String> listeUserHabilites = new ArrayList<String>();
	
	
	/**
	 * Insérez la description de la méthode à cet endroit.
	 *  Date de création : (22/02/2002 10:51:46)
	 */
	protected void initialiseParametreInitiaux() {

		boolean doitPrendreInit = getServletContext().getInitParameterNames().hasMoreElements();

		logger.info("Chargement des paramètres initiaux dans la servlet : "+getClass().getName());
		if (getMesParametres().size() == 0) {

			//chargement des paramêtres du contexte
			Enumeration<?> enumContext = doitPrendreInit ? getServletContext().getInitParameterNames() : getServletContext().getAttributeNames();
			while (enumContext.hasMoreElements()) {
				try {
					String cleParametre = (String)enumContext.nextElement();
					if (cleParametre != null && ! cleParametre.startsWith("com.ibm.websphere") ) {
						String valParametre = doitPrendreInit ? (String)getServletContext().getInitParameter(cleParametre) : (String)getServletContext().getAttribute(cleParametre);
						getMesParametres().put(cleParametre,valParametre);
						logger.info("Chargement de la clé : "+cleParametre+" avec "+valParametre);
					}
				} catch (Exception e) {
					continue;
				}
			}
		
			//chargement des param de la servlet
			Enumeration<?> enumServlet = getInitParameterNames();
			while (enumServlet.hasMoreElements()) {
				String cleParametre = (String)enumServlet.nextElement();
				String valParametre = (String)getInitParameter(cleParametre);
				getMesParametres().put(cleParametre,valParametre);
				logger.info("Chargement de la clé : "+cleParametre+" avec "+valParametre);
			}
		}
		logger.info("Fin de chargement des paramètres initiaux dans la servlet : "+getClass().getName());
	}

	public Hashtable<String, String> getMesParametres() {
		if (mesParametres == null) {
			mesParametres = new Hashtable<String, String>();
		}
		return mesParametres ;
	}
	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (28/10/2002 11:17:51)
	 * @author Luc Bourdil
	 * @return nc.mairie.technique.UserAppli
	 * @param request javax.servlet.http.HttpServletRequest
	 */
	public static UserAppli getUserAppli(javax.servlet.http.HttpServletRequest request) {
		return (UserAppli)VariableGlobale.recuperer(request,VariableGlobale.GLOBAL_USER_APPLI);
	}
	/**
	 * Méthode qui contrôle l'habilitation d'un utilisateur qui se connecte
	 * @author Luc Bourdil
	 * @param request request
	 * @return boolean
	 */
	public boolean controlerHabilitation(javax.servlet.http.HttpServletRequest request) {
		//Si un user appli en session alors OK
		if (getUserAppli(request) != null)
			return true;

		//Sinon fenêtre de connexion
		String auth = request.getHeader("Authorization");
		if (auth == null)
			return false;

		String str = null;
		String passwd = null;
		String user = null;

		// Vérification du schéma d'authentification
		String startString = "basic ";
		if (auth.toLowerCase().startsWith(startString)) {
			// Extraction et décodage du user
			String creditB64 = auth.substring(startString.length());
			try {
				//byte[] credit = decoder.decodeBuffer(creditB64);
	            byte[] credit = Base64.decodeBase64(creditB64);
				str = new String(credit);

				//Découpage du nom user:passwd
				int sep = str.indexOf(':');
				user = str.substring(0,sep);
				passwd = str.substring(sep+1);
			} catch (Exception e) {
				return false;
			}
		}

		//init de la liste de user habilités au cas où mis à jour
		initialiseListeUserHabilites();
		
		//Contrôle de la liste des authorisés
		if (user == null || ! getListeUserHabilites().contains(user.toUpperCase())) {
			logger.info("AffecteActeServlet : L'utilisateur "+user+" n'appartient pas à la liste des utilisateurs habilités à affecter les actes");
			return false;
		}
		
		//Contrôle de l'habilitation LDAP
		if (!MairieLDAP.controlerHabilitation(getMesParametres(), user,passwd))
			return false;
		//Creation du UserAppli
		UserAppli aUserAppli = new UserAppli(user,passwd, (String)getMesParametres().get("HOST_SGBD"));
		//Ajout du user en var globale
		VariableGlobale.ajouter(request,VariableGlobale.GLOBAL_USER_APPLI, aUserAppli);

		return true;
	}
	
	/**
	 * Process incoming HTTP GET requests 
	 * 
	 * @param request Object that encapsulates the request to the servlet 
	 * @param response Object that encapsulates the response from the servlet
	 */
	public void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

		performTask(request, response);

	}
	/**
	 * Process incoming HTTP POST requests 
	 * 
	 * @param request Object that encapsulates the request to the servlet 
	 * @param response Object that encapsulates the response from the servlet
	 */
	public void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

		performTask(request, response);

	}

	public abstract void performTask(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws IOException;

	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (26/05/2004 11:42:46)
	 * @return ArrayList
	 */
	private static ArrayList<String> getListeUserHabilites() {
		if (listeUserHabilites ==  null) {
			listeUserHabilites = new ArrayList<String>();
		}
		return listeUserHabilites;
	}

	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (14/04/2004 09:53:56)
	 */
	protected  void initialiseListeUserHabilites() {
	
		if (getListeUserHabilites().size() == 0) {
			Transaction t = null;
			
			try  {
				String admin = (String)getMesParametres().get("HOST_SGBD_ADMIN");
				String pwd = (String)getMesParametres().get("HOST_SGBD_PWD");
				String serveur =(String)getMesParametres().get("HOST_SGBD");
				
				UserAppli aUser = new UserAppli(admin, pwd, serveur);
				
				t = new Transaction(aUser);
		
				java.sql.Connection conn = t.getConnection();
				java.sql.Statement st = conn.createStatement();
				java.sql.ResultSet rs = st.executeQuery("select * from mairie.dcidut where APDF='O'");
				
				while (rs.next()){
					String user = rs.getString("CDIDUT");
					getListeUserHabilites().add(user.trim().toUpperCase());
				}
		
				rs.close();
				st.close();
				conn.commit();
				conn.close();
			} catch (Exception e) {
				t.fermerConnexion();
				e.printStackTrace();
			}
		}
		
	}
	
}
