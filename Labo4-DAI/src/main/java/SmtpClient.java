import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SmtpClient {
    public static void main(String[] args) throws IOException {
// Constants
// Project specific settings
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 1025;
        final String DOMAIN = "localhost";
        final Charset CHARSET = StandardCharsets.UTF_8;
// Messages
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

        // Ignores everything until INITIAL_ANSWER
            //String line;
            //while ((line = reader.readLine()) != null && !line.equals(INITAL_ANSWER));

        String emailAddress = "maxsch@localhost";

        String message = "telnet " + DOMAIN + " " + PORT + "\n" +
                    INITAL_MESSAGE + DOMAIN + "\r\n" +
                    "mail from:<" + emailAddress + ">\n" +
                    "rcpt to: <" + emailAddress + ">\n" +
                    "data\n" +
                    "From: <" + emailAddress + ">\n" +
                    "To: <" + emailAddress + ">\n" +
                    "Date: April 1st, 2023\n" +
                    "Subject: inshallah ça marche\n\n" +
                    "blablabla\n\n";
        String[] lines = message.split("\r\n|\r|\n");
        int nblines = lines.length;

        for(int i = 0; i < nblines; ++i){
            System.out.println("Sent to server: " + lines[i]);
            writer.write(lines[i] + "\n");
            writer.flush();
            System.out.println("Echo: " + reader.readLine());
        }

        String QUIT = ("quit"  + "\r\n");
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