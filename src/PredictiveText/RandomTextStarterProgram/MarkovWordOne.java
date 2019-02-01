package PredictiveText.RandomTextStarterProgram;
/**
 * Write a description of class MarkovWordOne here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWordOne implements IMarkovModel {
    private String[] myText;
    private Random myRandom;

    public MarkovWordOne() {
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
        int index = myRandom.nextInt(myText.length - 1);  // random word to start with
        String key = myText[index];
        sb.append(key);
        sb.append(" ");
        for (int k = 0; k < numWords - 1; k++) {
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = next;
        }

        return sb.toString().trim();
    }

    private ArrayList<String> getFollows(String key) {

        ArrayList<String> follows = new ArrayList<>();
        int index = indexOf(myText,key,0);
        String followWord;

        while (index != -1) {
            index++;
            followWord = myText[index];
            follows.add(followWord);
            index = indexOf(myText, key, index);
        }
        return follows;
    }

    private int indexOf(String[] words, String target, int start) {

        int index = -1;

        for (; start < words.length; start++) {
            if (words[start].equals(target)) {
                index = start;
                return index;
            }
        }
        return index;
    }

    public void testIndexOf() {
        String string = "this is just a test yes this is a simple test";
        setTraining(string);
        System.out.println(getFollows("word"));
        System.out.println(getFollows("yes"));
        System.out.println(getFollows("is"));
        System.out.println(getFollows("just"));
        System.out.println(getFollows("a"));

    }
}
