package com.eae

class OpenLavaService {

    def openLavaBsub(){

        def sout = new StringBuilder()
        def serr = new StringBuilder()
        def proc = '/home/ubuntu/print2.sh'.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        println "out> $sout err> $serr"
        return 0
    }
}
