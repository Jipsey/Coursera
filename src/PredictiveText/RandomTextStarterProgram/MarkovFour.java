package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Random;

public class MarkovFour {


    private String myText;
    private Random myRandom;

    public MarkovFour() {
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
        StringBuffer sb = new StringBuffer();
        StringBuilder sBuf = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - 4);
        String key = myText.substring(index, index + 4);
        sb.append(key);

        for (int k = 0; k < numChars - 4; k++) {

            System.out.println(key);
            follows = getFollows(key);
            if (follows.size() == 0)
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }

    public ArrayList<String> getFollows(String key) {

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
