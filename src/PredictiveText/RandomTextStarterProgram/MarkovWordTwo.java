package PredictiveText.RandomTextStarterProgram;

import java.util.ArrayList;
import java.util.Random;

public class MarkovWordTwo implements IMarkovModel {

    private String[] myText;
    private Random myRandom;

    public MarkovWordTwo() {
        myRandom = new Random();
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String text) {
        myText = text.split("\\s+");
    }

    public String getRandomText(int numWords) {
        StringBuilder sb = new StringBuilder();
        int index1 = myRandom.nextInt(myText.length - 1);  // random word to start with
        String key1 = myText[index1];
        String key2 = myText[index1 + 1];
        sb.append(key1).append(" ");
        sb.append(key2).append(" ");

        for (int k = 0; k < numWords - 1; k++) {
            ArrayList<String> follows = getFollows(key1, key2);
            if (follows.size() == 0) {
                break;
            }
            index1 = myRandom.nextInt(follows.size());
            String next = follows.get(index1);
            sb.append(next).append(" ");

            key1 = key2;
            key2 = next;
        }
        return sb.toString().trim();
    }

    private ArrayList<String> getFollows(String key1, String key2) {

        key1 = key1.trim();
        key2 = key2.trim();
        ArrayList<String> follows = new ArrayList<>();
        int index = indexOf(myText, key1, key2, 0);
        String followWord;

        while (index != -1) {
            followWord = myText[index];
            follows.add(followWord);
            index = indexOf(myText, key1, key2, index);
            //index++;
        }
        return follows;
    }

    private int indexOf(String[] words, String target1, String target2, int start) {

        int index = -1;
        for (; start < words.length - 2; start++) {
            if (words[start].equals(target1) &&
                    words[start + 1].equals(target2)) {
                index = start + 2;
                return index;
            }
        }
        return index;
    }

    public void testIndexOf() {
        String string = "this is just a test yes this is a simple test";
        setTraining(string);
        System.out.println(getFollows("this", "is"));
        System.out.println(getFollows("just", "a"));
    }
}
