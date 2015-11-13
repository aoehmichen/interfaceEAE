package com.eae

class SparkSubmitController {

    def sparkSubmitService

    def runSubmit = {
        //def workflow = params.workflow;
        final String scriptDir = getWebAppFolder() + '/Scripts/';
        String ans = params.SPARK_URL

        sparkSubmitService.sparkSubmit(scriptDir)

        render ans
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
