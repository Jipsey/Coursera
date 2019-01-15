package PredictiveText.RandomTextStarterProgram;

import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

        MarkovOne mo = new MarkovOne();
        mo.setRandom(42);
        mo.setTextFromFile();
      System.out.println(mo.getRandomText(67));
//                ArrayList<String> list = mo.getFollows("t");
//        System.out.println(list.size());
//        System.out.println(list);
    }
}
