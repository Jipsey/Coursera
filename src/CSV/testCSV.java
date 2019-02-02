package CSV;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class testCSV {
    public static void main(String[] args) {

//        FileResource fr = new FileResource(
//    "C:\\Coursera\\Coursera\\SourceCode\\exportdata.csv");
//        CSVParser parser = fr.getCSVParser();
//        WhichCountriesExport l = new WhichCountriesExport();

//      l.listExporters(parser,"coffee");
//        l.listExportersProduct(parser,"fish","nuts");
//        System.out.println(l.numberOfExporters(parser,"sugar"));
//         l.bigExporters(parser,"$400,000,000");
//        System.out.println(l.coutryInfo(parser,"Nauru"));

//
//        FileResource f = new FileResource("C:\\Coursera\\Coursera\\SourceCode\\data\\2015\\weather-2015-01-02.csv");
//
//        CSVMax max = new CSVMax();
//        CSVRecord largest = max.hottestManyDays();
//        System.out.println("Hottest temperature was " + largest.get("TemperatureF") +
//         " at " + largest.get("TimeEST"));

//        System.out.println("Hottest temperature was " + largest.get("TemperatureF") +
//         " at " + largest.get("DateUTC"));

//
          ColdestDay cd = new ColdestDay();
        System.out.println( cd.fileWithColdestTemperature());


    }

}
