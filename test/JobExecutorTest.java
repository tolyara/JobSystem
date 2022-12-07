
import main.Job;
import main.JobExecutor;
import main.JobType;
import org.junit.Assert;
import org.junit.Test;

public class JobExecutorTest {

    @Test
    public void addJobTest() {
        JobExecutor executor = new JobExecutor(2);
        Job job1 = new Job("Job1", executor, JobType.SINGLE, null);
        Job job2 = new Job("Job2", executor, JobType.SINGLE, null);

        executor.addJob(job1);
        executor.addJob(job2);
        Assert.assertEquals(2, executor.getSingleJobs().size());
    }

}