package com.eae
import grails.converters.JSON

class SparkSubmitController {

    def sparkSubmitService


    static allowedMethods = [runSubmit:'POST']

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        response.setContentType("application/json");

        println(request.reader.text )

        def myParams =  request.reader.text as JSON
        println(out)

        String workflow = myParams.workflow;
        String dataFileName = myParams.dataFileName;
        String additionalFileName = myParams.additionalFileName
        def workflowSpecificParameters = myParams.workflowSpecificParameters; // sparkSubmitService.prepareSpecificParameters(params)
        def mongoDocumentID = myParams.mongoDocumentID;

        sparkSubmitService.sparkSubmit(scriptDir, workflow, dataFileName, additionalFileName, workflowSpecificParameters, mongoDocumentID)

        render "OK"
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
