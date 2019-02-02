package StringsFirstAssignments;
/**
 * Write a description of class Part1 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import edu.duke.*;

public class Part1
{

    public String findSimpleGene(String dna){

        String start = "ATG";
        String stop = "TAA";

        int startIndex = dna.indexOf(start);
        int stopIndex = dna.indexOf(stop);
        int currIndex = dna.indexOf(startIndex+3);
        while(currIndex != -1)
            if((currIndex - startIndex) % 3 == 0){
                return dna.substring(startIndex, stopIndex + 3);
            }
        return"";
    }

    public String findGene(String st,String sp, String dna,boolean x){

        String start = st;
        String stop = sp;
        boolean multipleThree = x;
        int startIndex = dna.indexOf(start);
        int stopIndex = dna.indexOf(stop);
        int currIndex = dna.indexOf(startIndex+3);

        while(currIndex != -1){
            if((currIndex - startIndex) % 3 == 0 & multipleThree == true)
                return dna.substring(startIndex, stopIndex + 3);
            else return dna.substring(startIndex, stopIndex + 3);
        }
        return "";
    }

    public void testSimpleGene(){

        URLResource res = new URLResource("http://www.dukelearntoprogram.com/course2/data/dna/GRch38dnapart.fa");

        String withoutATG = null;//DNA without ATG codon
        String withoutTAA = null;//DNA without TAA codon
        String withoutATGorTAA = null;//DNA without ATG or TAA codon
        String multipleThree = null; // DNA with ATG,TAA codons and multiple 3
        String notMultipleThree = null;// DNA with ATG,TAA codons and NOT multiple 3


        for(String word : res.words()){
            withoutATG = findGene("TAA","TAA",word,true);
            withoutTAA = findGene("ATG","ATG",word,true);
            //if(words.contains("")){withoutATGorTAA = findGene(ATG,-1,word);};
            multipleThree = findGene("ATG","TAA",word,true);
            notMultipleThree  = findGene("ATG","TAA",word,false);
        }

        System.out.println( "DNA without ATG codon " + withoutATG + "\n"
                +  "DNA without TAA codon " + withoutTAA + "\n"
                + "DNA without ATG or TAA" + withoutATGorTAA +"\n"
                + "multipleThree" + multipleThree + "\n"
                + "notMultiple" + notMultipleThree + "\n");

    }
}

