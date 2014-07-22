package nc.mairie.etatcivil.servlets;

import java.io.Serializable;
import java.util.Hashtable;

import nc.mairie.technique.FormateListe;

/**
 * Insérez la description du type ici.
 * Date de création : (08/04/2004 12:59:27)
 * @author: Administrator
 */
public class AffecteActeBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5699520347045925412L;
	private String [] LB_IMAGES = null;
	private String LB_IMAGES_SELECT = null;
	private String [] LB_ACTES = null;
	private String LB_ACTES_SELECT = null;
	private String [] LB_ACTES_NUM = null;
	private String LB_ACTES_NUM_SELECT = null;
	private String [] LB_INDICE_ACTES = null;
	private Hashtable<String, FormateListe> hashActes = new Hashtable<String, FormateListe>();
	private String messageErreur;
	private String currentPDF = null;
	private String voirPDFUrl = null;
	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (17/06/2004 11:18:27)
	 * @param aVoirPDFUrl aVoirPDFUrl
	 */
	public AffecteActeBean(String aVoirPDFUrl) {
		voirPDFUrl = aVoirPDFUrl;
		
	}
	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (17/06/2004 11:18:27)
	 */
	public AffecteActeBean() {
		
	}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 15:07:12)
 * @return java.lang.String
 */
public java.lang.String getCurrentPDF() {
	return currentPDF;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @return java.lang.String[]
 */
public java.lang.String[] getLB_ACTES() {
	String [] vide = {""};
	if (LB_ACTES == null)  {
		LB_ACTES = vide;
	}
	return LB_ACTES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
 * @return java.lang.String
 */
public java.lang.String getLB_ACTES_SELECT() {
	return LB_ACTES_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @return java.lang.String[]
 */
public java.lang.String[] getLB_ACTES_NUM() {
	String [] vide = {""};
	if (LB_ACTES_NUM == null)  {
		LB_ACTES_NUM = vide;
	}
	return LB_ACTES_NUM;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
 * @return java.lang.String
 */
public java.lang.String getLB_ACTES_NUM_SELECT() {
	return LB_ACTES_NUM_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @return java.lang.String[]
 */
public java.lang.String[] getLB_IMAGES() {
	String [] vide = {""};
	if (LB_IMAGES == null)  {
		LB_IMAGES = vide;
	}
	return LB_IMAGES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
 * @return java.lang.String
 */
public java.lang.String getLB_IMAGES_SELECT() {
	return LB_IMAGES_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (08/04/2004 13:00:18)
 * @return java.lang.String[]
 * @param i i
 * @throws Exception Exception
 */
public java.lang.String getLB_INDICE(int i) throws Exception {
	return ""+i;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @return java.lang.String[]
 */
public java.lang.String[] getLB_INDICE_ACTES() {
	String [] vide = {""};
	if (LB_INDICE_ACTES == null)  {
		LB_INDICE_ACTES = vide;
	}
	return LB_INDICE_ACTES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 12:51:58)
 * @return java.lang.String
 */
public java.lang.String getMessageErreur() {
	return messageErreur;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (17/06/2004 11:17:22)
 * @return java.lang.String
 */
public java.lang.String getVoirPDFUrl() {
	return voirPDFUrl;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 15:07:12)
 * @param newCurrentPDF java.lang.String
 */
public void setCurrentPDF(java.lang.String newCurrentPDF) {
	currentPDF = newCurrentPDF;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @param newLB_ACTES java.lang.String[]
 */
public void setLB_ACTES(java.lang.String[] newLB_ACTES) {
	LB_ACTES = newLB_ACTES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
 * @param newLB_ACTES_SELECT java.lang.String
 */
public void setLB_ACTES_SELECT(java.lang.String newLB_ACTES_SELECT) {
	LB_ACTES_SELECT = newLB_ACTES_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
  * @param newLB_ACTES_NUM newLB_ACTES_NUM
 */
public void setLB_ACTES_NUM(java.lang.String[] newLB_ACTES_NUM) {
	LB_ACTES_NUM = newLB_ACTES_NUM;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
  * @param newLB_ACTES_NUM_SELECT newLB_ACTES_NUM_SELECT
 */
public void setLB_ACTES_NUM_SELECT(java.lang.String newLB_ACTES_NUM_SELECT) {
	LB_ACTES_NUM_SELECT = newLB_ACTES_NUM_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @param newLB_IMAGES java.lang.String[]
 */
public void setLB_IMAGES(java.lang.String[] newLB_IMAGES) {
	LB_IMAGES = newLB_IMAGES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (15/06/2004 13:32:52)
 * @param newLB_IMAGES_SELECT java.lang.String
 */
public void setLB_IMAGES_SELECT(java.lang.String newLB_IMAGES_SELECT) {
	LB_IMAGES_SELECT = newLB_IMAGES_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @param newLB_INDICE_ACTES java.lang.String[]
 */
public void setLB_INDICE_ACTES(java.lang.String[] newLB_INDICE_ACTES) {
	LB_INDICE_ACTES = newLB_INDICE_ACTES;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 12:51:58)
 * @param newMessageErreur java.lang.String
 */
public void setMessageErreur(java.lang.String newMessageErreur) {
	messageErreur = newMessageErreur;
}


/**
* Cette méthode retourne la date courante sous la forme jj/mm/aaaa
 * @param liste liste
 * @param select select
 * @return String
 */
public String forComboHTML(String [] liste, String select) {
	return forComboHTML(liste, null, select);
}
/**
* Cette méthode retourne la date courante sous la forme jj/mm/aaaa
* * @param liste liste
 * @param colors colors
 * @param select select
 * @return String
 */
public String forComboHTML(String [] liste, String [] colors, String select) {
	return forComboHTML(liste, colors, null, select);
}
/**
* Cette méthode retourne la date courante sous la forme jj/mm/aaaa
**/

/**
 * @param liste liste
 * @param colors colors
 * @param fonds fonds
 * @param select select
 * @return String
 */
public String forComboHTML(String [] liste, String [] colors, String [] fonds, String select) {

	//<OPTION value="2" style="background-color: red; color: green;">2</OPTION>
	
	StringBuffer result = new StringBuffer();
	
	if (liste.length == 0) return "";

	String aColor;
	String aFond;
	String aStyle;
	
	for (int i = 0; i < liste.length; i++){
		result.append("<OPTION ");
		if (String.valueOf(i).equals(select)) {
			result.append("selected ");
		}
		result.append("value=\"");
		result.append(i);
		result.append("\"");
		
		//Affectation de color
		try {
			aColor = colors[i];
		} catch (Exception exColor) {
			aColor=null;
		}
			
		//Affectation de fond
		try {
			aFond = fonds[i];
		} catch (Exception exFond) {
			aFond=null;
		}
		
		//Affectation du style
		aStyle = (aColor == null ? "" : "color: "+aColor+";")+
				 (aFond == null ? "" : "background-color: "+aFond+";");
		if (aStyle.length() > 0) {
			aStyle = "style=\"" + aStyle+"\"";
		}
		result.append(aStyle);	
		
		result.append(">");
		result.append(liste[i]);
		result.append("</OPTION>\n");
		
	}
	
	return result.toString();
	
}

public Hashtable<String, FormateListe> getHashActes() {
	return hashActes;
}

}
