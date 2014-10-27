#Pour installer JT400 dans le repo local :
mvn install:install-file -Dfile=./lib/jt400-8.2.jar -DgroupId=net.sf.jt400 -DartifactId=jt400 -Dversion=8.2 -Dpackaging=jar

#Sous linux pour monter les dossiers r�seau (en mode root)
mount -t cifs -o username=SITE-MAIRIE/***REMOVED***,pass=***REMOVED***,uid=505,gid=507,dir_mode=0775,file_mode=0775 //netmea/ETATCIVIL/TESTECETATCIVIL /home/luc/mnt/ECETATCIVIL
mount -t cifs -o username=SITE-MAIRIE/***REMOVED***,pass=***REMOVED***,uid=505,gid=507,dir_mode=0775,file_mode=0775 //netmea/ETATCIVIL/TESTECIMGSCN /home/luc/mnt/ECIMGSCN
mount -t cifs -o username=SITE-MAIRIE/***REMOVED***,pass=***REMOVED***,uid=505,gid=507,dir_mode=0775,file_mode=0775 //netmea/ETATCIVIL/TESTECPDFSCN /home/luc/mnt/ECPDFSCN

#Sous linux pour monter les dossiers r�seau
umount  /home/luc/mnt/ECETATCIVIL
umount  /home/luc/mnt/ECIMGSCN
umount  /home/luc/mnt/ECPDFSCN

#config du context.xml :
<Parameter name="REP_PDF" value="/home/luc/mnt/ECPDFSCN/" description="Espace de stockage des actes d'etat civil scanneei" override="false" />
<Parameter name="REP_ECETATCIVIL" value="/home/luc/mnt/ECETATCIVIL/" description="Espace de stockage des actes d'etat civil" override="false" />
<Parameter name="REP_SCN" value="/home/luc/mnt/ECPIMGCN/" description="Espace de stockage des images scanneei" override="false" />