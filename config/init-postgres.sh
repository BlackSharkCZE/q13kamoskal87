#!/bin/sh

#****************************************************
#***************** CONFIGURATION ********************
#****************************************************
DATABASE_NAME="kamoska"
USER_NAME="partner.kamoska.cz"
PASSWORD="kamoska"

CREATE_DB_COMMAND="createdb"
CREATE_USER_COMMAND="createuser"
PSQL_COMMAND="psql"
#***************************************************
#***** CREATE DATABASE USER AND DATABASE ***********
#***************************************************

echo -n "Checking database $DATABASE_NAME ... "
DATABASE_INFO=$($PSQL_COMMAND -l -U postgres  | grep $DATABASE_NAME)

if [ -n "$DATABASE_INFO" ]; then
	echo "[OK]"
	OWNER=$(echo $DATABASE_INFO | awk '{print $3}')
	echo -n "Checking database owner $USER_NAME ... "
	if [ "$OWNER"!="$USER_NAME" ]; then
		echo "[WARN] - Database owner is $OWNER"
		USER_NAME="$OWNER"
	else
		echo "[OK]"
	fi
else
	echo "[ERROR]"
	echo -n "Create database owner $USER_NAME ... "
	PGPASSWORD="$PASSWORD"
	if  $CREATE_USER_COMMAND -D -E -l -P -R -S -W -U postgres "$USER_NAME"; then
		echo "[OK]"
		echo -n "Create database $DATABASE_NAME ... "
		if $CREATE_DB_COMMAND -E UTF-8 -O "$USER_NAME" -U postgres "$DATABASE_NAME"; then
			echo "[OK]"
		else
			echo "[ERROR]"
			exit 1
		fi
	else
		echo "[ERROR]"
		exit 1
	fi
fi

echo "*****************************"
echo "***** NEW DATABASE INFO *****"
echo "*****************************"
echo "Database Name: $DATABASE_NAME"
echo "Database Owner: $USER_NAME"


exit 0
