package PredictiveText.RandomTextStarterProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EfficientMarkovWord implements IMarkovModel {

    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> markovWordMap;


    EfficientMarkovWord(int myOrder) {
        this.myOrder = myOrder;
        myRandom = new Random();
        markovWordMap = new HashMap<>();
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
            if (wg.equals(target)) {
                index = start;
                return index;
            }
        }
        return index;
    }

    public ArrayList<String> getFollows(WordGram kGram) {

        if (markovWordMap.containsKey(kGram))
            return markovWordMap.get(kGram);

        return new ArrayList<>();

    }

    @Override
    public String getRandomText(int numWords) {

        StringBuilder sb = new StringBuilder();
        buildMap();
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
        printHashMapInfo();

        return sb.toString().trim();
    }

    public void buildMap() {

        WordGram wg;

        for (int k = 0; k <= myText.length - myOrder; k++) {
            wg = new WordGram(myText, k, myOrder);

            if (!markovWordMap.containsKey(wg)) {
                ArrayList<String> arrayList = new ArrayList<>();
                int i;
                int pointer = 0;

                if (indexOf(myText, wg, pointer) == -1)
                    markovWordMap.put(wg, arrayList);

                while ((i = indexOf(myText, wg, pointer)) != -1) {
                    pointer = i + wg.length();
                    arrayList.add(myText[pointer]);
                }
                markovWordMap.put(wg, arrayList);
            }
        }
    }

    public void printHashMapInfo() {


        if (markovWordMap.size() < 15)
            System.out.println(markovWordMap);

        System.out.println("Number of keys: " + markovWordMap.size());

        System.out.println("The size of the largest value " + getSizeOfMaxValue());

        System.out.print("Keys with max size value : ");

        ArrayList<WordGram> arrayList = getWordGramWithMaxValueSize();
        for (WordGram wg : arrayList) {
            System.out.println(wg);
        }
    }

    private int getSizeOfMaxValue() {

        int maxValue = 0;


        for (Map.Entry<WordGram, ArrayList<String>> entry :
                markovWordMap.entrySet()) {

            int currentValueSize = entry.getValue().size();

            if (currentValueSize > maxValue)
                maxValue = currentValueSize;
        }
        return maxValue;
    }

    private ArrayList<WordGram> getWordGramWithMaxValueSize() {

        ArrayList<WordGram> wordGramWithMaxValueSize
                = new ArrayList<>();
        int max = getSizeOfMaxValue();

        for (Map.Entry<WordGram, ArrayList<String>> entry :
                markovWordMap.entrySet()) {

            int currentValueSize = entry.getValue().size();

            if (currentValueSize == max)
                wordGramWithMaxValueSize.add(entry.getKey());
        }

        return wordGramWithMaxValueSize;
    }
}
