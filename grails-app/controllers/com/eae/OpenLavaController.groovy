package com.eae

class OpenLavaController {

    def openLavaService

    static allowedMethods = [testSubmitOpenLava: ['POST', 'GET']]

    def testSubmitOpenLava = {
        openLavaService.openLavaBsub()
        render "OK"
    }

}
