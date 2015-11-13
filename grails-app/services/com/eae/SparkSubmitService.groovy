package com.eae

class SparkSubmitService {

    def sparkSubmit(String scriptDir) {

//        println("echo \"toto\" >> /tmp/hello.txt".execute())
//
//        def executeCommand = "/opt/mapr/spark/spark-1.4.1/bin/spark-submit --py-files CrossValidation.zip --master yarn-client  --num-executors 2 --driver-memory 1024m  --executor-memory 512m   --executor-cores 1 pe.py listOfGenes.txt Bonferroni 564117e52dee92247e7ca3a1"
//        println(executeCommand)
//        executeCommand.execute().waitFor()

        def script = scriptDir +'executeSparkJob.sh'

        String workflowFileName = "pe.py"
        String dataFileName = "listOfGenes.txt"
        String workflowSpecificParameters = ""
        String mongoDocumentID = "564117e52dee92247e7ca3a1"

        def scriptFile = new File(script)
        if (scriptFile.exists()) {
            if (!scriptFile.canExecute()) {
                scriptFile.setExecutable(true)
            }
        }else {
            log.error('The Script file spark submit wasn\'t found')
        }
        def executeCommand = script + " " + workflowFileName + " " + dataFileName + " " + workflowSpecificParameters + " " + mongoDocumentID
        println(executeCommand)
        executeCommand.execute().waitFor()
        return 0

//        // Let's build a quartz job
//        // We get a simple scheduler
//        SchedulerFactory sf = new StdSchedulerFactory();
//        Scheduler sched = sf.getScheduler();
//
//        // define the job and tie it to our HelloJob class
//        JobDetail job = JobBuilder.newJob(SparkJob.class)
//                .withIdentity("job1", "group1")
//                .build();
//
//
//        // Trigger the job to run on the next round minute
//        Trigger trigger = newTrigger()
//                .withIdentity("trigger1", "group1")
//                .startAt(new Date())
//                .build();
//
//
//        // Tell quartz to schedule the job using our trigger
//        sched.start();
//        sched.scheduleJob(job, trigger);
//        println("Trigger started()")

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
