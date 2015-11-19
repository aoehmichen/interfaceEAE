package com.eae

class SparkSubmitController {

    def sparkSubmitService


    static allowedMethods = [runSubmit:'POST']

    def runSubmit = {
        final String scriptDir = getWebAppFolder() + '/Scripts/';

        println(request.reader.text.hasProperty("workflow") )
        println(request.reader.text.getClass())
        def myParams =  request.reader.text
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
