package com.coursera;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CodonDNA {


    private HashMap<String,Integer> CodonMap;

    public CodonDNA() {

        CodonMap = new HashMap<>();
    }

    void  printCodonCounts(int start, String dna){

        buildCodonMap(start,dna);
        Set<Map.Entry<String,Integer>> set = CodonMap.entrySet();
        for (Map.Entry<String,Integer> map: set) {
            System.out.print(map.getKey() + " : ");
            System.out.println(map.getValue());
        }
        System.out.println("----------------");
        System.out.println("the most common codon is: " + getMostCommonCodon());
        System.out.println("unique codons are: " + CodonMap.size());
    }



    String getMostCommonCodon(){
       int numb = 0;
       String key = "";
       if (!CodonMap.isEmpty())
         {
             for (String s:CodonMap.keySet()) {
                   if(CodonMap.get(s) > numb)
                   {
                       numb = CodonMap.get(s);
                       key = s;
                   }
             }
             return key;
         }
        return "";
    }

    void buildCodonMap(int start, String dna){
       String newDNA = dna.substring(start);
       String subsDna = "";
       int dnaLength = 3;
       int tactCnt = (int) Math.floor(newDNA.length()/dnaLength);// количество тактов
       int dnaCnt = 0;
       int cnt = 1;
            while (tactCnt != 0) {
                subsDna = newDNA.substring(dnaCnt, dnaCnt + dnaLength);

                if(CodonMap.containsKey(subsDna))
                {  cnt = CodonMap.get(subsDna);
                   CodonMap.put(subsDna,++cnt);
                   cnt = 1;
                }
                else
                   CodonMap.put(subsDna,cnt);

                dnaCnt += 3;
                tactCnt--;
            }
    }
}