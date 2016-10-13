package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class TransmartController {

    def transmartService

    static allowedMethods = [runSubmit:'POST']

    /**
     *   Execute the spark-submit job for teh specified workflow
     *
     *   @return {str}: OK if submitted, ERROR if unknown script
     */
    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/'
        final String sparkScriptsDir = grailsApplication.config.com.sparkScriptsDir

        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)
        String workflow = jsonParams.workflow

        if(jsonParams.workflowType == "SQL" || jsonParams.workflowType == "NoSQL"){
//            String dataFileName = jsonParams.dataFileName
//            String additionalFileName = jsonParams.additionalFileName
            String zipFile = jsonParams.zipFile;
            def workflowSpecificParameters = jsonParams.workflowSpecificParameters;
            def mongoDocumentID = jsonParams.mongoDocumentID;
            transmartService.sparkSubmit(scriptDir, sparkScriptsDir, workflow, dataFileName, additionalFileName, workflowSpecificParameters, mongoDocumentID);
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
