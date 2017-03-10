## Overview
Application implemented as semestral project of architecture of communication system course at FIIT STU.

Application designed to monitor changes in cisco router logging proces. In case higher amount of unsuccessful logins are made into router, router access lists are automatically updated and connection from potencial dangerous source is blocked for defined period of time. System works as network based intrusion detection system - in case one router detects higher amounts of unsuccessful login attemts access lists are updated in all registered routers.

##Technologies
Following technologies are used in project:
* Frontend is implemented with HTML + jQuery
* Javax Servlets for web REST Api
* Cisco OnePK for communication with network components [http://blogs.cisco.com/security/ciscos-onepk-part-1-introduction]

## Installation
Install external onepk library using following command:
```
mvn install:install-file -Dfile=libonep-core-rel-1.3.0.jar -DgroupId=com.cisco.onep -DartifactId=libonep-core-rel -Dversion=1.3.0 -Dpackaging=pom
mvn install:install-file -Dfile=libthrift-0.6.1-Cisco-1.1.jar -DgroupId=org.apache.thrift -DartifactId=libthrift -Dversion=0.6.1-Cisco-1.1 -Dpackaging=jar
mvn install:install-file -Dfile=libthrift-0.6.1.jar -DgroupId=org.apache.thrift -DartifactId=libthrift -Dversion=0.6.1 -Dpackaging=jar
```
