package com.eae

import org.codehaus.groovy.grails.web.json.JSONObject

class OpenLavaService {

    def createConfigFile(String configFileName, JSONObject jsonParams){


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
}
