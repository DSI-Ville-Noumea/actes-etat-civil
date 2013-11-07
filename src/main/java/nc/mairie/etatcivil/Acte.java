package nc.mairie.etatcivil;

import nc.mairie.technique.Services;
/**
 * Insérez la description du type ici.
 * Date de création : (29/03/2004 15:55:03)
 * @author: Administrator
 */
public class Acte {
	private String mairie;
	private String etablissement;
	private String statut;
	private String typeActe;
	private String anneeActe;
	private String numeroActe;
/**
 * Commentaire relatif au constructeur Acte.
 * "1801C1200100001"
 * "18 01 C 1 2001 00001"
 */
public Acte(String aMairie, String aEtablissement, String aStatut, String aTypeActe, String aAnneeActe, String aNumeroActe) {
	mairie= Services.lpad(aMairie,2,"0");
	etablissement = Services.lpad(aEtablissement,2,"0");
	statut=aStatut;
	typeActe=aTypeActe;
	anneeActe=aAnneeActe;
	numeroActe=Services.lpad(aNumeroActe,5,"0");
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getAnneeActe() {
	return anneeActe;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getEtablissement() {
	return etablissement;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:04:03)
 * @return String
 */
public String getExt() {
	return ".PDF";
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getMairie() {
	return mairie;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:04:03)
 * @return String
 */
public String getNomDossier() {
	return getMairie()+"\\"+getEtablissement()+"\\"+getStatut()+"\\"+getTypeActe()+"\\"+getAnneeActe();
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:04:03)
 * @return String
 */
public String getNomFichier() {
	return getNomFichierSansExt()+getExt();
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:04:03)
 * @return String
 */
public String getNomFichierSansExt() {
	return getMairie()+getEtablissement()+getStatut()+getTypeActe()+getAnneeActe()+getNumeroActe();
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:04:03)
 * @return String
 */
public String getNomFichierSansNumSansExt() {
	return getMairie()+getEtablissement()+getStatut()+getTypeActe()+getAnneeActe();
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getNumeroActe() {
	return numeroActe;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getStatut() {
	return statut;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (29/03/2004 16:03:37)
 * @return String
 */
private String getTypeActe() {
	return typeActe;
}
/**
 * Insérez la description de la méthode ici.
 *  Date de création : (01/04/2004 09:36:50)
 * @return migration.etatcivil.Acte
 * @param numActe String
 */
public static Acte parse(String nomFichier) {
	if (nomFichier == null || nomFichier.length() != 15)
		return null;
	String aMairie = nomFichier.substring(0,2);
	String aEtablissement = nomFichier.substring(2,4);
	String aStatut = nomFichier.substring(4,5);
	String aTypeActe= nomFichier.substring(5,6);
	String aAnneeActe= nomFichier.substring(6,10);
	String aNumeroActe= nomFichier.substring(10,15);
	return new Acte(aMairie, aEtablissement, aStatut, aTypeActe, aAnneeActe, aNumeroActe);
}
}
