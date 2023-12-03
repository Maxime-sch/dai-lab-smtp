import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides methods to parse a csv file to get a list of email addresses from victims or a list of messages
 */
public class CsvReader {
    private final String delimiter;

    CsvReader(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * Reads a file and returns a list of email addresses from victims found in the contents of the file.
     * The addresses can all be in a line or spread across lines in the csv, it does not matter.
     * @param file file to parse
     * @return the ArrayList of victims that were found in the file
     * @throws RuntimeException if the file is not .csv or there is an invalid email address.
     */
    public ArrayList<String> readVictims(File file) {
        checkCsv(file);
        ArrayList<String> victims = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                for (String value : values) {
                    value = value.trim();
                    if (!isValidEmail(value)) {
                        throw new RuntimeException(value + " is not a valid email address");
                    }
                    else{
                        victims.add(value.toLowerCase());
                        //as emails are not case-sensitive, we decided to always use all lowercases
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return victims;
    }

    /**
     * Reads a file and returns a list subjects and a list of bodies found in the contents of the file.
     * The csv file must contain exactly one subject and one body on each line, in that order.
     * @param file file to parse
     * @return a two-dimensional array or arrayLists, the first being the list of subjects and the other being the list
     * of bodies
     * @throws RuntimeException if the file is not .csv, if the file does not follow the required pattern, if
     * a subject or body is empty or if a subject is too long.
     */
    public ArrayList<String>[] readMessage(File file) {
        checkCsv(file);
        ArrayList<String>[] messages = new ArrayList[2];
        messages[0] = new ArrayList<String>();
        messages[1] = new ArrayList<String>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter);
                if (values.length != 2) {
                    throw new RuntimeException(Arrays.toString(values) + ": Invalid message format: each line must " +
                            "contain exactly one subject and one body");
                }
                for (String value : values) {
                    if (value.isBlank()) {
                        throw new RuntimeException(Arrays.toString(values) + " : Missing message subject or body");
                    }
                }
                if(messages[0].size() > 78){
                    //It is usually said that subjects should not exceed 78 characters even though SMTP allows it.
                    throw new RuntimeException(messages[0] + " : subject is too long");
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

    /**
     * Method to check if an email address is valid
     * @param email String to check whether it corresponds to a valid email address
     * @return yes if it is valid, no otherwise
     */
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

    /**
     * Method to get the extension of a file
     * @param file to check the extension of
     * @return the extension as a string
     */
    public String getExtension(File file) {
        String fileName = file.getName();

        int extensionIndex = fileName.lastIndexOf('.');
        if(extensionIndex == -1) {
            return null;
        }

        return fileName.substring(extensionIndex + 1);
    }

    /**
     * Method to check if a file is a csv and throw an exception if it is not.
     * @param file to check
     * @throws RuntimeException if the file is not csv
     */
    public void checkCsv(File file){
        String fileExtension = getExtension(file);
        if(!fileExtension.equals("csv")){
            throw new RuntimeException(fileExtension + " is not a valid file type for this program," +
                    " only csv is supported");
        }
    }
}
