<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="nc.mairie.etatcivil.servlets.SuperServlet"%>
<%@page contentType="text/html;charset=UTF-8"%>
<HTML>
<HEAD>
<META name="GENERATOR" content="IBM WebSphere Page Designer V3.5.3 for Windows">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/sigp2.css" rel="stylesheet" type="text/css">
<TITLE>Gestion des actes</TITLE>
<SCRIPT language="javascript" src="js/menu.js"></SCRIPT>
<SCRIPT language="JavaScript">

//afin de sÃ©lectionner un Ã©lÃ©ment dans une liste
function executeBouton(nom)
{
document.formu.elements[nom].click();
}

// afin de mettre le focus sur une zone prÃ©cise
function setfocus(nom)
{
document.formu.elements[nom].focus();
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
<BODY bgcolor="#ebebeb" style="background-repeat:no-repeat;background-image : url(images/Log-Nea3_120.gif);background-position : 0px 15px;margin-top : 0px;margin-left : auto;margin-right : auto;margin-bottom : auto;">
<script>
	ajoute('Affectation', '<%=request.getContextPath()%>/AffecteActeServlet');
	ajoute('Gestion', '<%=request.getContextPath()%>/GestionActePDFServlet');
	insererMenu('Menu Actes');
</script>
<jsp:useBean class="nc.mairie.etatcivil.servlets.GestionActePDFBean" id="gestionActePDFBean" scope="session"></jsp:useBean>
<FORM name="formu" method="POST" action="GestionActePDFServlet" style="text-align : center;">
<TABLE width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">

    <tr>
      <TD style="background-repeat : no-repeat;" nowrap height="120" align="center">
      <TABLE border="0" class="sigp2" cellpadding="0" cellspacing="0">
  <TBODY>
          <TR>
      <TD>
      <TABLE border="1" class="sigp2">
        <TBODY>
          <TR>
            <TD><%--METADATA type="DynamicData" startspan
<SELECT size="6" name="LB_LESPDF" class="sigp2-liste" style="width : 250px;" loopproperty="gestionActePDFBean.LB_LESPDF()" itemproperty="gestionActePDFBean.LB_LESPDF()" valueproperty="gestionActePDFBean.LB_LESPDF()" dynamicelement defaultproperty="gestionActePDFBean.LB_LESPDF_SELECT" onchange='executeBouton("PB_LESPDF")'></SELECT>
--%><%
try {
  Object _p0 = gestionActePDFBean.getLB_LESPDF(0); // throws an exception if empty.
  %><SELECT class="sigp2-liste" name="LB_LESPDF" onchange='executeBouton("PB_LESPDF")' size="6" style="width : 250px;"><%
    for (int _i0 = 0; ; ) {
      if (_p0.equals(gestionActePDFBean.getLB_LESPDF_SELECT())) { %>
        <OPTION selected value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      else { %>
        <OPTION value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      _i0++;
      try {
        _p0 = gestionActePDFBean.getLB_LESPDF(_i0);
      }
      catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
        break;
      }
    } %></SELECT><%
}
catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
} %><%--METADATA type="DynamicData" endspan--%></TD>
            <TD><BR>
            <INPUT type="submit" name="PB_DROITE" value="->"><BR>
            <INPUT type="submit" name="PB_GAUCHE" value="<-"><BR>
            <INPUT style="visibility : hidden;" type="submit" name="PB_LESPDF" value="$" onchange='executeBouton("PB_LESPDF")'></TD>
            <TD><%--METADATA type="DynamicData" startspan
<SELECT size="6" name="LB_LESPDFCHOISIS" class="sigp2-liste" style="width : 250px;" loopproperty="gestionActePDFBean.LB_LESPDFCHOISIS()" itemproperty="gestionActePDFBean.LB_LESPDFCHOISIS()" valueproperty="gestionActePDFBean.LB_LESPDFCHOISIS()" dynamicelement defaultproperty="gestionActePDFBean.LB_LESPDFCHOISIS_SELECT" onchange='executeBouton("PB_LESPDFCHOISIS")'></SELECT>
--%><%
try {
  Object _p0 = gestionActePDFBean.getLB_LESPDFCHOISIS(0); // throws an exception if empty.
  %><SELECT class="sigp2-liste" name="LB_LESPDFCHOISIS" onchange='executeBouton("PB_LESPDFCHOISIS")' size="6" style="width : 250px;"><%
    for (int _i0 = 0; ; ) {
      if (_p0.equals(gestionActePDFBean.getLB_LESPDFCHOISIS_SELECT())) { %>
        <OPTION selected value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      else { %>
        <OPTION value="<%= _p0 %>"><%= _p0 %></OPTION><%
      }
      _i0++;
      try {
        _p0 = gestionActePDFBean.getLB_LESPDFCHOISIS(_i0);
      }
      catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
        break;
      }
    } %></SELECT><%
}
catch (java.lang.ArrayIndexOutOfBoundsException _e0) {
} %><%--METADATA type="DynamicData" endspan--%></TD>
            <TD><BR>
            <INPUT type="submit" name="PB_HAUT" value="^" style="font-weight : bold;"><BR>
            <INPUT type="submit" name="PB_BAS" value="v"><BR>
            <INPUT style="visibility : hidden;" type="submit" name="PB_LESPDFCHOISIS" value="$"></TD>
            <TD><INPUT type="submit" name="PB_SUPPRIMER" value="Supprimer" class="sigp2-Bouton-100"><BR>
            <BR>
            <INPUT  type="submit" name="PB_FUSIONNER" value="Fusionner" class="sigp2-Bouton-100"><BR>
            <BR>
            <INPUT type="submit" name="PB_SCINDER" value="Scinder" class="sigp2-Bouton-100"></TD>
          </TR>
        </TBODY>
      </TABLE>
      </TD>
    </TR>
          <TR>
            <TD align="center" style="color : red;"><B><%=gestionActePDFBean == null || gestionActePDFBean.getMessageErreur()== null ? "" : gestionActePDFBean.getMessageErreur()%></B></TD>
          </TR>
        </TBODY>
</TABLE>
      <input type = "hidden" name= "IMAGES" value = "">
<input type = "hidden" name= "ACTES" value = "">
</TD>
    </tr>
    <tr>
      <TD width="100%" nowrap >

<%if (gestionActePDFBean.getCurrentPDF() != null && gestionActePDFBean.getCurrentPDF().length() >0 ) {%><EMBED type="application/pdf" src="<%=gestionActePDFBean.getVoirPDFUrl()%>" width="100%" height="100%">
<%}%>
</TD>
    </tr>
</TABLE>

</FORM>
</BODY>
</HTML>