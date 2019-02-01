package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;
import java.util.Random;

public class MarkovModel extends AbstractMarkovModel{

        private int keySize;

    public MarkovModel(int keySize) {
        this.keySize =keySize;
        myRandom = new Random();
    }

        public void setRandom(int seed) {
        myRandom = new Random(seed);
    }

        public void setTraining(String s) {
        myText = s.trim();
    }

        public void setTextFromFile() {

        String str = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\" +
                "RandomTextStarterProgram\\data\\romeo.txt";
        FileResource fr = new FileResource(str);
        myText = fr.asString()
                .replace("\n", " ");
        setTraining(myText);
    }

        public int getKeySize(){
        return keySize;
        }
        public String getRandomText(int numChars) {
        if (myText == null) {
            return "";
        }
        ArrayList<String> follows;
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length() - keySize);
        String key = myText.substring(index, index + keySize);
        sb.append(key);

        for (int k = 0; k < numChars - keySize; k++) {

            follows = getFollows(key);
            if (follows.size() == 0)
                break;
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            key = key.substring(1) + next;
        }
        return sb.toString();
    }

   public String toString(){
        return "Markov model of order " + keySize;

   }
}
