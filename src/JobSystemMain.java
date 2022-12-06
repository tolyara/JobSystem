import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JobSystemMain {

    public static void main(String[] args) {
        boolean programLaunched = true;

        JobExecutor executor = new JobExecutor();
        Job job1 = new Job("JOB_1");
        Job job2 = new Job("JOB_2");

        executor.addJob(job1, JobType.SINGLE);
        executor.addJob(job2, JobType.SINGLE);

        while (programLaunched) {
            printJobInfo(executor);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if ("Q".equalsIgnoreCase(input)) {
                    programLaunched = false; // close program
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void printJobInfo(JobExecutor executor) {
        System.out.println("JOB INFO:");
        if (executor.isEmpty()) {
            System.out.println("No active jobs found");
        } else {
            System.out.println("Single jobs (name - state - start time)");
            for (Job job : executor.getSingleJobs()) {
                System.out.println(job.getName() + " - " + job.getJobState() + " - " + job.getStartTime());
            }
        }
    }

}
