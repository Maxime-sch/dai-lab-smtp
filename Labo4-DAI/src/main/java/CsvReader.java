import java.io.*;
import java.util.*;

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
                victims.addAll(Arrays.asList(values));
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return victims;
    }
}
