package PredictiveText.RandomTextStarterProgram;
/**
 * Write a description of class MarkovRunner here.
 *
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*;

import java.util.concurrent.TimeUnit;

public class MarkovRunner {


    public void runModel(IMarkovModel markov, String text, int size) {
        markov.setTraining(text);
        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }


    public void runMarkovZero() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovZero markov = new MarkovZero();
        markov.setRandom(101);
        markov.setTraining(st);
        for (int k = 0; k < 3; k++) {
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }

    public void runMarkovOne() {

        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setRandom(101);
        markov.setTraining(st);
        for (int k = 0; k < 3; k++) {
            String text = markov.getRandomText(500);
            printOut(text);
        }
    }

    private void printOut(String s) {
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for (int k = 0; k < words.length; k++) {
            System.out.print(words[k] + " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println();
                psize = 0;
            }
        }
        System.out.println("\n----------------------------------");
    }

    public void compareMethods() {

        String path = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\RandomTextStarterProgram\\" +
                "data\\hawthorne.txt";

        FileResource fr = new FileResource(path);
        String str = fr.asString()
                .replace("\n", " ");


        MarkovWord mw = new MarkovWord(2);
        mw.setRandom(42);
        mw.setTraining(str);

        EfficientMarkovWord emw = new EfficientMarkovWord(2);
        emw.setRandom(42);
        emw.setTraining(str);


        long current = System.nanoTime();
        mw.getRandomText(100);
        long worktime = System.nanoTime();

        long result = TimeUnit.NANOSECONDS.toMillis(worktime-current);

        System.out.println("MarkovWord .getRandom() method " + result + " milliseconds");

        current = System.nanoTime();
        emw.getRandomText(100);
        worktime = System.nanoTime();
        result = TimeUnit.NANOSECONDS.toMillis(worktime-current);


        System.out.println("EfficientMarkovWord .getRandom() method " + result + " milliseconds");
    }

    public void testHashMap() {
        EfficientMarkovWord effMW = new EfficientMarkovWord(2);
        effMW.setRandom(42);
        effMW.setTraining("this is a test yes this is really a test yes a test this is wow");
        System.out.println(effMW.getRandomText(50));


    }
}
