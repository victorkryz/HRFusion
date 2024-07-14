#!/bin/bash

POCO_DESTINATION_PREFIX=app/src/main/cpp/include

if [ "$1" = "" ]; then
    echo "Poco Libs root directory is expected as a parameter"
    exit 1
else
    POPCO_LIBS_ROOT="$1"
fi    

if ! test -f ${POPCO_LIBS_ROOT}/VERSION; then
  echo "${POPCO_LIBS_ROOT} doesn't seem Poco Libs root directory!"
  exit 1
fi

POCO_VERSION=$(cat ${POPCO_LIBS_ROOT}/VERSION)

echo "Poco Libs root directory assumed: ${POPCO_LIBS_ROOT}"
echo "Poco Libs version: ${POCO_VERSION}"

echo "Exstracting ..."
mkdir -p ${POCO_DESTINATION_PREFIX}/Poco

echo "- Fondation -> ${POCO_DESTINATION_PREFIX}/Poco" 
cp ${POPCO_LIBS_ROOT}/Foundation/include/Poco/*.h ${POCO_DESTINATION_PREFIX}/Poco

echo "-- Dynamic -> ${POCO_DESTINATION_PREFIX}/Poco"
mkdir -p ${POCO_DESTINATION_PREFIX}/Poco/Dynamic
cp ${POPCO_LIBS_ROOT}/Foundation/include/Poco/Dynamic/*.h ${POCO_DESTINATION_PREFIX}/Poco/Dynamic

echo "-- Util -> ${POCO_DESTINATION_PREFIX}/Poco"
mkdir -p ${POCO_DESTINATION_PREFIX}/Poco/Util
cp ${POPCO_LIBS_ROOT}/Util/include/Poco/Util/*.h  ${POCO_DESTINATION_PREFIX}/Poco/Util

echo "-- Data - -> ${POCO_DESTINATION_PREFIX}/Poco"
mkdir -p  ${POCO_DESTINATION_PREFIX}/Poco/Data
cp ${POPCO_LIBS_ROOT}/Data/include/Poco/Data/*.h  ${POCO_DESTINATION_PREFIX}/Poco/Data

echo "--- SQLite -> ${POCO_DESTINATION_PREFIX}/Poco/Data"
mkdir -p  ${POCO_DESTINATION_PREFIX}/Poco/Data/SQLite
cp ${POPCO_LIBS_ROOT}/Data/SQLite/include/Poco/Data/SQLite/*.h ${POCO_DESTINATION_PREFIX}/Poco/Data/SQLite


echo "Done!"



