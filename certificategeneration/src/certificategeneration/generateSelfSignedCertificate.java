package certificategeneration;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class generateSelfSignedCertificate {

    public static void main(String[] args) {
        String certificateName = "JKBANK"; // Change this to your desired certificate name
        String keystorePassword = "JKBANK"; // Default Java keystore password

        // Set the paths for the certificate 6and key files
        String certificatePath ="D:"+File.separator+certificateName+".cert";
       // String certificatePath = "D:/SelfSignedCertificates/" + certificateName + ".cert";
        String keyPath = "D:/SelfSignedCertificates/" + certificateName + ".key";

        try {
            generateSelfSignedCertificate1(certificatePath, keyPath, keystorePassword);
            System.out.println("Self-signed certificate and private key generated successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

 

    private static void generateSelfSignedCertificate1(String certificatePath, String keyPath, String keystorePassword) throws IOException, InterruptedException {
        // Create a list to hold the command and its arguments
        List<String> command = new ArrayList<>();

        // Set the path to the keytool executable (Java's key management tool)
        String keytoolPath = System.getProperty("java.home") + "/bin/keytool";

        // Add the keytool command and arguments to the list
        command.add(keytoolPath);
        command.add("-genkeypair");
        command.add("-alias");
        command.add("mykey");
        command.add("-keyalg");
        command.add("RSA");
        command.add("-keysize");
        command.add("2048");
        command.add("-storetype");
        command.add("PKCS12");
        command.add("-keystore");
        command.add(certificatePath);
        command.add("-storepass");
        command.add(keystorePassword);
        command.add("-keypass");
        command.add(keystorePassword);
        command.add("-validity");
        command.add("365");

        // Convert the list to an array of strings (command line arguments)
        String[] commandArray = command.toArray(new String[0]);

        // Create a process builder with the command and start the process
        ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Wait for the process to complete
       // process.waitFor();
    }
}