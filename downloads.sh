#!/usr/bin/env bash

if [ ! -d docker/jenkins/downloads ]; then
    mkdir docker/jenkins/downloads
fi

if [ ! -f docker/jenkins/downloads/apache-maven-3.6.1-bin.tar.gz ]; then
    curl -o docker/jenkins/downloads/apache-maven-3.6.1-bin.tar.gz http://mirror.nbtelecom.com.br/apache/maven/maven-3/3.6.1/binaries/apache-maven-3.6.1-bin.tar.gz
fi