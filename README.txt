
FriendlySNMP and snmp4j recent library is no where to be found in Maven Repositories anywhre.
Thus we need install them to our local development PC, or to you office artifactory.

Download all the library from :
http://www.friendlysnmp.org/download/FriendlySNMP-3.0.zip

Extract all Jar in that zip into one directory and execute the following commands :
mvn install:install-file -Dfile=FriendlySNMP-3.0.jar -DgroupId=friendlysnmp -DartifactId=friendlysnmp -Dversion=3.0 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=snmp4j-2.3.1.jar -DgroupId=org.snmp4j -DartifactId=snmp4j -Dversion=2.3.1 -Dpackaging=jar -DgeneratePom=true
mvn install:install-file -Dfile=snmp4j-agent-2.3.0.jar -DgroupId=org.snmp4j -DartifactId=snmp4j-agent -Dversion=2.3.0 -Dpackaging=jar -DgeneratePom=true