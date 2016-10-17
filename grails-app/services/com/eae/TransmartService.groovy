package com.eae

class TransmartService {

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
    def sparkSubmit(String scriptDir, String computationType, String jobName, String scriptsZip, String sparkScriptsDir, String mainFileName, String configFileName) {
        def sout = new StringBuilder();
        def serr = new StringBuilder();

        def executeCommand = scriptDir + "Job"+ computationType + ".sh " + jobName + " " + scriptsZip + " " + sparkScriptsDir + " " + mainFileName + " " + configFileName;
        def proc = executeCommand.execute();
        proc.consumeProcessOutput(sout, serr);
        proc.waitForOrKill(1000);
        println jobName
        println "out> $sout err> $serr"

        if(serr.toString() == ""){
            return 0
        } else {
            log.error("The openLava submit has failed for job $jobName. Error: $serr")
            return 1
        }
    }

}
