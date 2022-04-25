#!/bin/bash

#JAVA_HOME=/var/opt/java/jdk1.7.0_51_x64
JAVA_HOME=/var/opt/java/jdk1.8.0_111_x64
export JAVA_HOME

#
# Remplacer le source de la librairie "fr.legrain.bdg.lib.osgi" par celui du dépot gerrit
# --- A CHANGER --- si on conserve l'utilisation de gerrit et un depot à part pour les librairies partager,
#      il faudra supprimer complètement ces projets du dépot principal
#
rm -rf $WORKSPACE/svn/fr.legrain.bdg.lib.osgi
cp -rap $WORKSPACE/svn_lib-legrain/fr.legrain.bdg.lib.osgi $WORKSPACE/svn/

#
# Mise à jour du numéro de version dans le "A propos" avant compilation
#
$WORKSPACE/svn/fr.ylyade.courtage-ear/divers/update_about_dialog.sh

#
# Génération du changelog
#
#$WORKSPACE/svn/fr.ylyade.courtage-ear/divers/changelog/changelog_bugzilla.rb

#
# Serveur
#
#service jboss stop



#mv $WORKSPACE/svn/fr.ylyade.courtage-ejb $WORKSPACE/svn/fr.ylyade.courtage/
#mv $WORKSPACE/svn/fr.ylyade.courtage-web $WORKSPACE/svn/fr.ylyade.courtage/
#mv $WORKSPACE/svn/fr.ylyade.courtage-ear $WORKSPACE/svn/fr.ylyade.courtage/

cd $WORKSPACE/svn/fr.ylyade.courtage
#/var/opt/maven/apache-maven-3.2.3/bin/mvn -X -U clean install
/var/opt/maven/apache-maven-3.2.3/bin/mvn -U clean install
#/var/opt/maven/apache-maven-3.2.3/bin/mvn -o site


# Déplacer dans "post build task"
#$WORKSPACE/svn/fr.legrain.bdg.ear/divers/update_jboss.sh

mv $WORKSPACE/svn/fr.ylyade.courtage/fr.ylyade.courtage-ejb $WORKSPACE/svn/
mv $WORKSPACE/svn/fr.ylyade.courtage/fr.ylyade.courtage-web $WORKSPACE/svn/
mv $WORKSPACE/svn/fr.ylyade.courtage/fr.ylyade.courtage-ear $WORKSPACE/svn/

$WORKSPACE/svn/fr.ylyade.courtage-ear/divers/build_archive_install.sh