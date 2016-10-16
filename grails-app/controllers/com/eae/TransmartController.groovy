package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class TransmartController {

    def transmartService
    def utilitiesService

    static allowedMethods = [runSubmit:'POST']

    /**
     *   Execute the spark-submit job for teh specified workflow
     *
     *   @return {str}: OK if submitted, ERROR if unknown script
     */
    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        final String sparkScriptsDir = grailsApplication.config.com.eae.sparkScriptsDir;
        final String remoteHost = request.getRemoteAddr();
        final String localDataStore = grailsApplication.config.com.eae.localDataStore;

        def myParams =  request.reader.text;
        def jsonParams = new JSONObject(myParams);
        String workflowType = jsonParams.workflowType;

        if(workflowType == "SQL" || workflowType == "NoSQL"){
//            String dataFileName = jsonParams.dataFileName
//            String additionalFileName = jsonParams.additionalFileName
            String workflow = jsonParams.workflow;
            String configs =  jsonParams.workflowSpecificParameters;
            String zipFileName = jsonParams.zipFile;
            def mongoDocumentID = jsonParams.mongoDocumentID;
            def configFileName = mongoDocumentID + "-config.txt";

            if(zipFileName != "") {
                utilitiesService.retrieveZipFile(scriptDir, zipFileName, remoteHost, localDataStore)
            }
            utilitiesService.writeConfigFile(localDataStore, configFileName, configs)
            transmartService.sparkSubmit( scriptDir, "Spark",  mongoDocumentID,  zipFileName,  workflow, configFileName );
        }
//        else if(jsonParams.workflowType == "NoSQL"){
//            String mongoDocumentID = jsonParams.mongoDocumentID
//            String workflowSpecificParameters = jsonParams.workflowSpecificParameters
//            sparkSubmitService.sparkSubmit(scriptDir, sparkScriptsDir, workflow, "", "", workflowSpecificParameters, mongoDocumentID)
//        }
        else{
            render "ERROR - Unknown Workflow Type: " + jsonParams.workflowType
        }

        render "OK"
    }

    /**
     *   Gets the directory where all the scripts are located
     *
     *   @return {str}: path to the script folder
     */
    def getWebAppFolder() {
        return request.getSession().getServletContext().getRealPath("")
    }
}
