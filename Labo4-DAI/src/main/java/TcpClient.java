import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TcpClient {
    public static void main(String[] args) {

        CsvReader csvReaderVirgule = new CsvReader(",");
        CsvReader csvReaderPtVirgule = new CsvReader(";");

        String victimsFilePath = args[0];
        String messagesFileName = args[1];
        int groupsAmount = Integer.parseInt(args[2]);

        //read victims, messages and put victims in List<String>
        List<String> addresses = csvReaderVirgule.readVictim(new File(victimsFilePath));
        List<String>[] messages = csvReaderPtVirgule.readMessage(new File(messagesFileName));
        List<List<String>> groups = GroupMaker.group(addresses, groupsAmount);

        List<String> sendTo;
        String from;

        //Test, TODO REMOVE

        for (int i = 0; i < groups.size(); ++i){
            sendTo = groups.get(i);
            from = sendTo.getFirst();
            sendTo.removeFirst(); //first or last??

            String subject = messages[0].get(i); //TODO accéder au bon message
            String body = messages[1].get(i);
            SmtpClient.sendEmail(from, from, sendTo, subject, body);
        }
        //TODO gestion edge cases
    }

}