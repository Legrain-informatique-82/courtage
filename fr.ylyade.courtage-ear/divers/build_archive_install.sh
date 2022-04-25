#!/bin/bash

echo "Création de l'archive d'installation ..."

INSTALL_FILE=setup_ylyade.tar.gz

mkdir $WORKSPACE/make_install

cp -r $WORKSPACE/svn/fr.ylyade.courtage-ear/target/fr.ylyade.courtage-ear.ear $WORKSPACE/make_install/

cp -r $WORKSPACE"/svn/fr.ylyade.courtage-ear/divers/sql/dump_postgresql/backup_ylyade.backup" $WORKSPACE/make_install/

cp -r $WORKSPACE"/svn/fr.ylyade.courtage-ear/lib/postgresql-9.3-1101.jdbc41.jar" $WORKSPACE/make_install/

cp -r $WORKSPACE"/svn/fr.ylyade.courtage-ear/divers/"*.sh $WORKSPACE/make_install/

cp -r $WORKSPACE/svn/fr.ylyade.courtage-ear/install $WORKSPACE/make_install/

echo "Version SVN : r$SVN_REVISION\nDate : $(date)\nBuild : $BUILD_TAG" > $WORKSPACE/make_install/version.txt

cd $WORKSPACE
rm -f $INSTALL_FILE
tar -zcvf $INSTALL_FILE make_install

echo "Fin de la création de l'archive d'installation."
