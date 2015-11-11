package com.eae

class SparkSubmitController {

    def sparkSubmitService

    def runSubmit = {
        //def workflow = params.workflow;
        sparkSubmitService.sparkSubmit()

        render "Hello"
    }

}
