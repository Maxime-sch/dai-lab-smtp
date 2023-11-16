import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.round;

public class GroupMaker {
    static List<List<String>> group(List<String> addresses, int n){
        int totalAddresses = addresses.size();
        float avgGrpSize = (float)(totalAddresses) / (float) (n);
        if(avgGrpSize < 2){
            throw new RuntimeException("There are too many groups to put at least 2 addresses per group");
        }
        else if(avgGrpSize > 5){
            throw new RuntimeException("There are too few groups to put at least 2 addresses per group");
        }

        List<List<String>> result = new ArrayList<>();

        int curIndex = 0;
        int curGrpSize;

        for(int i = 0; i < n; ++i){
            result.add(new ArrayList<>()); //ajouter un groupe vide

            avgGrpSize = (float)(totalAddresses - curIndex) / (float) (n - i);
            curGrpSize = round(avgGrpSize);

            for(int j = 0; j < curGrpSize; ++j){
                result.get(i).add(addresses.get(curIndex)); //remplir le groupe courant
                ++curIndex;
            }
        }
        return result;
    }

    public static void main(String[] args){ //Programme de test de groupage simple
        List<String> testArrayList = new ArrayList<>();
        for(int i=0; i<20; i++){
            testArrayList.add("Person" + Integer.toString(i) + "@gmail.com");
        }

        List<List<String>> testGrp = group(testArrayList, 4);

        for(List<String> i : testGrp){
            System.out.println(i);
        }
    }
}
