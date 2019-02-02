package CSV;


import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class ColdestDay {

    public double coldestTempInFile(CSVParser parser) {

        double lowTemp = Double.MAX_VALUE;
        double currentTemp;

        for (CSVRecord rec : parser) {

            currentTemp = Double.parseDouble(rec.get("TemperatureF"));

            if(currentTemp < lowTemp) {
                lowTemp = currentTemp;
            }
        }

        return lowTemp;
    }


    public String fileWithColdestTemperature() {

        String fileName = null;

        DirectoryResource dr = new DirectoryResource();

        double currentTemp;
        double coldestTemp = Double.MAX_VALUE;

        for (File f : dr.selectedFiles()
                ) {
            FileResource fr = new FileResource(f);

            currentTemp = coldestTempInFile(fr.getCSVParser());

            if (currentTemp < coldestTemp && currentTemp!= -9999d) {
                coldestTemp = currentTemp;
                fileName = f.getName();
            }
        }
        return fileName;
    }
}
