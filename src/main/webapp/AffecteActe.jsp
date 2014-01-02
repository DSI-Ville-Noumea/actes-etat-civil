<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="nc.mairie.etatcivil.servlets.SuperServlet"%>
<%@page contentType="text/html;charset=UTF-8"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
<TITLE>Affectation des actes</TITLE>
<SCRIPT language="javascript" src="js/menu.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sélectionner un élément dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

//init
function init() {
	gereBoutonAffecter();
}

//Locke toutes les zones
function lockTout() {
//	lockListes();
	document.formu.elements["PB_AFFECTER"].style.display='none';
}

//bloquer les listes de saisie
function lockListes() {
	document.formu.elements["LESIMAGES"].disabled=true;
	document.formu.elements["LESDEBUTACTES"].disabled=true;
	document.formu.elements["LESNUMACTES"].disabled=true;
}

//gere le boutton affecter
function gereBoutonAffecter() {
//	if (document.formu.elements["IMAGES"].value == "" || document.formu.elements["ACTES"].value == "" ) {

	if (document.formu.elements["LESIMAGES"].value == "" || document.formu.elements["LESNUMACTES"].value == "0" ) {

		document.formu.elements["PB_AFFECTER"].style.display='none';
	} else {
		document.formu.elements["PB_AFFECTER"].style.display='';
	};
}

//Select image
function selectImage() {
//	document.formu.elements["IMAGES"].value=document.formu.elements["LESIMAGES"].value;
	gereBoutonAffecter();
}

//Select acte
function selectActe() {
//	document.formu.elements["ACTES"].value=document.formu.elements["LESACTES"].value;
	gereBoutonAffecter();
}

//Select acte
function affecter() {
	lockTout();
	texte.innerText = "En cours d'affectation de l'acte. Veuillez patienter.";
	progress.innerText = "....................";
}

</SCRIPT>
</HEAD>
<%
	if (SuperServlet.getUserAppli(request) == null) {
		response.setStatus(javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED); 
		response.setHeader("WWW-Authenticate","BASIC realm=\"Habilitation HTTP pour l'état civil\"");

		response.setContentType("text/html");
		try {
		out.println("Vous n'êtes pas habilité");
		} catch (Exception e) {
		}
		
		return;
	}
%>
<BODY bgcolor="#ebebeb" onload="init()" style="background-repeat:no-repeat;background-image : url(images/Log-Nea3_120.gif);background-position : 0px 15px;">
<script>
	ajoute('Affectation', '<%=request.getContextPath()%>/AffecteActeServlet');
	ajoute('Gestion', '<%=request.getContextPath()%>/GestionActePDFServlet');
	insererMenu('Menu Actes');
</script>

<FORM name="formu" method="POST" action="AffecteActeServlet" style="text-align : center;border-width : thin thin thin thin;">

<jsp:useBean class="nc.mairie.etatcivil.servlets.AffecteActeBean" id="affecteActeBean" scope="session"></jsp:useBean>
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

    <tr>
      <TD style="background-repeat : no-repeat;" nowrap height="120" align="center">
      <TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
  <TBODY>
          <TR>
            <TD colspan="3" valign="bottom">
      <TABLE border="1" class="sigp2">
        <TBODY>
          <TR>
            <TD rowspan="2" align="center" width="120"><INPUT type="submit" name="PB_RAFRAICHIR" value="Rafraîchir" class="sigp2-Bouton-100"></TD>
            <TD>
                  <TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
              <TBODY>
                <TR>
                        <TD width="90" style="font-size : x-small;font-weight : bold;" nowrap>Acte scanné :</TD>
                        <TD><%--METADATA type="DynamicData" startspan
