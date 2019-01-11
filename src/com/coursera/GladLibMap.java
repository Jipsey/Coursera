package com.coursera;

import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.*;

public class GladLibMap {
    private LinkedList<String> usedWords;

    private HashMap<String, ArrayList<String>> myMap;
    private int counterWordsOfTypes =0;
    private Random myRandom;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "C:\\Coding\\Java\\GladLibData\\data\\";

    public GladLibMap() {
        myMap = new HashMap<>();
        initializeFromSource();
        myRandom = new Random();
        usedWords = new LinkedList<>();
    }

    public GladLibMap(String source) {
        initializeFromSource();
        myRandom = new Random();
    }

    private void initializeFromSource() {

        String[] arr = new String[]{"adjective", "noun", "color", "country",
                "name", "animal", "timeframe", "verb", "fruit"};

        String[] arrGladLibCategories = new String[]{"adjective", "noun", "color"};
        for (String s : arr) {
            if (!myMap.containsKey(s))
                myMap.put(s, new ArrayList<>(readIt(s)));
                for(String str:arrGladLibCategories){
                    if (str.equals(s)){
                      counterWordsOfTypes+= myMap.get(s).size();
                    }
                }
        }
    }

    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    public int totalWordsInMap(){
     if (myMap.isEmpty())
         System.out.println("map is empty!");

     int cnt =0;
      for(Map.Entry<String,ArrayList<String>> entry : myMap.entrySet()){

          cnt += entry.getValue().size();
      }
      return cnt;
    }

    private String getSubstitute(String label) {

        if (!myMap.isEmpty())
            for (Map.Entry<String, ArrayList<String>> entry : myMap.entrySet()) {
                if (label.equals("number"))
                    return "" + myRandom.nextInt(50) + 5;
                if (label.equals(entry.getKey()))
                    return randomFrom(entry.getValue());
            }

        return "**UNKNOWN**";
    }


    private String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if ((first == -1 || last == -1)) {
            return w;
        }
        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String sub = "";
        do {
            sub = getSubstitute(w.substring(first + 1, last));
            if (!usedWords.contains(sub))
                usedWords.add(sub);
        }
        while (!usedWords.contains(sub));

        return prefix + sub + suffix;
    }

    private void printOut(String s, int lineWidth) {
        int charsWritten = 0;
        for (String w : s.split("\\s+")) {
            if (charsWritten + w.length() > lineWidth) {
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
        System.out.println("\n\nTotal words in three categories: " + counterWordsOfTypes);
    }

    private String fromTemplate(String source) {
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String typeName) {
        String source = dataSourceDirectory;
        typeName = typeName + ".txt";
        ArrayList<String> list = new ArrayList<String>();

        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source + typeName);
            for (String line : resource.lines()) {
                list.add(line);
            }
        } else {
            FileResource resource = new FileResource(source + typeName);
            for (String line : resource.lines()) {
                list.add(line);
            }
        }
        return list;
    }

    public void makeStory() {
        System.out.println("\n");
        String story = fromTemplate(dataSourceDirectory + "madtemplate2.txt");
        printOut(story, 60);
    }
}
