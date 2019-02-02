package StringThirdAssignments;

import edu.duke.StorageResource;

public class Part33 {

    public void processGenes (StorageResource sr){
        int barrier = 60;
        int longestDNA = 0;
        float barrierCg = 0.35f;
        StorageResource srcChar = new StorageResource();
        StorageResource srCGratio = new StorageResource();

        Part11 p11 = new Part11();


        for (String s:
             sr.data()) {

            if (s.length() > barrier)
                srcChar.add(s);

            if (p11.cgRatio(s) > barrierCg)
                srCGratio.add(s);

            if(s.length() > longestDNA)
                longestDNA = s.length();
        }

        System.out.println("The numder of Strings in sr that are longer than " + barrier + " characters " + srcChar.size());
 //       printRes(srcChar);
        System.out.println("The number of strings in sr whose C-G-ratio is higher than " + barrierCg + " "+srCGratio.size());
 //       printRes(srCGratio);
        System.out.println("Longest DNA, is size " + longestDNA);

        System.out.println("Overall quantity of DNA is " +sr.size() );
    }

    public void printRes(StorageResource res){

        for (String s:res.data()
             ) {
            System.out.println(s);
        }
    }
}