<SELECT name="LESIMAGES" style="width : 200px;" loopproperty="affecteActeBean.LB_IMAGES[]" dynamicelement itemproperty="affecteActeBean.LB_IMAGES[]" valueproperty="affecteActeBean.LB_IMAGES[]" class="sigp2-liste" defaultproperty="affecteActeBean.LB_IMAGES_SELECT" onchange='executeBouton("PB_LESIMAGES");selectImage();'></SELECT>
--%><%
try {
  Object[] _a0 = affecteActeBean.getLB_IMAGES();
  Object _p0 = _a0[0]; // throws an exception if empty.
  %><SELECT class="sigp2-liste" name="LESIMAGES" onchange='executeBouton("PB_LESIMAGES");selectImage();' style="width : 200px;"><%
    for (int _i0 = 0; ; ) {
      if (_p0.equals(affecteActeBean.getLB_IMAGES_SELECT())) { %>
        <OPTION selected value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      else { %>
        <OPTION value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      _i0++;
      try {
        _p0 = _a0[_i0];
      }
      catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
        break;
      }
    } %></SELECT><%
}
catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
} %><%--METADATA type="DynamicData" endspan--%></TD>
                  <TD><INPUT style="visibility : hidden;" type="submit" name="PB_LESIMAGES" value="$" onchange='executeBouton("PB_LESPDF")'></TD>
                </TR>
              </TBODY>
            </TABLE>
                  </TD>
            <TD rowspan="2" width="120" align="center"><INPUT style="display:'none'" type="submit" name="PB_AFFECTER" value="Affecter" onclick="affecter();" class="sigp2-Bouton-100"></TD>
          </TR>
          <TR>
            <TD>
                  <TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
              <TBODY>
                <TR>
                        <TD width="90" style="font-size : x-small;font-weight : bold;" nowrap valign="bottom">N° Acte dispo:</TD>
                        <TD width="300">
                  <TABLE border="0" cellpadding="0" cellspacing="0">
        <TBODY>
          <TR>
            <TD style="font-family : monospace;" class="sigp2"> Mairie Centre Statut Type Année    Numéro</TD>
          </TR>
          <TR>
            <TD nowrap>
<SELECT name="LESDEBUTACTES" onchange='executeBouton("PB_DEBUTACTE");'
					class="sigp2-liste" style="width : 250px;">
					<%= affecteActeBean.forComboHTML(affecteActeBean.getLB_ACTES(),affecteActeBean.getLB_ACTES_SELECT()) %>
				</SELECT>

<SELECT name="LESNUMACTES" onchange="selectActe();"
															class="sigp2-liste" style="width : 60px;">
															<%= affecteActeBean.forComboHTML(affecteActeBean.getLB_ACTES_NUM(),affecteActeBean.getLB_ACTES_NUM_SELECT()) %>
														</SELECT>
<INPUT style="visibility : hidden;" type="submit" name="PB_DEBUTACTE" value="$" >

</TD>
          </TR>
        </TBODY>
      </TABLE>
                  </TD>
                </TR>
              </TBODY>
            </TABLE>
                  </TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
          </TR>
    <TR>
      <TD align="right" style="font-weight : bold;" colspan="2" nowrap id="texte"></TD>
      <TD><MARQUEE id="progress" direction="right"></MARQUEE></TD>
    </TR>
  </TBODY>
</TABLE>
      </TD>
    </tr>
    <% if (affecteActeBean != null && affecteActeBean.getMessageErreur()!= null) { %>
    <TR>
      <TD align="center" nowrap> <%=affecteActeBean.getMessageErreur()%></TD>
    </TR>
    <%		affecteActeBean.setMessageErreur(null);
    	} %>
    <tr>
      <TD width="100%" nowrap>
<%if (affecteActeBean.getCurrentPDF() != null && affecteActeBean.getCurrentPDF().length() >0 ) {%><EMBED type="application/pdf" src="<%=affecteActeBean.getVoirPDFUrl()%>" width="100%" height="100%">
<%}%>
</TD>
    </tr>
</TABLE>
</FORM>
</BODY>
</HTML>