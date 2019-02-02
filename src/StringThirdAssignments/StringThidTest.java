package StringThirdAssignments;

import StringSecondAssignments.Part2;
import edu.duke.FileResource;
import edu.duke.URLResource;

public class StringThidTest {
    public static void main(String[] args) {
//        String dna ="ATGTAAGATGCCCTAGTATGxxTAAATGyyyyyyTGAATGTAA";
          Part11 p11 = new Part11();
//
//        for (String s:
//        p11.getAllGenes(dna).data()
//                ) {
//            System.out.println(s);
//        }
                      //    --G---G--GCCC
//        System.out.println(p11.cgRatio("ATGTAAGATGCCC"));
//
          URLResource res = new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa");
          String s = res.asString().toUpperCase();
//
           Part33 p33 = new Part33();

 //          p33.printRes(p11.getAllGenes(s));
//
//          System.out.println(p11.countCtg(s));
//
          p33.processGenes(p11.getAllGenes(s));

        Part2 p2 = new Part2();
        System.out.println(p2.howMany("CTG",s));



   }
}
