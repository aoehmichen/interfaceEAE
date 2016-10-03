package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class EAEManagementController {

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
}
