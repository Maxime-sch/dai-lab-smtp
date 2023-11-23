import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SmtpClient {
    public static void main(String[] args) throws IOException {
// Constants
// Project specific settings
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 1080;
        final String DOMAIN = "heig-vd.ch";
        final Charset CHARSET = StandardCharsets.UTF_8;
// Messages
        final String OPEN = ("OPEN mail01.heig-vd.ch.");
        final String INITAL_MESSAGE = "ehlo ";
        final String INITAL_ANSWER = "250 HELP";
// Set to null to allow for better error handling
    Socket socket = null;
    BufferedReader reader = null;
    BufferedWriter writer = null;
    try {
    // Sockets
            socket = new Socket(SERVER_ADDRESS, PORT);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), CHARSET));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), CHARSET));
    // Initial negotiation
        writer.write(OPEN + "\r\n");
        System.out.println(OPEN + "\r\n");
        writer.flush();
            writer.write(INITAL_MESSAGE + DOMAIN + "\r\n");
            System.out.println(INITAL_MESSAGE + DOMAIN + "\r\n");
            writer.flush();
    // Ignores everything until INITIAL_ANSWER
    // TODO in an actual client : correctly read that
            String line;
            while ((line = reader.readLine()) != null && !line.equals(INITAL_ANSWER));
    // FROM :
        String emailAddress = "maxsch@heig-vd.ch";

        String message = "mail from:<" + emailAddress + ">\n" +
                    "rcpt to: <" + emailAddress + ">\n" +
                    "data\n" +
                    "From: <" + emailAddress + ">\n" +
                    "To: <" + emailAddress + ">\n" +
                    "Date: April 1st, 2023\n" +
                    "Subject: inshallah ça marche\n\n" +
                    "blablabla\n\n";
        String QUIT = ("quit"  + "\r\n");

            writer.write("");
            writer.write(message + "\r\n");
            System.out.print(message);
            writer.flush();
            //if(250 OK)
            writer.write(QUIT);
            System.out.print(QUIT);
    // End
            socket.close();
            writer.close();
            reader.close();
            System.out.println("Done !\r\n");
        }
    // Error handling : Try to close everything that was initialised
    // Uses the code example provided in our course, as I cannot see any better way of doing it
    // (Except maybe keeping the logger instead of those println)
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
}
