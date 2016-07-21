package com.eae

class OpenLavaController {

    def openLavaService

    static allowedMethods = [testSubmitOpenLava:'POST']

    def testSubmitOpenLava = {
        openLavaService.openLavaBsub()

    }

}
