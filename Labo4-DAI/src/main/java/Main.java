import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //String victimsFileName = args[0];
        //String messagesFileName = args[1];
        //int groupsAmount = Integer.parseInt(args[2]);

        //read victims, messages and put victims in List<String>
        List<String> addresses = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        List<List<String>> groups = new ArrayList<>();//GroupMaker.group(addresses, groupsAmount);

        List<String> sendTo;
        String from;

        //Test, TODO remove
        List<String> group1 = new ArrayList<>();
        group1.add("envoyeur@mail.ch");
        group1.add("receveur@mail.ch");
        groups.add(group1);
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