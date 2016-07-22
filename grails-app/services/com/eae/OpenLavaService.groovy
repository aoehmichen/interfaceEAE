package com.eae

class OpenLavaService {

    def openLavaBsub(){

        def sout = new StringBuilder()
        def serr = new StringBuilder()
        log.error("Here 2")
        def proc = 'bsub -m "test-spark-master" \'bash spark.sh\''.execute()
        log.error("Here 3")
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        println "out> $sout err> $serr"
        return 0
    }
}
