package Arrays;

import edu.duke.FileResource;

import java.util.Arrays;
import java.util.Iterator;

public class WordLengths {


    public int[] countWordLengths(FileResource res, int[] cnt) {
        int arrLength = cnt.length;
        int wordLength;

        for (String word:
                res.words()) {

            word = onlyLetters(word);

            wordLength = word.length();

            if(wordLength <= arrLength)
                cnt[wordLength]++;

             else if(wordLength > arrLength  )
                 cnt[arrLength-1]++;
        }

        return cnt;
    }

    public String onlyLetters(String s){

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i <s.length() ; i++) {

            char ch = s.charAt(i);

            if (Character.isLetter(ch) || ch == '\'')
                sb.append(ch);
        }

        return  sb.toString();
    }

    public int indexOfMax(int [] val){
       // String alph = "abcdefghijklmnopqrstuvwxyz";
        int index = 0;
        for (int i = 0; i < val.length ; i++) {

            if (val[i] > val[index])
            {
                 index = i;
            }
        }
       // System.out.println("The most common letter is :  " + alph.charAt(index));
        return index;
    }
}