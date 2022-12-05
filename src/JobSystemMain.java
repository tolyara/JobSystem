
public class JobSystemMain {

    public static void main(String[] args) {
        JobExecutor executor = new JobExecutor();
        Job job1 = new Job("JOB_1");
        Job job2 = new Job("JOB_2");

        executor.addJob(job1, JobType.ONE_TIME);
        executor.addJob(job2, JobType.ONE_TIME);

        System.out.println("Starting jobs...");
        executor.startJobs();
    }

}
