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
            String workflow = jsonParams.workflow;
            String configs =  jsonParams.workflowSpecificParameters;
            String dataZipFileName = jsonParams.dataZipFile;
            def UUID = jsonParams.mongoDocumentID;
            def configFileName = UUID + "-config.txt";
            String zipFile = "None";

            if(dataZipFileName != "") {
                utilitiesService.retrieveZipFile(scriptDir, dataZipFileName, remoteHost, localDataStore, UUID);
                zipFile = localDataStore + UUID + "/" + dataZipFileName;
            }
            def configFile = utilitiesService.writeConfigFile(localDataStore, configFileName, configs + " " + UUID)

            String jobName = "tranSMART-"+ UUID;
            transmartService.sparkSubmit(scriptDir, "Transmart", jobName, zipFile, sparkScriptsDir, workflow, configFile);
        }
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
