#!/bin/bash

TYPE="HORIZONTAL"
SECTION="sex"
COUNT=2

URL="localhost:8080/rest/tip/html/$SECTION/$TYPE/$COUNT"

#/rest/tip/html/{section}/{type}/{count}

echo "Call: $URL"

curl "$URL"

echo

exit 0
