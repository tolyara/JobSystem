import java.util.ArrayList;
import java.util.List;

public class JobExecutor {

    List<Job> periodicJobs = new ArrayList();
    List<Job> oneTimeJobs = new ArrayList();

    public void addJob(Job job, JobType jobType) {
        if (jobType.equals(JobType.ONE_TIME)) {
            this.oneTimeJobs.add(job);
        } else if (jobType.equals(JobType.PERIODIC)) {
            this.periodicJobs.add(job);
        } else {
            // do nothing
        }
    }

    public void removeJob() {

    }

    public void startJobs() {
        for (Job oneTimeJob : oneTimeJobs) {
            Thread thread = new Thread(oneTimeJob);
            thread.start();
        }
    }

}
