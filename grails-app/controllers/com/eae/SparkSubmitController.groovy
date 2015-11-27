package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class SparkSubmitController {

    def sparkSubmitService

    static allowedMethods = [runSubmit:'POST']

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        final sparkScriptsDir = grailsApplication.config.com.sparkScriptsDir;

        def myParams =  request.reader.text;
        def jsonParams = new JSONObject(myParams);
        String workflow = jsonParams.workflow;
        String dataFileName = jsonParams.dataFileName;
        String additionalFileName = jsonParams.additionalFileName;
        def workflowSpecificParameters = jsonParams.workflowSpecificParameters; // sparkSubmitService.prepareSpecificParameters(params)
        def mongoDocumentID = jsonParams.mongoDocumentID;

        sparkSubmitService.sparkSubmit(scriptDir, sparkScriptsDir, workflow, dataFileName, additionalFileName, workflowSpecificParameters, mongoDocumentID);

        render "OK"
    }

    /**
     *   Gets the directory where all the R scripts are located
     *
     *   @return {str}: path to the script folder
     */
    def getWebAppFolder() {
        return request.getSession().getServletContext().getRealPath("");
    }

}
