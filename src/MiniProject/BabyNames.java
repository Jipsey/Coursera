package MiniProject;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class BabyNames {


    public void totalBirths(){

        FileResource fr = new FileResource();

        int rowQ = 0;

        int totalBabies = 0;
        int totalBoys = 0;
        int totalGirls =0;
        int boysNames = 0;
        int girlNames = 0;

        for (CSVRecord rec :fr.getCSVParser(false)
             ) {
             rowQ = Integer.parseInt(rec.get(2));
           totalBabies += rowQ;

           if(rec.get(1).equals("M")){
               totalBoys += rowQ;
               boysNames++;}

           else {
               totalGirls += rowQ;
               girlNames++;
           }
        }


        System.out.print("Total births: " + totalBabies +"\n" + "Total boys: " + totalBoys + "\n"
                   + "Total girls: " + totalGirls + "\n" + "Quantity boy`s names : " + boysNames +"\n" +
                "Quantity girl`s names : " + girlNames +"\n");

    }


     public int getRank(int year, String name, String gender){

         FileResource fr =
                new FileResource("C:\\Coursera\\Coursera\\SourceCode\\us_babynames_by_year\\yob" + year +".csv" );
         int rank = -1;
         int genderCnt = 0;

         for (CSVRecord rec:fr.getCSVParser(false)
              ) {

               if(rec.get(1).equals(gender))
               {
               genderCnt ++;
               if(rec.get(0).equals(name) && rec.get(1).equals(gender))
                    rank = genderCnt;
               }
         }

         return rank;
     }


     public String getName(int year, int rank, String gender){

         String name = "NO NAME";
         int cnt = 0;
         FileResource fr =
                 new FileResource("C:\\Coursera\\Coursera\\SourceCode\\us_babynames_by_year\\yob" + year +".csv" );

         for (CSVRecord rec:fr.getCSVParser(false)
                 ) {
              if(gender.equals(rec.get(1))){
                  cnt++;
                 if(rank == cnt)
                 name = rec.get(0);
              }
         }
         return name;
     }

     public void whatIsNameYear(String name, int year, int newYear, String gender){

         int rankOfYear = -1;

         String nameOfNewYear = "NO NAME";

         FileResource fr =
                 new FileResource("C:\\Coursera\\Coursera\\SourceCode\\us_babynames_by_year\\yob" + year +".csv" );

            rankOfYear = getRank(year,name,gender);

            if (rankOfYear != -1)
               nameOfNewYear = getName(newYear,rankOfYear,gender);


         System.out.println(name + " born in " + year + " would be " + nameOfNewYear +
                            " if she was born in " + newYear );

     }

     public int  yearOfHighestRank(String name, String gender){

         int rank = -1;
         int year = -1;
         int tempRank;

         DirectoryResource dr = new DirectoryResource();

         for (File f : dr.selectedFiles()
              ) {

             int tempYear = parseYear( f.getName());

             tempRank =  getRank(tempYear,name,gender);

             if((tempRank < rank || rank == -1) && tempRank != -1){
                 rank=tempRank;
                 year = tempYear;
             }
         }

         return year;
     }


     public double getAvarageRank (String name, String gender){


       DirectoryResource dr = new DirectoryResource();
       int cnt = 0;
       double avg = 0;
       for (File f : dr.selectedFiles()
              ) {
             cnt++;
           avg +=  getRank( parseYear( f.getName()),name,gender);

         }

         return avg/cnt;
     }


     public int getTotalBirthsRankedHigher (int year, String name, String gender){

        int cnt=0;
        DirectoryResource dr = new DirectoryResource();



             FileResource fr = new FileResource("C:\\Coursera\\Coursera\\SourceCode\\us_babynames_by_year\\" +
                     "yob" +  year + ".csv");
             for(CSVRecord rec : fr.getCSVParser(false)) {
                 if (rec.get(0).equals(name) && rec.get(1).equals(gender))
                     break;
                 else if (rec.get(1).equals(gender))
                     cnt += Integer.parseInt(rec.get(2));
             }


        return cnt;
     }


    private int parseYear (String s){

         StringBuilder sb = new StringBuilder();

         for (int i = 0; i <s.length() ; i++) {

             if (Character.isDigit(s.charAt(i)))
                sb.append(s.charAt(i));
         }

       return Integer.parseInt(sb.toString());

     }
}
