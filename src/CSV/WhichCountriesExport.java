package CSV;

import edu.duke.FileResource;
import edu.duke.StorageResource;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVParser;

public class WhichCountriesExport {

    public void listExporters(CSVParser parser, String exportOfInterest){

        for (CSVRecord record :
                parser) {
         String  export = record.get("Exports").toLowerCase();
        if(export.contains(exportOfInterest.toLowerCase())){
            String country = record.get("Country").toLowerCase();
            System.out.println(country);
         }
       }
    }

    public String coutryInfo(CSVParser parser, String country){


        for (CSVRecord rec : parser){
            String c = rec.get("Country");
            if(c.contains(country)) {

                StringBuilder sb = new StringBuilder(country);
                sb.append(": ").append(rec.get("Exports")).append(rec.get("Value (dollars)"));

                return sb.toString();
            }
        }

        return "NOT FOUND";
    }



    public void listExportersProduct(CSVParser parser, String exportItem1, String exportItem2){


        for (CSVRecord rec :
                parser) {
            String x = rec.get("Exports");
            if (x.contains(exportItem1) && x.contains(exportItem2))
            {
                System.out.println(rec.get("Country"));
            }
        }
    }


    public int numberOfExporters(CSVParser parser, String exportItem){
        StorageResource storage = new StorageResource();
        int cnt=0;

        for (CSVRecord rec : parser
                ) {
            String x =rec.get("Exports");
            if(x.contains(exportItem)){

                storage.add(rec.get("Country"));
            }
        }
        return storage.size();

    }


    public void bigExporters(CSVParser parser, String amount){
        StringBuilder sb = new StringBuilder();

        for (CSVRecord rec:parser
             ) {
            int x = Integer.getInteger(rec.get("Value (dollars)"));
            if (x >= Integer.getInteger(amount))
            {
              sb.append(rec.get("Country")).append(" ")
              .append(rec.get("Value (dollars)"));
            }
        } if (!sb.toString().isEmpty())
            System.out.println(sb);

    }




}
