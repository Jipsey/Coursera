package PredictiveText.RandomTextStarterProgram;

import java.util.Arrays;
import java.util.Objects;

public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt " + index);
        }
        return myWords[index];
    }

    public int length() {
        // TODO: Complete this method

        return myWords.length;
    }

    public String toString() {
        //String ret = "";
        StringBuilder sb = new StringBuilder();
        // TODO: Complete this method

        for (String word : myWords) {
            sb.append(word)
                    .append(" ");
        }
        //return ret.trim();
        return sb.toString();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        if (this == other)
            return true;

        if (other == null || this.getClass() != other.getClass())
            return false;

        if (this.length() != other.length())
            return false;

        if(this.hashCode() != other.hashCode())
            return false;

        for (int i = 0; i < other.length(); i++) {

            if (!this.wordAt(i).equals(other.wordAt(i)))
                return false;
        }

        return true;
    }

    public WordGram shiftAdd(String entryWord) {
        // shift all words one towards 0 and add word at the end.
        // you lose the first word
        // TODO: Complete this method
        String[] array = new String[myWords.length];
        array[myWords.length - 1] = entryWord;
        System.arraycopy(myWords, 1, array, 0, this.length() - 1);
        WordGram out = new WordGram(array, 0, array.length);
        return out;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(myHash);
        result = 31 * result + Arrays.hashCode(myWords);
        return result;
    }
}