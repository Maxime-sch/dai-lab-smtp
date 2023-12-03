import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * This class provides a static method to group email addresses.
 */
public class SmtpClient {
    /**
     * Method to send an email using SMTP
     * @param sendingAddress the "from" address of the email that will be used by the server
     * @param displayedSendingAddress the "from" address of the email that will be displayed by the receiving client
     * @param recipients a List of email addresses which the email will be sent to.
     * @param subject the subject of the email to send
     * @param body the body of the email to send
     */
    public static void sendEmail(String sendingAddress, String displayedSendingAddress, List<String> recipients,
                                 String subject, String body){

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

        String toAddress = recipients.get(0);

        // Read the server's welcome message
        System.out.println("Server: " + reader.readLine());

        // Send EHLO command and read/display server answer
        sendCommand(writer, INITAL_MESSAGE + DOMAIN);
        System.out.println("Server: " + reader.readLine());

        // Send MAIL FROM command and read/display server answer
        sendCommand(writer, "MAIL FROM:<" + sendingAddress + ">");
        System.out.println("Server: " + reader.readLine());

        // Send RCPT TO command and read/display server answer
        sendCommand(writer, "RCPT TO:<" + toAddress + ">");
        System.out.println("Server: " + reader.readLine());

        // Send DATA command and read/display server answer
        sendCommand(writer, "DATA");
        System.out.println("Server: " + reader.readLine());

        // Send the email content
        sendCommand(writer, "From: <" + displayedSendingAddress + ">");
        sendCommand(writer, "To: <" + toAddress + ">");
        sendCommand(writer, "Date: April 1st, 2023");
        sendCommand(writer, "Subject: " + subject);
        sendCommand(writer, ""); // Empty line indicates the start of the email body
        sendCommand(writer, body);

        // Send the end of email marker and read/display server answer
        sendCommand(writer, ".");
        System.out.println("Server: " + reader.readLine());

        // Send QUIT command and read/display server answer
        sendCommand(writer, "QUIT");
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

    /**
     * Method to send commands using a bufferedWriter
     * @param writer bufferedWriter to use to send the command
     * @param command the command to send
     */
    private static void sendCommand(BufferedWriter writer, String command) throws IOException {
        System.out.println("Client: " + command);
        writer.write(command + "\r\n");
        writer.flush();
    }
}