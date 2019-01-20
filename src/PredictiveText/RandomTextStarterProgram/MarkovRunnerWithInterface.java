package PredictiveText.RandomTextStarterProgram;
/**
 * Write a description of class MarkovRunner here.
 *
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MarkovRunnerWithInterface {
    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.getRandomText(seed);

        System.out.println("running with " + markov);
        for (int k = 0; k < 3; k++) {
            String st = markov.getRandomText(size);
            printOut(st);
        }
    }

    public void runMarkov() {

        String str = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\" +
                "RandomTextStarterProgram\\data\\confucius.txt";

        FileResource fr = new FileResource(str);
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;

        //  MarkovZero mz = new MarkovZero();
        //  runModel(mz, st, size,5);

        //  MarkovOne mOne = new MarkovOne();
        //  runModel(mOne, st, size, 5);

        //  MarkovModel mThree = new MarkovModel(3);
        //  runModel(mThree, st, size, 5);

        //  MarkovFour mFour = new MarkovFour();
        //  runModel(mFour, st, size,5);

    }

    public void testHashMap() {

        EfficientMarkovModel efficientMarkovModel =
                new EfficientMarkovModel(2);
        efficientMarkovModel.setRandom(42);
        efficientMarkovModel.setTraining("yes-this-is-a-thin-pretty-pink-thistle");
        System.out.println(efficientMarkovModel.getRandomText(50));
    }

    public void compareMethods() {

        String path = "C:\\Users\\Sanek\\IdeaProjects\\Coursera\\src\\PredictiveText\\RandomTextStarterProgram\\" +
                "data\\hawthorne.txt";
        FileResource fr = new FileResource(path);
        String fileContent = fr.asString().replace("\n"," ");

        MarkovModel markovModel = new MarkovModel(2);
        long start = System.nanoTime();
        runModel(markovModel, fileContent, 1000, 42);
        long end = System.nanoTime();

        double calc = (double) (end - start) / 9 * 10;
        System.out.printf("markovModel-%s works %s seconds\n",
                markovModel.getKeySize(), calc);

        EfficientMarkovModel efficientMarkovModel = new EfficientMarkovModel(2);
        start = System.nanoTime();
        runModel(efficientMarkovModel, fileContent, 1000, 42);
        end = System.nanoTime();

        calc = (double) (end - start) / 9 * 10;
        System.out.printf("efficientMarkovModel-%s works %s seconds\n",
                efficientMarkovModel.getKeySize(), calc);
    }

    public String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        File file = new File(path);
        BufferedReader buff = null;
        try (FileInputStream is = new FileInputStream(file)) {
            buff = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            String line;
            while ((line = buff.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {

        }

        return sb.toString();
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
}
