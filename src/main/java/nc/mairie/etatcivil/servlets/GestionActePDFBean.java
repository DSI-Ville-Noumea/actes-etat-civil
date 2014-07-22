package nc.mairie.etatcivil.servlets;

import java.util.ArrayList;

/**
 * Insérez la description du type ici.
 * Date de création : (08/04/2004 12:59:27)
 * @author: Administrator
 */
public class GestionActePDFBean {
	private ArrayList<String> arrayLesPDF = null;
	private String LB_LESPDF_SELECT = null;
	private ArrayList<String> arrayLesPDFChoisis = null;
	private String LB_LESPDFCHOISIS_SELECT = null;
	private String messageErreur;
	private String currentPDF = null;
	private String voirPDFUrl = null;
	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (17/06/2004 11:23:12)
	 * @param aVoirPDFUrl aVoirPDFUrl
	 */
	public GestionActePDFBean(String aVoirPDFUrl) {
		voirPDFUrl = aVoirPDFUrl;
		
	}
	/**
	 * Insérez la description de la méthode ici.
	 *  Date de création : (17/06/2004 11:23:12)
	 */
	public GestionActePDFBean() {
		
	}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 13:02:22)
 * @return ArrayList
 */
public ArrayList<String> getArrayLesPDF() {
	if (arrayLesPDF == null) {
		arrayLesPDF =new ArrayList<String>();
	}
	return arrayLesPDF;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 13:02:22)
 * @return ArrayList
 */
public ArrayList<String> getArrayLesPDFChoisis() {
	if (arrayLesPDFChoisis == null) {
		arrayLesPDFChoisis = new ArrayList<String>();
	}
	return arrayLesPDFChoisis;
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
 *  Date de création : (08/04/2004 13:00:18)
 * @param i i
 * @return java.lang.String[]
 * @throws Exception Exception
 */
public java.lang.String getLB_INDICE(int i) throws Exception {
	return ""+i;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @param i i
 * @return java.lang.String[]
 */
public java.lang.String getLB_LESPDF(int i) {
	try {
		return (String)getArrayLesPDF().get(i);
	} catch (Exception e) {
		if (i==0)
			return "";
		else
			throw new java.lang.ArrayIndexOutOfBoundsException(e.getMessage());
	}
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 12:45:51)
 * @return java.lang.String
 */
public java.lang.String getLB_LESPDF_SELECT() {
	return LB_LESPDF_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 09:59:18)
 * @param i i
 * @return java.lang.String[]
 */
public java.lang.String getLB_LESPDFCHOISIS(int i) {
	try {
		return (String)getArrayLesPDFChoisis().get(i);
	} catch (Exception e) {
		if (i==0)
			return "";
		else
			throw new java.lang.ArrayIndexOutOfBoundsException(e.getMessage());
	}
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 12:45:51)
 * @return java.lang.String
 */
public java.lang.String getLB_LESPDFCHOISIS_SELECT() {
	return LB_LESPDFCHOISIS_SELECT;
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
 *  Date de création : (14/06/2004 13:02:22)
 * @param newArrayLesPDF ArrayList
 */
public void setArrayLesPDF(ArrayList<String> newArrayLesPDF) {
	arrayLesPDF = newArrayLesPDF;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 13:02:22)
 * @param newArrayLesPDFChoisis ArrayList
 */
public void setArrayLesPDFChoisis(ArrayList<String> newArrayLesPDFChoisis) {
	arrayLesPDFChoisis = newArrayLesPDFChoisis;
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
 *  Date de création : (14/06/2004 12:45:51)
 * @param newLB_LESPDF_SELECT java.lang.String
 */
public void setLB_LESPDF_SELECT(java.lang.String newLB_LESPDF_SELECT) {
	LB_LESPDF_SELECT = newLB_LESPDF_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/06/2004 12:45:51)
 * @param newLB_LESPDFCHOISIS_SELECT java.lang.String
 */
public void setLB_LESPDFCHOISIS_SELECT(java.lang.String newLB_LESPDFCHOISIS_SELECT) {
	LB_LESPDFCHOISIS_SELECT = newLB_LESPDFCHOISIS_SELECT;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (14/04/2004 12:51:58)
 * @param newMessageErreur java.lang.String
 */
public void setMessageErreur(java.lang.String newMessageErreur) {
	messageErreur = newMessageErreur;
}
}
