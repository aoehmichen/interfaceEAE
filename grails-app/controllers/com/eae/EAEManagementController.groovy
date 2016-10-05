package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class EAEManagementController {

    def mongoService

    static allowedMethods = [authenticate: ['POST'], retrieveClusters: ['GET']]

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
     *   Authenticate the admin and go to eAE management page if the authentication is successful
     */
    def authenticate = {
        String username = params.username
        String password = params.password

        final def (MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD) = mongoParams();
        def url = MONGO_URL.split(':');
        String eaeDatabase= "eae";
        String collection = "users";
        def res = mongoService.checkUser((String)url[0], (String)url[1], (String)MONGO_USER, (String)MONGO_USER_DB_IDENTIFICATION, (char[])MONGO_PASSWORD, eaeDatabase, collection, username, password);

        if( res == "OK"){
            render template: '/eAEManagement/management'
        }else {
            render res
        }
    }

    /**
     *  Retrieve clusters and the associated nodes
     */
    def retrieveClusters = {

    }
}
