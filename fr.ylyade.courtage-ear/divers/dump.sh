#!/bin/bash

BDG_FILESYSTEM_PATH=$1
PGUSER=$2
PGPASSWORD=$3

BDG_FILESYSTEM_PATH=/var/lgr
PGUSER=xxxx
PGPASSWORD=xxxxx

export PGPASSWORD PGUSER

DATE=$(date +"%Y-%m-%d_%H-%M-%S")

echo "=========================================================================================================================================="
echo "=========================================================================================================================================="
echo "Database ylyade => DUMP"
echo "=========================================================================================================================================="
echo "Date : $DATE"
echo "JBOSS_HOME : $JBOSS_HOME"
echo "ylyade_FILESYSTEM_PATH : $BDG_FILESYSTEM_PATH"
echo "PGUSER : $PGUSER"
echo "PGPASSWORD : $PGPASSWORD"
echo "Param 5 : $5 - $SERVEUR_CLIENT"
echo "=========================================================================================================================================="
echo "=========================================================================================================================================="

pg_dump -h localhost -p 5432 -U ylyade -F c -b -o -v -f "$BDG_FILESYSTEM_PATH/ylyade/system/db_dumps/ylyade_manuel_"$DATE".backup" ylyade

echo "DUMP OK"
echo "=========================================================================================================================================="
