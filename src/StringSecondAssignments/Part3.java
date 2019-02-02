package StringSecondAssignments;


public class Part3 {

    public int counterGenes(String dna){
        Part1 p1 = new Part1();
        int cnt =0;
        int dnaCnt =0;

        String tempDna;

        do {
            tempDna = p1.findGene(dna.substring(cnt));
            cnt += tempDna.length();
            if(!tempDna.isEmpty()){
            System.out.println(tempDna);
            dnaCnt++;
            } else cnt +=3 ;
        }
        while (cnt < dna.length());
        return dnaCnt ;
    }
}
