#!/bin/bash
# We build the war file to be deployed in the tomcat
grails war

# We stop the tomcat instance
sudo service tomcat7 stop

#move the script to the home directory of the user
rm ~/putToHDFS.sh
cp web-app/Scripts/putToHDFS.sh ~
chmod +x ~/putToHDFS.sh

#We clear the logs to have a cleaner build every time
sudo rm -rf /var/lib/tomcat7/webapps/interfaceEAE*
sudo rm -rf /var/lib/tomcat7/logs/*

# We move the war file to the tomcat directory and we restart the tomcat7
sudo mv target/interfaceEAE.war /var/lib/tomcat7/webapps/
sudo service tomcat7 start
