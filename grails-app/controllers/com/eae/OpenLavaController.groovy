package com.eae

class OpenLavaController {

    def openLavaService

    static allowedMethods = [testSubmitOpenLava: ['POST', 'GET']]

    def testSubmitOpenLava = {
        log.error("Here")
        openLavaService.openLavaBsub()
        render "OK"
    }

    def submitSparkBatchOpenLava = {
        log.error("Here")
        openLavaService.openLavaBsub()
        render "OK"
    }

}
