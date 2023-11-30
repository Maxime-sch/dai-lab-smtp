import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TcpClient {
    public static void main(String[] args) {

        CsvReader csvReader = new CsvReader(",");

        String victimsFilePath = args[0];
        String messagesFileName = args[1];
        int groupsAmount = Integer.parseInt(args[2]);

        //read victims, messages and put victims in List<String>
        List<String> addresses = csvReader.readVictim(new File(victimsFilePath));
        List<String> messages = new ArrayList<>(); // csvReader.readMessages(new File(victimsFilePath));
        List<List<String>> groups = GroupMaker.group(addresses, groupsAmount);

        List<String> sendTo;
        String from;

        //Test, TODO REMOVE
        messages.add("Message de test.");

        for (int i = 0; i < groups.size(); ++i){
            sendTo = groups.get(i);
            from = sendTo.getFirst();
            sendTo.removeFirst(); //first or last??

            String message = messages.get(0); //TODO accéder au bon message
            SmtpClient.sendEmail(from, from, sendTo, message, message);
        }
        //TODO gestion edge cases
    }

}