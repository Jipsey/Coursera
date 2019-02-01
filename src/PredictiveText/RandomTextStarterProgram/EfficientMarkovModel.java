package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EfficientMarkovModel extends AbstractMarkovModel {

    private int keySize;
    private HashMap<String, ArrayList<String>> mapFollows;

    public EfficientMarkovModel(int keySize) {
        this.keySize = keySize;
        myRandom = new Random();
        mapFollows = new HashMap<>();
    }

    public int getKeySize() {
        return keySize;
    }

    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

    public void setTraining(String s) {
        myText = s.trim();
    }

    private void buildMap() {

        StringBuilder sb = new StringBuilder(myText);
        mapFollows.clear();
        ArrayList<String> arrayList;
        for (int i = 0; i <= sb.length() - keySize;
             i++) {

            String newKey = sb.substring(i, i + keySize);


            if (!mapFollows.containsKey(newKey))
                mapFollows.put(newKey, new ArrayList<>());
        }

        for (int i = 0; i <= sb.length() - keySize; i++) {

            String newKey = sb.substring(i, i + keySize);

            if(mapFollows.containsKey(newKey)){
                arrayList = getFollows(newKey);
                mapFollows.put(newKey,arrayList);
            }

        }
        printHashMapInfo();
    }

    public String getRandomText(int numChars) {
        if (myText == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        buildMap();
        int index = myRandom.nextInt(myText.length() - keySize);
        String key = myText.substring(index, index + keySize);
        sb.append(key);
        for (int i = 0; i <= numChars - keySize; i++) {

            ArrayList<String> follows = getFollows(key);
            if(follows.size() == 0)
                break;
            else mapFollows.put(key,follows);
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }

    private void printHashMapInfo() {

        int sizeOfMaxList = 0;
        ArrayList<String> keysWithMaxSize = new ArrayList<>();

        if (mapFollows.isEmpty() || mapFollows.size() < 10) {
            System.out.println("map of follow characters is empty or too small !");
            return;
        }

        for (Map.Entry<String, ArrayList<String>> entry : mapFollows.entrySet()) {
            int sizeOfCurrentList = entry.getValue().size();
            if (sizeOfMaxList < sizeOfCurrentList)
                sizeOfMaxList = sizeOfCurrentList;

        }
        if (mapFollows.size() > 10)
            for (Map.Entry<String, ArrayList<String>> entry : mapFollows.entrySet()) {

                if (entry.getValue().size() == sizeOfMaxList)
                    keysWithMaxSize.add(entry.getKey());
            }

        System.out.printf("number of keys: %s  size of the largest ArrayList %s\n",
                mapFollows.size(), sizeOfMaxList);
        System.out.println(" Keys with maximum size\n" + keysWithMaxSize);


    }


    public String toString() {
        return "EfficientMarkov model of order " + keySize;

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
