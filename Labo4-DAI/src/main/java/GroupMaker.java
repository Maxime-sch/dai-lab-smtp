import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.round;

/**
 * This class provides a static method to group email addresses.
 */
public class GroupMaker {
    /**
     * Method to group a list of email addresses into n groups, with a maximum of 5 and a minimum of 2 email addresses
     * per group
     * @param addresses the email addresses to group, given as a List of Strings
     * @param n the amount of groups to make
     * @return a List of Lists, each second-level list being a group of email addresses
     * @throws RuntimeException if there are too many or too little addresses to have a group size <5 and >2
     */
    public static List<List<String>> group(List<String> addresses, int n){
        float totalAddresses = addresses.size();
        float avgGrpSize = totalAddresses / (float) (n);
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
            result.add(new ArrayList<>()); //add new empty group

            //update average group size to choose how many addresses to put in the current group
            avgGrpSize = (totalAddresses - curIndex) / (float) (n - i);
            curGrpSize = round(avgGrpSize);

            for(int j = 0; j < curGrpSize; ++j){ //fill the current group
                result.get(i).add(addresses.get(curIndex));
                ++curIndex;
            }
        }
        return result;
    }
}
