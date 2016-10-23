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
        final String scriptDir = getScriptsFolder() + '/Scripts/';
        final String remoteHost = request.getRemoteAddr();
        final String localDataStore = grailsApplication.config.com.eae.localDataStore;

        def myParams =  request.reader.text;
        def jsonParams = new JSONObject(myParams);

        def UID = jsonParams.id;
        def zipFileToRetrieve = jsonParams.zip;
        def configs = jsonParams.configs;
        def configFileName = UID + "-config.txt";
        def cluster = jsonParams.cluster;
        def computationType = jsonParams.clusterType;
        def mainFile = jsonParams.mainScriptExport;

//        utilitiesService.retrieveZipFile(scriptDir, zipFileToRetrieve, remoteHost, localDataStore, UID);
//        def zipFile = localDataStore + "Job-" + UID + "/" + UID + ".zip";

        utilitiesService.writeConfigFile(localDataStore, configFileName,configs);
        openLavaService.openLavaBsub( computationType, cluster, scriptDir, UID, zipFileToRetrieve, mainFile, configFileName, remoteHost)
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
