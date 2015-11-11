package com.eae
import grails.plugins.rest.client.RestBuilder

import grails.transaction.Transactional
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SchedulerFactory
import org.quartz.impl.StdSchedulerFactory

@Transactional
class SparkSubmitService {

    def sparkSubmit(workflow, params, dataFileName , workflowSpecificParameters) {

        // Let's build a quartz job
        // We get a simple scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(SparkJob.class)
                .withIdentity("job1", "group1")
                .build();


        def script = 'executeSparkJob.sh'

        def scriptFile = new File(script)
        if (scriptFile.exists()) {
            if (!scriptFile.canExecute()) {
                scriptFile.setExecutable(true)
            }
        }else {
            log.error('The Script file spark submit wasn\'t found')
        }
        def executeCommand = script + " " + SparkURL + " " + workflowFileName + " " + dataFileName + " " + workflowSpecificParameters + " " + mongoDocumentID
        println(executeCommand)
        executeCommand.execute().waitFor()
        return 0

    }
}
