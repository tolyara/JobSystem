import java.util.ArrayList;
import java.util.List;

public class JobExecutor {

    private List<Job> periodicJobs = new ArrayList();
    private List<Job> singleJobs = new ArrayList();

    public List<Job> getPeriodicJobs() {
        return periodicJobs;
    }

    public void setPeriodicJobs(List<Job> periodicJobs) {
        this.periodicJobs = periodicJobs;
    }

    public List<Job> getSingleJobs() {
        return singleJobs;
    }

    public void setSingleJobs(List<Job> singleJobs) {
        this.singleJobs = singleJobs;
    }

    public void addJob(Job job, JobType jobType) {
        if (jobType.equals(JobType.SINGLE)) {
            this.singleJobs.add(job);
        } else if (jobType.equals(JobType.PERIODIC)) {
            this.periodicJobs.add(job);
        } else {
            // do nothing
        }
    }

    public void removeJob() {

    }

    public void startJobs() {
        for (Job oneTimeJob : singleJobs) {
            Thread thread = new Thread(oneTimeJob);
            thread.start();
        }
    }

    public boolean isEmpty() {
        return singleJobs.isEmpty() && periodicJobs.isEmpty();
    }

}
