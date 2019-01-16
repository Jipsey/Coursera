package PredictiveText.RandomTextStarterProgram;

import edu.duke.FileResource;

import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {

//        MarkovFour mf = new MarkovFour();
          MarkovModel mm = new MarkovModel(8);
//          MarkovZero mz = new MarkovZero();
/*
          mz.setRandom(88);
          mz.setTextFromFile();
        System.out.println(mz.getRandomText(65));
*/
                  mm.setRandom(365);
                  mm.setTextFromFile();
        System.out.println(mm.getRandomText(65));
/*
        mf.setRandom(371);
        mf.setTextFromFile();
        System.out.println(mf.getRandomText(65));
*/
//          mf.setTextFromFile();
/*
        MarkovOne mo = new MarkovOne();
        mo.setRandom(273) ;
        mo.setTextFromFile("confucius.txt");
        System.out.println(mo.getRandomText(60));
*/
//      System.out.println(mf.getRandomText(50));
//        System.out.println(mm.getRandomText(60));
/*
        ArrayList<String> list = mo.getFollows("th");
        System.out.println(list.size());
        System.out.println(list);
*/
    }
}
