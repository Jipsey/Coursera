package EarthQuakeData;

import java.util.*;

public class LargestQuakes {


    public void findLargestQuakes() {

        EarthQuakeParser parser = new EarthQuakeParser();
        ArrayList<QuakeEntry> quakeEntryArrayList;
        String source = "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);

        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }

        System.out.println("read data for " + list.size() + " quakes");

        list = getLargest(list,50);
        for (QuakeEntry qe:list){

            System.out.println(qe);
        }
    }

    public int indexOfLargest(ArrayList<QuakeEntry> data) {

        int index = -1;
        double maxMagn = 0;

        for (QuakeEntry qe : data) {
            if (maxMagn < qe.getMagnitude()) {
                maxMagn = qe.getMagnitude();
                index = data.indexOf(qe);
            }
        }

       /* System.out.printf(
                "Index of the location that largest magnitude is %s. (%s)",
                index, maxMagn);
*/
        return index;
    }

    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany) {

        ArrayList<QuakeEntry> answer = new ArrayList<>();
        ArrayList<QuakeEntry> temp = new ArrayList<>(quakeData);
        double maxMagn;

        for (int i = 0; i < howMany; i++) {
            int maxIndex = indexOfLargest(temp);
            QuakeEntry qe = temp.remove(maxIndex);
            answer.add(i, qe);
        }
        return answer;
    }
}
