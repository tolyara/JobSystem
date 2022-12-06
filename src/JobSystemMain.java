import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JobSystemMain {

    public static void main(String[] args) {
        boolean programLaunched = true;

        JobExecutor executor = new JobExecutor();
        Job job1 = new Job("Default Job");
        executor.addJob(job1, JobType.SINGLE);

        while (programLaunched) {
            printJobInfo(executor);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input = reader.readLine();
                if ("Q".equalsIgnoreCase(input)) {
                    programLaunched = false; // close program
                } else if ("A".equalsIgnoreCase(input)) {
                    addNewJob(executor);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void addNewJob(JobExecutor executor) {
        System.out.print("Select job name: ");
        String name = requestUserInput();
        System.out.print("Select job type (S - single, P - periodic): ");
        String type = requestUserInput();

        Job job = new Job(name);
        executor.addJob(job, "P".equals(type) ? JobType.PERIODIC : JobType.SINGLE);
    }

    private static String requestUserInput() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            String input = reader.readLine();
            return input;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void printJobInfo(JobExecutor executor) {
        System.out.println();
        System.out.println("JOB INFO:");
        if (executor.isEmpty()) {
            System.out.println("No active jobs found");
        } else {
            System.out.println("Single jobs (name - state - start time)");
            for (Job job : executor.getSingleJobs()) {
                System.out.println(job.getName() + " - " + job.getJobState() + " - " + job.getStartTime());
            }
        }
        System.out.print("Please select next action (A - add new job, Q - quit): ");
    }

}
