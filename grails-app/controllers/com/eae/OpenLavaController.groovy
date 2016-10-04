package com.eae

import org.codehaus.groovy.grails.web.json.JSONArray
import org.codehaus.groovy.grails.web.json.JSONObject
import mongo.MongoFactory

class OpenLavaController {

    def openLavaService
    def mongoService

    static allowedMethods = [testSubmitOpenLava: ['POST']]

    def mongoParams(){
        final String MONGO_URL = grailsApplication.config.com.eae.mongoURL;
        final String MONGO_USER = grailsApplication.config.com.eae.mongoUser;
        final String MONGO_USER_DB_IDENTIFICATION = grailsApplication.config.com.eae.mongoUserdatabse;
        final char[] MONGO_PASSWORD = grailsApplication.config.com.eae.mongoPassword;

        return [MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD];
    }

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

    def submitTransmartBatchOpenLava = {
        final String scriptDir = getWebAppFolder() + '/Scripts/'
        final String sparkScriptsDir = grailsApplication.config.com.sparkScriptsDir

        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)

        def mongoDocumentID = jsonParams.mongoDocumentID
        def workflowSpecificParameters = jsonParams.workflowSpecificParameters
        String dataFileName = jsonParams.dataFileName
        String additionalFileName = jsonParams.additionalFileName
        String workflow = jsonParams.workflow

        transmartSubmitService.sparkSubmit(scriptDir, sparkScriptsDir, workflow, dataFileName, additionalFileName, workflowSpecificParameters, mongoDocumentID);
    }

    def test(){

        final def (MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD) = mongoParams();
        def url = MONGO_URL.split(':');
        def eaeDatabase= "eae";
        def collection = "users";
        def mongoClient = mongoService.getMongoCollection(url[0], url[1], MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD, eaeDatabase, collection);

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
