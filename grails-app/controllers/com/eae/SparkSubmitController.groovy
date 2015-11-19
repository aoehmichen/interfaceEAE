package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class SparkSubmitController {

    def sparkSubmitService


    static allowedMethods = [runSubmit:'POST']

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        response.setContentType("application/json");

        println( request.reader)
        println(request.reader.text)

        def out = new JSONObject(request.reader.text)
        println(out)
        JSONObject myParams = new JSONObject(response.toString())

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
