#!/bin/bash

#cron, redémarrage des serveurs jboss et mysql tous les jours à 3h
#0 3 * * * /root/reboot_jboss.sh

#JBOSS_HOME="/data1/wildfly-8.2.0.Final"
JBOSS_HOME=$4

BDG_FILESYSTEM_PATH=$1

PGUSER=$2
PGPASSWORD=$3

export PGPASSWORD PGUSER

DATE=$(date +"%Y-%m-%d_%H-%M-%S")

echo "=========================================================================================================================================="
echo "=========================================================================================================================================="
echo "WildFly (8.2+) doit être installé sur la machine"
echo "PostgreSQL (9.3+) doit être installé sur la machine"
echo "=========================================================================================================================================="
echo "Date : $DATE"
echo "JBOSS_HOME : $JBOSS_HOME"
echo "ylyade_FILESYSTEM_PATH : $BDG_FILESYSTEM_PATH"
echo "PGUSER : $PGUSER"
echo "PGPASSWORD : $PGPASSWORD"
echo "Param 5 : $5 - $SERVEUR_CLIENT"
echo "=========================================================================================================================================="
echo "=========================================================================================================================================="

echo "Initialisation et préparation du déploiement ..."

rm $BDG_FILESYSTEM_PATH/ylyade/update/*
cd install
./install.sh $BDG_FILESYSTEM_PATH $PGUSER $PGPASSWORD
cd ..
cp -r fr.ylyade.courtage-ear.ear $BDG_FILESYSTEM_PATH/ylyade/update/

cp -r backup_ylyade.backup $BDG_FILESYSTEM_PATH/ylyade/update/

cp -r *.sh $BDG_FILESYSTEM_PATH/ylyade/bin/

cp -r postgresql-9.3-1101.jdbc41.jar $BDG_FILESYSTEM_PATH/ylyade/lib/
cp -r postgresql-9.3-1101.jdbc41.jar $BDG_FILESYSTEM_PATH/ylyade/bin/tools/liquibase/lib/

rm -rf $BDG_FILESYSTEM_PATH/ylyade/update_db/*

cp -r install/update_db/* $BDG_FILESYSTEM_PATH/ylyade/update_db/

cp -r update.sh $BDG_FILESYSTEM_PATH/ylyade/update/

echo "Fin de l'initialisation => début du déploiement ..."

#echo "Mise à jour de l'application et de la bdd en arrière plan"


echo "=========================================================================================================================================="
echo "=========================================================================================================================================="

echo "Début (re)déploiement de l'application sur jboss ====== $(date)" >> /var/log/log_reboot_jboss.txt

ps -ae | grep java;ps -ae | grep sh


PID_JBOSS=$(cat $JBOSS_HOME/pid)

echo "PID_JAVA = $PID_JAVA  ==== PID_JBOSS = $PID_JBOSS"
echo "Arret de JBoss/WildFly"
$JBOSS_HOME/bin/jboss-cli.sh -c :shutdown

kill -9 $PID_JAVA $PID_JBOSS

ps -ae | grep java;ps -ae | grep sh


echo "..Dump ou création des bases de données"

if [[ `psql -tAc "SELECT 1 FROM pg_database WHERE datname='ylyade'"` == "1" ]]
then
    echo "Database ylyade already exists => DUMP"
    pg_dump -h localhost -p 5432 -U ylyade -F c -b -o -v -f "$BDG_FILESYSTEM_PATH/ylyade/system/db_dumps/ylyade_"$DATE"_ylyade.backup" ylyade
    echo "DUMP OK"
else
    echo "Database ylyade does not exist => CREATE"
    createdb -O ylyade -E UTF8 ylyade
fi

echo "..Mise à jour de la structure des bases de données 'ylyade' (tous les schémas/dossiers)"
echo "Restauration du schéma public à partir du dump de développement"
#psql -U $PGUSER -h localhost -d ylyade -c "DROP SCHEMA IF EXISTS public CASCADE"
#psql -U $PGUSER -h localhost -d ylyade -c "create schema public"
#pg_restore  -h localhost -p 5432 -U $PGUSER -d ylyade -n public -v $BDG_FILESYSTEM_PATH/ylyade/update/backup_ylyade.backup
#echo "Mise à jour des dossiers utilisateurs"
#a=$(pwd)
#cd $BDG_FILESYSTEM_PATH/ylyade/update_db
#$BDG_FILESYSTEM_PATH/ylyade/update_db/update_db_all_dossier.sh $PGUSER $PGPASSWORD $BDG_FILESYSTEM_PATH
#cd $a

echo "Mise à jour des EAR"
#Sauvegarde/archivage
mv $JBOSS_HOME/standalone/deployments/fr.ylyade.courtage-ear.ear $BDG_FILESYSTEM_PATH/ylyade/system/backup/fr.ylyade.courtage-ear.ear_$DATE.ear
#Nettoyage
rm -rf $JBOSS_HOME/standalone/deployments/*
#Déploiement des nouveau EAR
cp $BDG_FILESYSTEM_PATH/ylyade/update/fr.ylyade.courtage-ear.ear $JBOSS_HOME/standalone/deployments/


echo "Redémarrage du SGBD"
systemctl restart postgresql@9.5.service

echo "Redémarrage de JBoss/WildFly"
/etc/init.d/jboss start

ps -ae | grep java;ps -ae | grep sh

echo "Fin (re)déploiement de l'application sur jboss  ====== $(date)" >> /var/log/log_reboot_jboss.txt

