package GladLibs;

import Arrays.WordLengths;
import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Iterator;

public class WordFrequencies {


    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;

    public WordFrequencies() {
        this.myWords = new ArrayList<String>();
        this.myFreqs = new ArrayList<Integer>();
    }

    public void findUnique(String path) {
        FileResource res = new FileResource(path);
        WordLengths wl = new WordLengths();

        for (String s :
                res.words()) {

//            s = wl.onlyLetters(s.toLowerCase());
            s = s.toLowerCase();
            int index = myWords.indexOf(s);

            if (!myWords.contains(s)) {

                myWords.add(s);
                myFreqs.add(1);

            } else {
                int value = myFreqs.get(index);
                myFreqs.set(index, value + 1);
            }
        }

        System.out.println("Unique words: " + myWords.size());
        for (int i = 0; i < myWords.size(); i++) {
            System.out.println(myFreqs.get(i) + "\t" + myWords.get(i));
        }
    }

    public int findIndexOfMax() {
        int index = 0;
        int value = 0;
        Iterator <Integer> iterator = myFreqs.iterator();

        while (iterator.hasNext()) {

            if (value < iterator.next()) {
                value = iterator.next();
                index = myFreqs.indexOf(value);
            }

        }

        return index;
    }

    public void tester (String s){
        findUnique(s);

        System.out.println(" Most often occurs word " + myWords.get(findIndexOfMax()));
        System.out.println(myFreqs.get(findIndexOfMax()));

    }
}