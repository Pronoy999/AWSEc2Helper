import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainController {
    private BufferedReader bufferedReader;
    private String instanceID;

    public static void main(String args[]) {
        MainController controller = new MainController();
        controller.executer(controller.getInput());
    }

    private String getInput() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the Instance ID:");
            instanceID = bufferedReader.readLine();
            System.out.println("Enter start to start an instance or stop to stop one.");
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void executer(String command) {
        AWSHelper awsHelper = new AWSHelper(Constants.ACCESS_KEY_ID, Constants.SECRET_ACCESS_KEY, instanceID);
        switch (command) {
            case "start":
                awsHelper.startInstance();
                System.out.println("Starting Instance...");
                break;
            case "stop":
                awsHelper.stopInstance();
                System.out.println("Stopping Instance...");
                break;
            case "list":
                awsHelper.listInstances();
                break;
            default:
                System.out.println("Please try again with a valid choice.");
        }
    }
}
