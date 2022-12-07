import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JobExecutor {

    private List<Job> periodicJobs = new ArrayList();

    private List<Job> singleJobs = new ArrayList();

    private List<Job> pendingJobs = new ArrayList();

    private final int limit = 2;  // limit on the amount of jobs that can run concurrently at any given moment

    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(limit);

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

    public int getLimit() {
        return limit;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public void addJob(Job job, JobType jobType, Integer delay) {
        if (job == null || jobType == null) return;

        if (jobType.equals(JobType.SINGLE)) {
            this.getSingleJobs().add(job);
            this.startJob(job);
        } else if (jobType.equals(JobType.PERIODIC) && delay != null) {
            job.setJobState(JobState.SCHEDULED);
            job.setScheduledStartTime(LocalDateTime.now().plusHours(delay));
            this.getPeriodicJobs().add(job);
            scheduledExecutorService.schedule(job, delay, TimeUnit.SECONDS);
        } else {
            // do nothing
        }
    }

    public int getAllJobsRunningAmount() {
        int singleJobsAmount = (int) this.getSingleJobs().stream().filter(j -> JobState.RUNNING.equals(j.getJobState())).count();
        int periodicJobsAmount = (int) this.getPeriodicJobs().stream().filter(j -> JobState.RUNNING.equals(j.getJobState())).count();
        return singleJobsAmount + periodicJobsAmount;
    }

    public void startJob(Job job) {
        Thread thread = new Thread(job);
        thread.start();
    }

    public boolean isEmpty() {
        return singleJobs.isEmpty() && periodicJobs.isEmpty();
    }

    public void checkPendingJobs() {
        if (this.getPendingJobs().isEmpty()) return;
        int allJobsRunningAmount = this.getAllJobsRunningAmount();
        if (allJobsRunningAmount < limit) {
            Job job = this.getPendingJobs().get(0);
            this.getPendingJobs().remove(0);

            if (JobState.SCHEDULED.equals(job.getJobState())) {
                this.getPeriodicJobs().add(job);
            } else {
                this.getSingleJobs().add(job);
            }
            this.startJob(job);
        }
    }

}
