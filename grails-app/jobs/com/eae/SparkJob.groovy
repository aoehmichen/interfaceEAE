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
        log.info "Hello World! - " + new Date();
    }
}
