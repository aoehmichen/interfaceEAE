package com.eae

import org.codehaus.groovy.grails.web.json.JSONArray

class OpenLavaService {

    def createConfigFile(String FileName, JSONArray jsonParams){
        File f = new File(FileName);
        int numberOfParameters = jsonParams.length()
        for(int i=0; i<numberOfParameters; i++) {
            String line = jsonParams.get(i)
            f.withWriterAppend('utf-8') { writer ->
                writer.writeLine line
            }
        }
        return 0
    }

    def openLavaBsub(String computationType, String scriptDir, String jobName, String scriptsZipName, String mainFileName, String configFileName){
        def sout = new StringBuilder()
        def serr = new StringBuilder()

        def executeCommande = scriptDir + "/" + "Job"+ computationType + ".sh " + jobName + " " + scriptsZipName + " " + mainFileName + " " + configFileName
        def proc = executeCommande.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        println "out> $sout err> $serr"

        return 0
    }

    def retrieveClusters(String scriptDir, String openLavaEnv){
        def sout = new StringBuilder()
        def serr = new StringBuilder()
        def executeCommande = scriptDir + "Clusters.sh " + openLavaEnv
        def proc = executeCommande.execute()
        proc.consumeProcessOutput(sout, serr)
        println("stout:" + sout)
        println("serr:" + serr)

    }
}
