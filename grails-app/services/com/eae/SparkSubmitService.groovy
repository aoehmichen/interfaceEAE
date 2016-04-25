package com.eae

class SparkSubmitService {

    /**
     * Execute the spark-submit command through the execution of a bash script
     * @param scriptDir
     * @param sparkScriptsDir
     * @param workflow
     * @param dataFileName
     * @param additionalFileName
     * @param workflowSpecificParameters
     * @param mongoDocumentID
     * @return {int}: 0 if the execute went through, 1 if the spark-submit file is not accessible
     */
    def sparkSubmit(String scriptDir, String sparkScriptsDir, String workflow, String dataFileName, String additionalFileName, String workflowSpecificParameters, String mongoDocumentID ) {
        def script = scriptDir + 'executeSparkJob.sh'

        String workflowFileName = sparkScriptsDir + workflow + ".py"

        def scriptFile = new File(script);
        if (scriptFile.exists()) {
            if (!scriptFile.canExecute()) {
                scriptFile.setExecutable(true)
            }
        } else {
            log.error('The Script file spark-submit wasn\'t found')
            return 1
        }

        // We add the parameters for teh bash script
        def executeCommand = script + " " + workflowFileName + " " + dataFileName + " " + additionalFileName + " " + workflowSpecificParameters + " " + mongoDocumentID
        println(executeCommand)

        // We execute the bash script with the parameters. The reason for using a bash script is that the spark-submit gets cut off if we execute the spark-submmit suing the executeCommand()
        executeCommand.execute().waitFor()

        return 0
    }

}
