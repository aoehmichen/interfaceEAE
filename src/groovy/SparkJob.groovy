import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

/**
 * Created by Axel on 10/11/2015.
 */
class SparkJob {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Say Hello to the World and display the date/time
        log.info("Hello World! - " + new Date());
    }
}
