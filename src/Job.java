import java.time.LocalDateTime;

public class Job implements Runnable {

    private String name;

    private JobState jobState;

    private LocalDateTime startTime;

    private LocalDateTime scheduledStartTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JobState getJobState() {
        return jobState;
    }

    public void setJobState(JobState jobState) {
        this.jobState = jobState;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Job(String name) {
        this.name = name;
    }

    public LocalDateTime getScheduledStartTime() {
        return scheduledStartTime;
    }

    public void setScheduledStartTime(LocalDateTime scheduledStartTime) {
        this.scheduledStartTime = scheduledStartTime;
    }

    @Override
    public void run() {
        this.setJobState(JobState.RUNNING);
        this.setStartTime(LocalDateTime.now());
        try {
            Thread.sleep(30_000);  // let each job last certain amount of time by default
            this.setJobState(JobState.FINISHED);
        } catch (Exception e) {
            System.out.println("Job " + name + " has been failed, message - " + e.getMessage());
            this.setJobState(JobState.FAILED);
        }
    }

}
