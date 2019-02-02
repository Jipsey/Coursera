package StringSecondAssignments;

public class StringTestSecondAssignment {
    public static void main(String[] args) {

//        String a ="aaacsttttgaATGATA";
//        String b ="TGA";
//
//        Part1 p1 = new Part1();
//        System.out.println(p1.findStopCodon(a,0,b));

//    Part2 p2 = new Part2();
//        System.out.println(p2.howMany("TAA","ATAAAAATAATTAA"));

        Part3 p3 = new Part3();               //ATGTAA__ATG___TAG_________ATG______TGAATGTAA
        System.out.println(p3.counterGenes("ATGTAAGATGCCCTAGTATGxxTAAATGyyyyyyTGAATGTAA"));
    }
}
