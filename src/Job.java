import java.time.LocalDateTime;

public class Job implements Runnable {

    private String name;

    private JobState jobState;

    private LocalDateTime startTime;

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

    @Override
    public void run() {
        System.out.println("Job " + name + " has been started");
        this.setJobState(JobState.RUNNING);
        this.setStartTime(LocalDateTime.now());
        try {
            Thread.sleep(5000);  // let each job last 5 seconds by default
            this.setJobState(JobState.FINISHED);
        } catch (Exception e) {
            System.out.println("Job " + name + " has been failed, message - " + e.getMessage());
            this.setJobState(JobState.FAILED);
        }
    }

}
