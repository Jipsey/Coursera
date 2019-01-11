package com.coursera;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordsInFile {


    private HashMap<String, ArrayList<String>> mapWord; // map which store pair of key(word) and value(files)
    private HashMap<String,Integer> mapWordUnique;
    int cnt = 0;

    public WordsInFile() {
        mapWord = new HashMap<>();
        mapWordUnique = new HashMap<>();
     }

    public void buildWordFileMap() {
        mapWord.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }


    public void printNumOccuredWordsInMap(){ //method which prints number of occured words
        if(mapWord.isEmpty())
            System.out.println("Map is empty! ");

        System.out.println("Total occured wors: " + mapWord.size());

    }

    int maxNumber() {

        int max = -1;
        if (mapWord.isEmpty())
            return -1;

        for (ArrayList<String> arr : mapWord.values()) {
            if (max < 0)
                max = arr.size();
            else if (max < arr.size())
                max = arr.size();
        }
        return max;
    }

    public int totalWords(){
         int cnt =0;
        for(Map.Entry<String,ArrayList<String>> entry : mapWord.entrySet()){

           cnt += entry.getValue().size();
            System.out.println(entry.getKey());
        }

        return cnt;
    }

    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> arr = new ArrayList<>();

        for (Map.Entry<String, ArrayList<String>> entry : mapWord.entrySet()) {

            if (entry.getValue().size() == number)
                arr.add(entry.getKey());
        }
        return arr;
    }


    void printFilesIn(String word) {
        ArrayList<String> arr;
        arr = new ArrayList<>();
        for (Map.Entry<String, ArrayList<String>> entry : mapWord.entrySet()) {
            if(entry.getKey().equals(word)){
                arr = entry.getValue();
            }
        }
        if (!arr.isEmpty()){
            System.out.println("word: \"" + word + "\"" +" appears in files: ");
            for ( String s : arr){

                System.out.println(s + ";");
            }
        }
    }


    void appearWordInFiles(String word){

        ArrayList<String> arrFile = new ArrayList<>();

        DirectoryResource dr = new DirectoryResource();

        for(File f:dr.selectedFiles()){
            FileResource fr = new FileResource(f);
             for(String w :fr.words()){
                 if(w.equals(word))
                     arrFile.add(f.getName());
             }

        }
         if(arrFile.isEmpty())
             System.out.println("entry word is not appears in chosen files!");

         for (String s:arrFile){
             System.out.println( s + " appear entry word \"" + word + "\"");
         }
    }

    private void builMapUniqeWords(String word){
        int cnt;
        if(mapWordUnique.isEmpty() || !mapWordUnique.containsKey(word)){
            mapWordUnique.put(word,1);
        }
          else{
              cnt = mapWordUnique.get(word);
              mapWordUnique.put(word,++cnt);
        }
    }

    public void mostOccuredWord(){
        if (mapWordUnique.isEmpty()) {
            System.out.println(" Map is empty!");
        }
        String str= "";
        int num = 0;
        for(Map.Entry<String,Integer> entry: mapWordUnique.entrySet()){

                if(entry.getValue() > num) {
                    num = entry.getValue();
                    str = entry.getKey();
                }
            }
        System.out.println(" The most occured word is: "+ str +"(" + num +")");

    }

    private void addWordsFromFile(File f) {
        String fileName;
        ArrayList<String> filesList;
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {

            //word = word.toLowerCase();
            builMapUniqeWords(word);
            if (mapWord.isEmpty() || !mapWord.containsKey(word)) {
                fileName = f.getName();
                filesList = new ArrayList<>();
                filesList.add(fileName);
                mapWord.put(word, filesList);
            } else {
                fileName = f.getName();
                filesList = mapWord.get(word);
                if (filesList.contains(fileName))
                    mapWord.put(word, filesList);
                else {
                    filesList.add(fileName);
                    mapWord.put(word, filesList);
                }
            }
        }
    }
}
