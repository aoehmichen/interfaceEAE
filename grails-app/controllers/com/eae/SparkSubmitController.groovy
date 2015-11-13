package com.eae

class SparkSubmitController {

    def sparkSubmitService

    def runSubmit = {
        //def workflow = params.workflow;
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        String workflow = params.workflow;
        String dataFileName = params.dataFileName;
        def workflowSpecificParameters = sparkSubmitService.prepareSpecificParameters(params)
        def mongoDocumentID = params.mongoDocumentID

        sparkSubmitService.sparkSubmit(scriptDir,workflow,dataFileName, workflowSpecificParameters, mongoDocumentID)

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
