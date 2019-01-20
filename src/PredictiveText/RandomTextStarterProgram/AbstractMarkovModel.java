package PredictiveText.RandomTextStarterProgram;
/**
 * Abstract class AbstractMarkovModel - write a description of the class here
 * 
 * @author (your name here)
 * @version (version number or date here)
 */

import java.util.*;

public abstract class AbstractMarkovModel implements IMarkovModel {
    protected String myText;
    protected Random myRandom;

    public AbstractMarkovModel() {
        myRandom = new Random();
    }

    public void setTraining(String s) {
        myText = s.trim();
    }
 
    abstract public String getRandomText(int numChars);

    protected ArrayList<String> getFollows(String key){

        ArrayList<String> arrayList = new ArrayList<>();
        int pos = 0;
        int start;

        while ((start = myText.indexOf(key, pos)) != -1 &&
                (start + key.length()) < myText.length()) {

            String str = myText.substring(start + key.length(),
                    start + key.length() + 1);

            arrayList.add(str);
            pos = start + key.length();
        }
        return arrayList;
    }
}
