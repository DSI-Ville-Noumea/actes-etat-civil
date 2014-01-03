var onglet = new Array();
var lien = new Array();

var nbOnglet = 0;

function ajoute(unOnglet, unLien) {
	onglet[nbOnglet] = unOnglet;
	lien[nbOnglet] = unLien;
	nbOnglet++;
}

function cacherMenu() {
	contenu.style.display="none";
}

function montrerMenu() {
	contenu.style.display="";
}

function insererMenu(unTitre) {

document.writeln('<DIV style="width : 110px;height : 20px;top : 2px;left : 2px;');
document.writeln('  position : absolute;');
document.writeln('  z-index : 1;');
document.writeln('  visibility : visible;');
document.writeln('  text-align : center;');
//document.writeln('background-color : #309098;color : white;  font-family : Arial;font-size : 16px;cursor : hand;font-weight : bold;" id="MenuClosed" onmouseover="montrerMenu()" onmouseout="cacherMenu()">');
document.writeln('background-color : #1b99a1;color : #ebebeb;font-family : Arial;font-size : 16px;cursor : pointer;font-weight : bold;" id="MenuClosed" onmouseover="montrerMenu()" onmouseout="cacherMenu()">');

document.writeln(unTitre+'<nobr>');

//document.writeln('<TABLE border="1" width="100%" id="contenu" style="display : none;color : white;text-align : center;">');
document.writeln('<TABLE border="1" id="contenu" cellspacing="0" cellpadding="0" width="100%" style="color : #ebebeb;text-align : center;background-color : #309098;display:none;">');

document.writeln('<TBODY>');

	for (var i=0;i<nbOnglet;i++) {
		document.writeln('<TR>');
//		document.writeln('<TD>');
//		document.writeln('<A href="' + lien[i] + '">'+onglet[i]+'</A>');
//		document.writeln('</TD>');

		document.writeln('<TD>');
		document.writeln('<A href="' + lien[i] + '" style="color: rgb(255,255,255);text-decoration: none">');
		document.writeln(onglet[i]);
		document.writeln('</TD>');
		document.writeln('</A>');


		document.writeln('</TR>');
	}

	

document.writeln('</TBODY>');
document.writeln('</TABLE>');

document.writeln('</DIV>');

}
