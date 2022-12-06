import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class JobSystemMain {

    public static void main(String[] args) {
        boolean programLaunched = true;
        JobExecutor executor = new JobExecutor();

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
        JobType jobType = "P".equalsIgnoreCase(type) ? JobType.PERIODIC : JobType.SINGLE;
        Integer delay = null;

        if (JobType.PERIODIC.equals(jobType)) {
            System.out.print("Select delay (1, 2, 6, or 12 hours):");
            List<String> delayValues = Arrays.asList("1", "2", "6", "12");
            String delayString = requestUserInput();
            delay = delayString != null && delayValues.contains(delayString) ? Integer.parseInt(delayString) : 1;
        }

        Job job = new Job(name);
        executor.addJob(job, jobType, delay);
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

            if (!executor.getSingleJobs().isEmpty()) {
                System.out.println("SINGLE JOBS (NAME - STATE - START TIME)");
                for (Job job : executor.getSingleJobs()) {
                    String startTime = job.getStartTime() != null ? job.getStartTime().format(formatter) : "n/a";
                    System.out.println(job.getName() + " - " + job.getJobState() + " - " + startTime);
                }
            }

            if (!executor.getPeriodicJobs().isEmpty()) {
                System.out.println("PERIODIC JOBS (NAME - STATE - START TIME)");
                for (Job job : executor.getPeriodicJobs()) {
                    String startTime = job.getStartTime() != null ? job.getStartTime().format(formatter) : "n/a";
                    System.out.println(job.getName() + " - " + job.getJobState() + " - " + startTime);
                }
            }

            if (!executor.getPendingJobs().isEmpty()) {
                System.out.println("PENDING JOBS (NAME - STATE - START TIME)");
                for (Job job : executor.getPendingJobs()) {
                    String startTime = job.getStartTime() != null ? job.getStartTime().format(formatter) : "n/a";
                    System.out.println(job.getName() + " - " + job.getJobState() + " - " + startTime);
                }
            }
        }
        System.out.print("Please select next action (A - add new job, Q - quit): ");
    }

}
