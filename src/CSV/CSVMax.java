package CSV;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {

        CSVRecord largestSoFar = null;

        for (CSVRecord currRow : parser
                ) {
            largestSoFar = getLargest( currRow, largestSoFar );
        }
        return largestSoFar;
    }

    public CSVRecord hottestManyDays() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord largestSoFar = null;

        for (File f : dr.selectedFiles()
                ) {
            FileResource fr = new FileResource(f);

            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());

            largestSoFar = getLargest(currentRow,largestSoFar);
        }
        return largestSoFar;
    }

    public CSVRecord getLargest(CSVRecord currentRow,CSVRecord largestSoFar){

        if (largestSoFar == null) {

            largestSoFar = currentRow;
        } else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            if (currentTemp > largestTemp) {
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }
}