package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class SparkSubmitController {

    def sparkSubmitService

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        println(response)
        println(getResponse())

        JSONObject myParams = new JSONObject(getResponse().toString())

        String workflow = myParams.workflow;
        String dataFileName = myParams.dataFileName;
        String additionalFileName = myParams.additionalFileName
        def workflowSpecificParameters = myParams.workflowSpecificParameters; // sparkSubmitService.prepareSpecificParameters(params)
        def mongoDocumentID = myParams.mongoDocumentID;

        sparkSubmitService.sparkSubmit(scriptDir, workflow, dataFileName, additionalFileName, workflowSpecificParameters, mongoDocumentID)

        render workflow
    }


    /**
     *   Gets the directory where all the R scripts are located
     *
     *   @return {str}: path to the script folder
     */
    def getWebAppFolder() {
        return request.getSession().getServletContext().getRealPath("")
    }

}
