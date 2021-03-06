#!/bin/bash
#
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if type -p java; then
    export _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    export _java="$JAVA_HOME/bin/java"
else
    echo "java not found in execution path and JAVA_HOME not set"
    exit
fi
pushd ./
cd $DIR/..
pwd
JAVA_SWITCHES="-Dlog4j.configurationFile=$DIR/../configuration/log4j2-te.xml -Djava.net.preferIPv4Stack=true"
$_java $JAVA_SWITCHES -cp $DIR/../meta-common/build/libs/teclient-1.1.jar  com.hoddmimes.te.testclient.TestClient configuration/ClientTestScript.json
popd

