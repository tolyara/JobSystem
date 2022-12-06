import java.util.ArrayList;
import java.util.List;

public class JobExecutor {

    private List<Job> periodicJobs = new ArrayList();

    private List<Job> singleJobs = new ArrayList();

    private List<Job> pendingJobs = new ArrayList();

    private final int limit = 2;  // limit on the amount of jobs that can run concurrently at any given moment

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

    public List<Job> getPendingJobs() {
        return pendingJobs;
    }

    public void setPendingJobs(List<Job> pendingJobs) {
        this.pendingJobs = pendingJobs;
    }

    public void addJob(Job job, JobType jobType) {
        if (job == null || jobType == null) return;

        int singleJobsAmount = (int) this.getSingleJobs().stream().filter(j -> JobState.RUNNING.equals(j.getJobState())).count();
        int periodicJobsAmount = (int) this.getPeriodicJobs().stream().filter(j -> JobState.RUNNING.equals(j.getJobState())).count();
        int allJobsRunningAmount = singleJobsAmount + periodicJobsAmount;

        if (allJobsRunningAmount >= limit) {
            this.getPendingJobs().add(job);
        } else {
            if (jobType.equals(JobType.SINGLE)) {
                this.singleJobs.add(job);
                this.startJob(job);
            } else if (jobType.equals(JobType.PERIODIC)) {
                this.periodicJobs.add(job);
                this.startJob(job);
            } else {
                // do nothing
            }
        }
    }

    public void startJob(Job job) {
        Thread thread = new Thread(job);
        thread.start();
    }

    public boolean isEmpty() {
        return singleJobs.isEmpty() && periodicJobs.isEmpty();
    }

}
