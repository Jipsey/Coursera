package StringSecondAssignments;

public class Part1 {

    public int findStopCodon(String dna, int startIndex, String stopCodon) {

        String lowDna = dna.toLowerCase();
        String lowStopCodon = stopCodon.toLowerCase();

        if ((lowStopCodon.equals("atg") && lowDna.substring(startIndex).contains(lowStopCodon)) || (
                ((lowDna.indexOf(lowStopCodon) - startIndex) % 3 == 0) &&
                        lowDna.indexOf(lowStopCodon) > 0)) {
            return lowDna.indexOf(lowStopCodon);
        }
        return dna.length();
    }


    public String findGene(String dna) {
        String tempDna = dna.toUpperCase();
        final String ATG ="ATG";
        while (tempDna.contains(ATG)) {

            int startIndex = findStopCodon(tempDna, 0, ATG);

                int taaIndex = findStopCodon(tempDna, startIndex, "TAA");
                int tagIndex = findStopCodon(tempDna, startIndex, "TAG");
                int tgaIndex = findStopCodon(tempDna, startIndex, "TGA");

                int diff1 = taaIndex - startIndex;
                int diff2 = tagIndex - startIndex;
                int diff3 = tgaIndex - startIndex;
                int minDiff = Math.min(Math.min(Math.abs(diff1), Math.abs(diff2)), Math.abs(diff3));

                int endIndex;
                int negativeDiff = Math.negateExact(minDiff);
                if (negativeDiff == diff1 || negativeDiff == diff2 || negativeDiff == diff3) {
                    endIndex = startIndex;
                    startIndex = startIndex - Math.abs(minDiff);
                } else
                    endIndex = startIndex + Math.abs(minDiff);                          //Math.min(taaIndex, Math.min(tagIndex, tgaIndex));

                if (endIndex == tempDna.length() && !tempDna.contains(ATG)) break;

               if (endIndex != tempDna.length())
               return tempDna.substring(startIndex, endIndex + 3);
               else tempDna = tempDna.substring(tempDna.indexOf(ATG) + ATG.length());
            }
        return "";
    }
    public void testFindGene() {

        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";

    }

    public void printAllGenes(String dna) {
        String gene = null;
        int cnt = 0;
        while (cnt <= dna.length()) {

            gene = findGene(dna);
            if (gene == null) {
                break;
            }
            System.out.println(gene);
            dna = dna.substring(gene.indexOf(gene) + gene.length());
            cnt++;
        }
    }
}
