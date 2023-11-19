import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class SmtpClient {
    public static void main(String[] args) throws IOException {
// Constants
// Project specific settings
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 2525;
        final String DOMAIN = "dai.ch";
        final String EMAIL_ADDRESS = "te1@dai.ch";
        final Charset CHARSET = StandardCharsets.UTF_8;
// Tested the server, even when giving it another charset at start (ex : `-Dfile.encoding=UTF-16`) it still
// only understood UTF-8, so I take it only accepts it
// Messages
        final String INITAL_MESSAGE = "EHLO ";
        final String INITAL_ANSWER = "250 HELP";
        final String MAIL_FROM = "MAIL FROM:";
    // Variables
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
            writer.write(INITAL_MESSAGE + DOMAIN + "\r\n");
            writer.flush();
    // Ignores everything until INITIAL_ANSWER
    // TODO in an actual client : correctly read that
            String line;
            while ((line = reader.readLine()) != null && !line.equals(INITAL_ANSWER));
    // FROM :
            writer.write(MAIL_FROM + "<" + EMAIL_ADDRESS + ">\r\n");
            writer.flush();
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
