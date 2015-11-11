package com.eae

import org.quartz.*
import org.quartz.impl.StdSchedulerFactory

import static org.quartz.TriggerBuilder.newTrigger

class SparkSubmitService {

    def sparkSubmit() {

        // Let's build a quartz job
        // We get a simple scheduler
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // define the job and tie it to our HelloJob class
        JobDetail job = JobBuilder.newJob(SparkJob.class)
                .withIdentity("job1", "group1")
                .build();


        // Trigger the job to run on the next round minute
        Trigger trigger = newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date())
                .build();


        // Tell quartz to schedule the job using our trigger
        sched.start();
        sched.scheduleJob(job, trigger);
        println("Trigger started()")

//        def script = 'executeSparkJob.sh'
//
//        def scriptFile = new File(script)
//        if (scriptFile.exists()) {
//            if (!scriptFile.canExecute()) {
//                scriptFile.setExecutable(true)
//            }
//        }else {
//            log.error('The Script file spark submit wasn\'t found')
//        }
//        def executeCommand = script + " " + SparkURL + " " + workflowFileName + " " + dataFileName + " " + workflowSpecificParameters + " " + mongoDocumentID
//        println(executeCommand)
//        executeCommand.execute().waitFor()
//        return 0

    }
}
