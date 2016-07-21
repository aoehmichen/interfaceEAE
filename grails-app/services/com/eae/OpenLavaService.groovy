package com.eae

import grails.transaction.Transactional

@Transactional
class OpenLavaService {

    def openLavaBsub(){

        def sout = new StringBuilder(), serr = new StringBuilder()
        def proc = 'bsub -m "test-spark-master" \'bash spark.sh\''.execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        println "out> $sout err> $serr"
        return 0
    }
}
