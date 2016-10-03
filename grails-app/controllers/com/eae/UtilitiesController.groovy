package com.eae

import javax.servlet.http.HttpServletResponse

class UtilitiesController {

    static allowedMethods = [isALive: ['GET']]

    /**
     * Method that allows to test if the REST Api server is alive or not
     *
     * @return {int}: Sends back 200 to tell that the server is alive.
     */
    def isALive = {
        render HttpServletResponse.SC_OK;
    }


}
