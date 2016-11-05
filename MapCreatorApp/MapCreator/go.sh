#!/bin/bash

mvn package

java -cp target/MapCreator-1.0-SNAPSHOT.jar inz.mapcreator.Main
