package com.eae

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import mongo.MongoFactory

class OpenLavaController {

    def openLavaService
    def utilitiesService

    static allowedMethods = [submitJob: ['POST']]

    /**
     *  Method to submit a standard job to openLava clusters
     */
    def submitJob = {
        final String scriptDir = getScriptsFolder();

        final String localDataStore = grailsApplication.config.com.eae.localDataStore;

        def jsonParams = new JSONObject(request.reader.text);

        def UID = jsonParams.id;
        def dockerHostIp = jsonParams.host_ip;
        def dockerSshPort = jsonParams.ssh_port;
//        String remoteHost = jsonParams.serverIp;
        def zipFileToRetrieve = jsonParams.zip;
        def configs = jsonParams.configs;
        def configFileName =  UID + "-config.txt";
        def cluster = jsonParams.cluster;
        def computationType = jsonParams.clusterType;
        def mainFile = jsonParams.mainScriptExport;

        if (configs.endsWith("\n")) {
            configs = configs.substring(0, configs.length() - 1);
        }

        def configFileNameFullName = utilitiesService.writeConfigFile(localDataStore, configFileName,configs);
        openLavaService.openLavaBsub( computationType, cluster, scriptDir, UID, zipFileToRetrieve, mainFile, configFileNameFullName, dockerHostIp, dockerSshPort)
        render "OK"
    }

     /**
     *   Gets the directory where all the bash scripts are located
     *
     *   @return {str}: path to the script folder
     */
    private def getScriptsFolder() {
        return  grailsApplication.mainContext.servletContext.getRealPath("") + '/Scripts/';
    }
}
