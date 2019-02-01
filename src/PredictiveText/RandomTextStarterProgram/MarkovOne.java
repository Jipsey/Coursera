package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Random;

public class MarkovOne extends AbstractMarkovModel{

    public MarkovOne() {
        myRandom = new Random();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String s) {
        myText = s.trim();
    }

    public void setTextFromFile(String fileName) {

        String str = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\" +
                "RandomTextStarterProgram\\data\\" + fileName;
        FileResource fr = new FileResource(str);
        myText = fr.asString()
                .replace("\n", " ");
        setTraining(myText);
    }

    public void testGetFollowsWithFile(){

      ArrayList<String > arr = getFollows("he");

    }

    public String getRandomText(int numChars) {
        if (myText == null) {
            return "";
        }
        ArrayList<String> follows;
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - 1);
        String key = myText.substring(index, index + 1);
        sb.append(key);

        for (int k = 0; k < numChars - 1; k++) {
            follows = getFollows(key);
            if (follows.size() == 0)
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = next;
        }
        return sb.toString();
    }


    public String toString(){
        return "Markov model of order 1";

    }

}
