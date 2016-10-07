grails war
sudo service tomcat7 stop
sudo rm -rf /var/lib/tomcat7/webapps/interfaceEAE*
sudo rm -rf /var/lib/tomcat7/logs/*
sudo mv target/interfaceEAE.war /var/lib/tomcat7/webapps/
sudo service tomcat7 start
