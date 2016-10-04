package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class EAEManagementController {

    def mongoService

    def mongoParams(){
        final String MONGO_URL = grailsApplication.config.com.eae.mongoURL;
        final String MONGO_USER = grailsApplication.config.com.eae.mongoUser;
        final String MONGO_USER_DB_IDENTIFICATION = grailsApplication.config.com.eae.mongoUserdatabse;
        final char[] MONGO_PASSWORD = grailsApplication.config.com.eae.mongoPassword;

        return [MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD];
    }

    /**
     * Method that allows to register a new node.
     *
     * @return {str}: Sends if the node has been registered or not.
     */
    def registerNewNode = {
        def myParams =  request.reader.text
        def jsonParams = new JSONObject(myParams)
        String workflow = jsonParams.workflow


    }

    /**
     *   Go to eAE Management Page
     */
    def goToManagementPage = {
        render template: '/eAEManagement/management'
        //return '/eAEManagement/management'
    }

    def test = {

        final def (MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD) = mongoParams();
        def url = MONGO_URL.split(':');
        String eaeDatabase= "eae";
        String collection = "users";
        def mongoClient = mongoService.getMongoCollection(url[0], url[1], MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD, eaeDatabase, collection);


    }

}
