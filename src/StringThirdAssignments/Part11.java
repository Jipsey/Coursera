package StringThirdAssignments;

import StringSecondAssignments.Part1;
import edu.duke.StorageResource;

public class Part11 {


    public StorageResource getAllGenes(String dna) {
        String gene;
        int cnt;
        Part1 p1 = new Part1();
        StorageResource sr = new StorageResource();

        while (true) {
            gene = p1.findGene(dna);
            if (gene.isEmpty()) {
                break;
            }
            sr.add(gene);

            int x = dna.indexOf(gene);
            gene = gene.concat("X");

            cnt = gene.length() + x;
            dna = dna.substring(cnt);

        }
        return sr;
    }

    public float cgRatio(String dna) {
        String c = "C";
        String g = "G";
        float cntCg = 0;
        int length = dna.length();

        if (dna.contains(c) || dna.contains(g)) {

            while (!dna.isEmpty()) {

                if (dna.startsWith(c) || dna.startsWith(g)) cntCg++;
                dna = dna.substring(1);
            }
            if (cntCg != 0) {
                return cntCg / length;
            }
        }
        return 0;
    }

    public int countCtg(String dna) {
        String ctg = "CTG";
        int cntCtg = 0;
        int ctgIndex;

        if (dna.contains(ctg)) {

            while (dna.contains(ctg)) {

                ctgIndex = dna.indexOf(ctg);

                    cntCtg++;
                    dna = dna.substring(ctgIndex + 3);

            }
            if (cntCtg != 0) {
                return cntCtg;
            }
        }
        return 0;
    }
}
