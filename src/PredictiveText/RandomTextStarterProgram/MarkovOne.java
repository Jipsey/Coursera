package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Random;

public class MarkovOne {
    private String myText;
    private Random myRandom;

    public MarkovOne() {
        myRandom = new Random();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String s) {
        myText = s.trim();
    }

    public void setTextFromFile() {

        String str = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\" +
                "RandomTextStarterProgram\\data\\confucius.txt";
        FileResource fr = new FileResource(str);
        myText = fr.asString()
                .replace("\n", " ");
        setTraining(myText);
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

    public ArrayList<String> getFollows(String key) {

        ArrayList<String> arrayList = new ArrayList<>();
        int i = 0;

        while ((i = myText.indexOf(key, i)) != -1 &&
                (i += key.length()) < myText.length()) {
            String str = String.valueOf(myText.charAt(i));
            arrayList.add(str);
        }
        return arrayList;
    }
}
