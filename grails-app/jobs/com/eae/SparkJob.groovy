package com.eae
import org.apache.commons.logging.LogFactory
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

class SparkJob implements Job{

    private static def log = LogFactory.getLog(this)

    public SparkJob(){
    }

    static triggers = {
        simple name: 'simpleTrigger', startDelay: 1, repeatInterval: 0, repeatCount: 0
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Say Hello to the World and display the date/time

        def executeCommand = "/opt/mapr/spark/spark-1.4.1/bin/spark-submit --py-files CrossValidation.zip --master yarn-client  --num-executors 2 --driver-memory 1024m  --executor-memory 512m   --executor-cores 1 pe.py listOfGenes.txt Bonferroni 5641101f2dee92247eb07ffd >> out.txt"
        println(executeCommand)
        executeCommand.execute().waitFor()

        println("echo \"toto\" >> /home/ubuntu/hello.txt".execute().exitValue())
    }
}
