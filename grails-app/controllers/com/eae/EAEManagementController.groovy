package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class EAEManagementController {

    def mongoService
    def openLavaService

    static allowedMethods = [authenticate: ['POST'],
                             retrieveNodesStatus: ['POST'],
                             retrieveClusters: ['GET'],
                             retrieveRunningJobs: ['GET']]

    private def mongoParams(){
        final String MONGO_URL = grailsApplication.config.com.eae.mongoURL;
        final String MONGO_USER = grailsApplication.config.com.eae.mongoUser;
        final String MONGO_USER_DB_IDENTIFICATION = grailsApplication.config.com.eae.mongoUserdatabse;
        final char[] MONGO_PASSWORD = grailsApplication.config.com.eae.mongoPassword;

        return [MONGO_URL, MONGO_USER, MONGO_USER_DB_IDENTIFICATION, MONGO_PASSWORD];
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
        final String scriptDir = getScriptsFolder();
        final String openLavaEnv = grailsApplication.config.com.eae.openLavaEnv;

        def clusters = openLavaService.retrieveClusters(scriptDir, openLavaEnv);

        render clusters
    }

    /**
     * Sends back the status of each Node.
     */
    def retrieveNodesStatus = {
        final String scriptDir = getScriptsFolder();
        final String openLavaEnv = grailsApplication.config.com.eae.openLavaEnv;
        String hosts = params.nodes

        def nodesStatus = openLavaService.retrieveNodesStatus(scriptDir, openLavaEnv, hosts);

        render nodesStatus
    }

    /**
     *
     */
    def retrieveRunningJobs ={
        final String scriptDir = getScriptsFolder();
        final String openLavaEnv = grailsApplication.config.com.eae.openLavaEnv;

        def jobsStatus = openLavaService.retrieveJobsStatus(scriptDir, openLavaEnv);

        render jobsStatus
    }

    /**
     *   Gets the directory where all the bash scripts are located
     *
     *   @return {str}: path to the home folder
     */
    private def getScriptsFolder() {
        return  grailsApplication.mainContext.servletContext.getRealPath("") + '/Scripts/';
    }
}
