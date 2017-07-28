package com.eae

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import mongo.MongoFactory

import java.text.SimpleDateFormat

class OpenLavaController {

    def openLavaService
    def utilitiesService

    static allowedMethods = [submitJob: ['POST'], killSparkJob: ['GET'], retrieveSparkLog:['GET']]

    /**
     *  Method to submit a standard job to openLava clusters
     */
    def submitJob = {
        final String scriptDir = getScriptsFolder();

        final String localDataStore = grailsApplication.config.com.eae.localDataStore;

        def jsonParams = new JSONObject(request.reader.text);

        def UID = jsonParams.id;
        String dockerHostIp = jsonParams.host_ip;
        String dockerSshPort = jsonParams.ssh_port;
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
        openLavaService.openLavaBsub(computationType, cluster, scriptDir, UID, zipFileToRetrieve, mainFile, configFileNameFullName, dockerHostIp, dockerSshPort)
        render "OK"
    }

    /**
     *  Method to submit a standard job to openLava clusters
     */
    def killSparkJob = {
        final String scriptDir = getScriptsFolder();

        def jsonParams = new JSONObject(request.reader.text);
        def computationType = "KillSpark";
        def cluster = "spark";
        def UID = "1234";
        def zipFileToRetrieve = "None";
        def mainFile = "None";
        def configFileNameFullName = "None"
        def dockerHostIp = "";
        def dockerSshPort = "";

        openLavaService.openLavaBsub(computationType, cluster, scriptDir, UID, zipFileToRetrieve, mainFile, configFileNameFullName, dockerHostIp, dockerSshPort)
        render "Spark jobs killed"
    }

    /**
     *  Method to submit a standard job to openLava clusters
     */
    def retrieveSparkLog = {
        final String scriptDir = getScriptsFolder();

        def jsonParams = new JSONObject(request.reader.text);
        def computationType = "RetrieveSparkLog";
        def cluster = "spark";
        def dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        def UID = "SparkLogRetrievale_" + dateFormat.format(date).toString();
        def zipFileToRetrieve = "None";
        def mainFile = "None";
        def configFileNameFullName = "None"
        def dockerHostIp = jsonParams.host_ip;
        def dockerSshPort = jsonParams.host_port;

        openLavaService.openLavaBsub(computationType, cluster, scriptDir, UID, zipFileToRetrieve, mainFile, configFileNameFullName, dockerHostIp, dockerSshPort)
        render "Log sent"
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
