#!/bin/bash

#BDG_FILESYSTEM_PATH="/var"
BDG_FILESYSTEM_PATH=$1

PGUSER=$2
PGPASSWORD=$3
export PGPASSWORD PGUSER
ANT_ZIP="apache-ant-1.9.5-bin.zip"
ANT_UNZIP="apache-ant-1.9.5"

LIQUIBASE_ZIP="liquibase-3.4.2-bin.zip"
LIQUIBASE_UNZIP="liquibase-3.4.2-bin"

#NOM_DOSSIER=demo1
#ANT_HOME="$BDG_FILESYSTEM_PATH/bdg/bin/tools/ant"
#BUILD_FILE="$BDG_FILESYSTEM_PATH/bdg/update_db/db.build_MAJ.xml"

#ANT_LIB="$BDG_FILESYSTEM_PATH/lib"

echo "Création/MAJ de la structure de fichier BDG dans $BDG_FILESYSTEM_PATH/ylyade"

mkdir $BDG_FILESYSTEM_PATH/ylyade
mkdir $BDG_FILESYSTEM_PATH/ylyade/bin
mkdir $BDG_FILESYSTEM_PATH/ylyade/bin/tools
mkdir $BDG_FILESYSTEM_PATH/ylyade/bin/clients
mkdir $BDG_FILESYSTEM_PATH/ylyade/data
mkdir $BDG_FILESYSTEM_PATH/ylyade/lib
mkdir $BDG_FILESYSTEM_PATH/ylyade/data/users
mkdir $BDG_FILESYSTEM_PATH/ylyade/system
mkdir $BDG_FILESYSTEM_PATH/ylyade/system/db_dumps
mkdir $BDG_FILESYSTEM_PATH/ylyade/system/backup
mkdir $BDG_FILESYSTEM_PATH/ylyade/tmp
mkdir $BDG_FILESYSTEM_PATH/ylyade/update
mkdir $BDG_FILESYSTEM_PATH/ylyade/update_db
mkdir $BDG_FILESYSTEM_PATH/ylyade/update_db/sql
mkdir $BDG_FILESYSTEM_PATH/ylyade/update_db/manuel
mkdir $BDG_FILESYSTEM_PATH/ylyade/update_db/manuel/sql
mkdir $BDG_FILESYSTEM_PATH/ylyade/update_db/manuel/sql_archives

echo "Copies des 'outils'"
cp ./tools/* $BDG_FILESYSTEM_PATH/ylyade/bin/tools/
echo "Copies des 'fichiers' de mise à jour de la base de données"
cp ./update_db/* $BDG_FILESYSTEM_PATH/ylyade/update_db/
cp ./update_db/sql/* $BDG_FILESYSTEM_PATH/ylyade/update_db/sql/

cd $BDG_FILESYSTEM_PATH/ylyade/bin/tools
unzip $ANT_ZIP
unzip $LIQUIBASE_ZIP -d $LIQUIBASE_UNZIP
rm -rf $BDG_FILESYSTEM_PATH/ylyade/bin/tools/ant
rm -rf $BDG_FILESYSTEM_PATH/ylyade/bin/tools/liquibase
mv $ANT_UNZIP ant
mv $LIQUIBASE_UNZIP liquibase
cd $BDG_FILESYSTEM_PATH/ylyade/



