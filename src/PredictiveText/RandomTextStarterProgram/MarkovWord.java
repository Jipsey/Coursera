package PredictiveText.RandomTextStarterProgram;

import java.util.ArrayList;
import java.util.Random;

public class MarkovWord implements IMarkovModel {

    private String[] myText;
    private Random myRandom;
    private int myOrder;

    MarkovWord(int myOrder) {
        this.myOrder = myOrder;
        myRandom = new Random();
    }

    @Override
    public void setTraining(String text) {
        myText = text.split("\\s+");
    }

    @Override
    public void setRandom(int seed) {
        myRandom.setSeed(seed);
    }

    public int indexOf(String[] words, WordGram target, int start) {

        int index = -1;

        for (; start < words.length - target.length(); start++) {
            WordGram wg = new WordGram(words, start, target.length());
            if (wg.equals(target))
                index = start;
        }
        return index;
    }

    public ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> arrayList = new ArrayList<>();

        int index = indexOf(myText, kGram, 0);
        while (index != -1) {

            index = index + kGram.length();
            arrayList.add(myText[index]);
            index = indexOf(myText, kGram, index);
        }

        return arrayList;
    }

    @Override
    public String getRandomText(int numWords) {

        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length - myOrder);  // random word to start with
        WordGram wg = new WordGram(myText, index, myOrder);

        sb.append(wg);
        for (int k = 0; k < numWords - myOrder; k++) {
            ArrayList<String> follows = getFollows(wg);
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");

            wg = wg.shiftAdd(next);
        }

        return sb.toString().trim();
    }


    public void testIndexOf() {
        //	String test = "this is just a test yes this is a simple test";
        String[] array = {"this", "is", "just", "a", "test", "yes", "this", "is", "a", "simple", "test"};
        WordGram testWg = new WordGram(array, 0, array.length);
        // look for the words: “this” starting at 0, “this” starting at 3, “frog” starting at 0,
        // “frog” starting at 5, “simple” starting at 2 and “test” starting at 5.

        int wordLoc = indexOf(array, testWg, 0);
        System.out.println("The Index of THIS is at position: " + wordLoc);
        int wordLoc1 = indexOf(array, testWg, 3);
        System.out.println("The Index of THIS is at position: " + wordLoc1);
        int wordLoc2 = indexOf(array, testWg, 0);
        if (wordLoc2 == -1) {
            System.out.println("FROG was not found");
        } else {
            System.out.println("The Index of FROG is at position: " + wordLoc2);
        }
        int wordLoc3 = indexOf(array, testWg, 5);
        if (wordLoc3 == -1) {
            System.out.println("FROG was not found");
        } else {
            System.out.println("The Index of FROG is at position: " + wordLoc3);
        }
        int wordLoc4 = indexOf(array, testWg, 2);
        System.out.println("The Index of SIMPLE is at position: " + wordLoc4);
        int wordLoc5 = indexOf(array, testWg, 5);
        System.out.println("The Index of SIMPLE is at position: " + wordLoc5);
    }


}
