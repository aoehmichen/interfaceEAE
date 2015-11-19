package com.eae

import groovyx.net.http.ContentType
import org.codehaus.groovy.grails.web.json.JSONObject

class SparkSubmitController {

    def sparkSubmitService

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        response.setContentType(ContentType.JSON);
        println(response.contentType)
        println(response.getWriter().toString())
        println(response.outputStream)
        def out =response.getWriter().flush();
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
