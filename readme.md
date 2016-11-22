## Installation
Install external onepk library using following command:
```
mvn install:install-file -Dfile=libonep-core-rel-1.3.0.jar -DgroupId=com.cisco.onep -DartifactId=libonep-core-rel -Dversion=1.3.0 -Dpackaging=pom
mvn install:install-file -Dfile=libthrift-0.6.1-Cisco-1.1.jar -DgroupId=org.apache.thrift -DartifactId=libthrift -Dversion=0.6.1-Cisco-1.1 -Dpackaging=jar
mvn install:install-file -Dfile=libthrift-0.6.1.jar -DgroupId=org.apache.thrift -DartifactId=libthrift -Dversion=0.6.1 -Dpackaging=jar
```
