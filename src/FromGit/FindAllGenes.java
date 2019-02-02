package FromGit; /**
 * Write a description of Part3 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import edu.duke.*;
public class FindAllGenes {
    public void ProcessGenes (StorageResource sr){
        int nCount = 0;
        int rCount = 0;
        int sCount = 0;
        int geneLength = 0;
        String longestGene = "";
        for (String d: sr.data()){

            if (d.length()>9){
                nCount = nCount + 1;
            }
            float ratio = cgRatio(d);
            if (ratio>0.35){
                rCount = rCount + 1;
            }

            if (d.length() > geneLength){
                geneLength = d.length();
                longestGene = d;
            }
            if (d.length()>60){
                sCount = sCount+1;
              }
        }
        System.out.println("Number of DNA strands that have 60 or more characters: " + sCount);
        System.out.println("The length of the Longest Gene in the DNA strand is: " + longestGene.length());
        System.out.println("Number of DNA strands that have a higher CG ration than 0.35: " + rCount);
        System.out.println("Number of DNA strands longer than 9 characters: " + nCount);
    }

    public float cgRatio (String dna){

        float cntCG = 0.0f;
        String upDNA = dna.toUpperCase();

        while (upDNA.contains("C") || upDNA.contains("G")) {

            if (upDNA.startsWith("C") || upDNA.startsWith("G")) {
                cntCG++;
            }
            upDNA = upDNA.substring(1);
        }

        float answer = cntCG/dna.length();
        return answer;
    }

    public void testProcessGenesFromFile(){
        URLResource fr = new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();

        StorageResource geneList = getAllGenes(dna);
        ProcessGenes(geneList);
    }

    public int findStopCodon(String dna, int startIndex, String stopCodon){
        int currIndex = dna.indexOf(stopCodon, startIndex+3);
        while (currIndex != -1){
            int diff = currIndex - startIndex;
            if (diff % 3 == 0){
                return currIndex;
            }
            else {
                currIndex = dna.indexOf(stopCodon, currIndex+1);
            }
        }
        return  -1;
    }

    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG", where);
        if (startIndex == -1){
            return "";
        }
        int taaIndex = findStopCodon (dna, startIndex, "TAA");
        int tagIndex = findStopCodon (dna, startIndex, "TAG");
        int tgaIndex = findStopCodon (dna, startIndex, "TGA");
        int minIndex = 0;
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        }
        else{
            minIndex = taaIndex;
        }
        if (minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)){
            minIndex = tagIndex;
        }
        if (minIndex == -1){
            return "";
        }
        return dna.substring(startIndex, minIndex + 3);
    }

    public StorageResource getAllGenes(String dna){
        int startIndex = 0;
        int count = 0;
        StorageResource geneList = new StorageResource();
        while (true){
            String currentGene = findGene(dna, startIndex);
            if (currentGene.isEmpty()){
                break;
            }
            geneList.add(currentGene);
            startIndex = dna.indexOf(currentGene, startIndex) + currentGene.length();
            count = count + 1;
        }
        System.out.println("Number of Genes is/are: " + count);
        return geneList;
    }


    public static void main(String[] args) {

         FindAllGenes f = new FindAllGenes();
         f.testProcessGenesFromFile();
    }
}
