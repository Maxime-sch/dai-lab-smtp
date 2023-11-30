import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvReader {
    private final String delimiter;

    CsvReader(String delimiter) {
        this.delimiter = delimiter;
    }
    public ArrayList<String> readVictim(File file) {
        ArrayList<String> victims = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                for (String value : values) {
                    value = value.trim();
                    if (!isValidEmail(value)) {
                        System.out.println(value);
                        throw new RuntimeException("Invalid email address");
                    }
                }
                victims.addAll(Arrays.asList(values));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return victims;
    }

    public ArrayList<String>[] readMessage(File file) {
        ArrayList<String>[] messages = new ArrayList[2];
        messages[0] = new ArrayList<String>(); // subject
        messages[1] = new ArrayList<String>(); // body
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                if (values.length != 2) {
                    System.out.println(values);
                    throw new RuntimeException("Invalid message format");
                }
                for (String value : values) {
                    if (value.isBlank()) {
                        System.out.println(values);
                        throw new RuntimeException("Missing message subject or body");
                    }
                }
                messages[0].add(values[0]);
                messages[1].add(values[1]);
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return messages;
    }

    public static boolean isValidEmail(String email) {
        // Expression régulière pour valider un email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Créer le modèle de l'expression régulière
        Pattern pattern = Pattern.compile(emailRegex);

        // Créer le correspondant de l'expression régulière
        Matcher matcher = pattern.matcher(email);

        // Retourner true si l'email est valide, sinon false
        return matcher.matches();
    }
}
