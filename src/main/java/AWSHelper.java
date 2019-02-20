import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;

public class AWSHelper {
    private String ACCESS_KEY, SECRET_KEY, instanceID;

    public AWSHelper(String ACCESS_KEY, String SECRET_KEY, String instanceID) {
        this.ACCESS_KEY = ACCESS_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.instanceID = instanceID;
    }

    public void startInstance() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        StartInstancesRequest request = new StartInstancesRequest().withInstanceIds(instanceID);
        ec2Client.startInstances(request);
    }

    public void stopInstance() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        ec2Client.stopInstances(new StopInstancesRequest().withInstanceIds(instanceID));
    }

    public void listInstances() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
        AmazonEC2 ec2Client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        boolean isDone = false;
        while (!isDone) {
            DescribeInstancesResult result = ec2Client.describeInstances(request);
            for (Reservation reservation : result.getReservations()) {
                for (Instance instance : reservation.getInstances()) {
                    System.out.println(instance.getInstanceId());
                }
            }
            request.setNextToken(result.getNextToken());
            if (result.getNextToken() == null) {
                isDone = true;
            }
        }
    }
}
