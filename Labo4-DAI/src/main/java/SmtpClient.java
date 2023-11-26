import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SmtpClient {
    public static void sendEmail(String sendingAddress, String displayedSendingAddress, List<String> recipients,
                                 String subject, String message){

        // Constants
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 1025;
        final String DOMAIN = "localhost";
        final Charset CHARSET = StandardCharsets.UTF_8;
        final String INITAL_MESSAGE = "ehlo ";
        // Set to null to allow for better error handling
        Socket socket = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;

    try {
        socket = new Socket(SERVER_ADDRESS, PORT);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));

        String fromAddress = sendingAddress;
        String toAddress = recipients.get(0);
        String fromDisplayedAddress = displayedSendingAddress;

        // Read the server's welcome message
        System.out.println("Server: " + reader.readLine());

        // Send EHLO command
        sendCommand(writer, INITAL_MESSAGE + DOMAIN);
        // Read and print the response
        System.out.println("Server: " + reader.readLine());

        // Send MAIL FROM command
        sendCommand(writer, "MAIL FROM:<" + fromAddress + ">");
        // Read and print the response
        System.out.println("Server: " + reader.readLine());

        // Send RCPT TO command
        sendCommand(writer, "RCPT TO:<" + toAddress + ">");
        // Read and print the response
        System.out.println("Server: " + reader.readLine());

        // Send DATA command
        sendCommand(writer, "DATA");
        // Read and print the response
        System.out.println("Server: " + reader.readLine());

        // Send the email content
        sendCommand(writer, "From: <" + fromDisplayedAddress + ">");
        sendCommand(writer, "To: <" + toAddress + ">");
        sendCommand(writer, "Date: April 1st, 2023");
        sendCommand(writer, "Subject: " + subject);
        sendCommand(writer, ""); // Empty line indicates the start of the email body
        sendCommand(writer, message);

        // Send the end of email marker
        sendCommand(writer, ".");
        // Read and print the response
        System.out.println("Server: " + reader.readLine());

        // Send QUIT command
        sendCommand(writer, "QUIT");
        // Read and print the response
        System.out.println("Server: " + reader.readLine());
        // End
        socket.close();
        writer.close();
        reader.close();
        System.out.println("Done !\r\n");
    }

    // Error handling : Close everything that was initialised
        catch (IOException ex) {
            System.out.println( "ERROR : " + ex);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException ex) {
                System.out.println( "ERROR : " + ex);
            }
            try {
                if (reader != null) reader.close();
            } catch (IOException ex) {
                System.out.println( "ERROR : " + ex);
            }
            try {
                if (socket != null && ! socket.isClosed()) socket.close();
            } catch (IOException ex) {
                System.out.println( "ERROR : " + ex);
            }
        }
    }
    private static void sendCommand(BufferedWriter writer, String command) throws IOException {
        System.out.println("Sent to server: " + command);
        writer.write(command + "\r\n");
        writer.flush();
    }
}