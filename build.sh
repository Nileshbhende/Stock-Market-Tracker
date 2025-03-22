#!/bin/sh
export JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))
export PATH=$JAVA_HOME/bin:$PATH

chmod +x mvnw
./mvnw clean package
