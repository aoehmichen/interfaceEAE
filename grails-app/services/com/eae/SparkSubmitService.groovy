package com.eae

class SparkSubmitService {

    def sparkSubmit(String scriptDir, String sparkScriptsDir, String workflow, String dataFileName, String additionalFileName, String workflowSpecificParameters, String mongoDocumentID ) {
        def script = scriptDir + 'executeSparkJob.sh'

        String workflowFileName = sparkScriptsDir + workflow + ".py"
        //String dataFileName = "listOfGenes.txt"
        //String workflowSpecificParameters = "Bonferroni"
        //String mongoDocumentID = "564117e52dee92247e7ca3a1"

        def scriptFile = new File(script)
        if (scriptFile.exists()) {
            if (!scriptFile.canExecute()) {
                scriptFile.setExecutable(true)
            }
        } else {
            log.error('The Script file spark submit wasn\'t found')
        }
        
        def executeCommand = script + " " + workflowFileName + " " + dataFileName + " " + additionalFileName + " " + workflowSpecificParameters + " " + mongoDocumentID
        println(executeCommand)
        executeCommand.execute().waitFor()

        return 0
    }

    def prepareSpecificParameters(params){


        return "toto"
    }

}
