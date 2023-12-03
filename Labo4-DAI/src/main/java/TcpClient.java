import java.io.File;
import java.util.List;

/**
 * Class containing the main method, implementation of our TCP client to send emails to a server.
 */
public class TcpClient {
    /**
     * Main method of our program. Creates and calls a relevant csvReader to read a victims and a messages file,
     * groups the obtained email addresses and sends one batch of emails per group (from the first address of the
     * group to all the other addresses.
     * @param args the first argument should be the victims file path, the second the messages file path and the third
     *             should be an integer that indicates the number of groups to make
     * @throws RuntimeException if args are invalid or if there are not enough messages to send to the n groups.
     */
    public static void main(String[] args) {

        CsvReader csvReaderVirgule = new CsvReader(",");
        CsvReader csvReaderPtVirgule = new CsvReader(";");

        if(args.length != 3){
            throw new RuntimeException("Please provide 3 program arguments: [victimsFilePath] [messagesFilePath]" +
                    " [(int)numberOfGroups]");
        }
        String victimsFilePath = args[0];
        String messagesFileName = args[1];
        int groupsAmount = Integer.parseInt(args[2]);

        //read victims, messages and put victims in List<String>
        List<String> addresses = csvReaderVirgule.readVictims(new File(victimsFilePath));
        List<String>[] messages = csvReaderPtVirgule.readMessage(new File(messagesFileName));
        List<List<String>> groups = GroupMaker.group(addresses, groupsAmount);

        if(groups.size() > messages[0].size()){
            throw new RuntimeException("There are not enough messages for the selected number of groups");
        }

        List<String> sendTo;
        String from;

        //Send one batch of emails per group
        for (int i = 0; i < groups.size(); ++i){
            sendTo = groups.get(i);
            from = sendTo.getFirst();
            sendTo.removeFirst();

            String subject = messages[0].get(i);
            String body = messages[1].get(i);
            SmtpClient.sendEmail(from, from, sendTo, subject, body);
        }
    }
}