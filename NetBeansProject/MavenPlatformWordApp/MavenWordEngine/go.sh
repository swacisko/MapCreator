#!/bin/bash


mvn package

java -cp target/MavenWordEngine-1.0-SNAPSHOT.jar com.mycompany.mavenwordengine.Inzynierka
