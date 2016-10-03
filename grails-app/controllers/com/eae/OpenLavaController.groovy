package com.eae

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject

class OpenLavaController {

    def openLavaService


    static allowedMethods = [testSubmitOpenLava: ['POST']]

    def testSubmitOpenLava = {
        def jobName = "test"
        def scriptsZip = "toto.zip"
        def mainFile = "toto.py"
        def configFileName= "config_" + jobName + ".txt"

        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)

        jsonParams = new JSONObject();
        JSONArray list = new JSONArray();
        list.add("msg 1");
        list.add("msg 2");
        list.add("msg 3");
        jsonParams.put("Configurations", list);

        openLavaService.createConfigFile(configFileName, (JSONArray)jsonParams["Configurations"])
        openLavaService.openLavaBsub("Test", getWebAppFolder(), jobName,  scriptsZip,  mainFile, configFileName )
        render "OK"
    }

    def submitSparkBatchOpenLava = {
        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)

        openLavaService.createConfigFile(configFileName, (JSONArray)jsonParams["Configurations"])
        openLavaService.openLavaBsub("Spark", getWebAppFolder(), jobName,  scriptsZip,  mainFile, configFileName)
        render "OK"
    }

    def submitGPUBatchOpenLava = {
        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)

        openLavaService.openLavaBsub("GPU", getWebAppFolder(), jobName,  scriptsZip,  mainFile, configFileName)
        render "OK"
    }

    /**
     *   Gets the directory where all the bash scripts are located
     *
     *   @return {str}: path to the script folder
     */
    private def getWebAppFolder() {
        return  grailsApplication.mainContext.servletContext.getRealPath('web-app');
    }
}
