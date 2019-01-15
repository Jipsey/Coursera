package EarthQuakeData;
/**
 * Write a description of class QuakeSortInPlace here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

import java.util.*;

import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }

    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = -1;
        double minMagn = Double.MAX_VALUE;

        for (; from < quakes.size(); from++) {
            double current = quakes.get(from).getMagnitude();
            if (current < minMagn || minIdx == -1) {
                minIdx = from;
                minMagn = current;
            }
        }
        return minIdx;
    }

    public void sortByMagnitude(ArrayList<QuakeEntry> in) {

        for (int i = 0; i < in.size(); i++) {
            int minIdx = getSmallestMagnitude(in, i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i, qmin);
            in.set(minIdx, qi);
        }

    }

    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted) {


        for (int i = 0; i < quakeData.size() - (numSorted + 1); i++) {

            double cur = quakeData.get(i).getMagnitude();
            double next = quakeData.get(i + 1).getMagnitude();
            if (cur > next) {
                QuakeEntry currQE = quakeData.get(i);
                QuakeEntry nextQE = quakeData.get(i + 1);
                quakeData.set(i, nextQE);
                quakeData.set(i + 1, currQE);
            }
        }
    }

    boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes) {


        for (int i = 0; i < quakes.size() - 1; i++) {

            QuakeEntry current = quakes.get(i);
            QuakeEntry next = quakes.get(i + 1);

            if (current.getMagnitude() > next.getMagnitude())
                return false;
        }

        return true;
    }

    void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in) {

        for (int i = 0; i < in.size() - 1; i++) {
            onePassBubbleSort(in, i);
            if (checkInSortedOrder(in)) {
                System.out.printf("we needed %s passes to sort \n", i + 1);
                break;
            }
        }
    }

    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in) {

        for (int i = 0; i < in.size() - 1; i++) {
            System.out.println("***************");
            System.out.println("after pass " + i);
            for (QuakeEntry quakeEntry : in) {
                System.out.println(quakeEntry);
            }
            onePassBubbleSort(in, i);
        }
    }


    public int getLargestDepth(ArrayList<QuakeEntry> quakeData, int from) {

        int indexOfMaxDepth = -1;
        double maxDepth = Double.MIN_VALUE;

        for (; from < quakeData.size(); from++) {

            double currentDepth = quakeData.get(from).getDepth();
            if (currentDepth > maxDepth || indexOfMaxDepth == -1) {
                indexOfMaxDepth = from;
                maxDepth = currentDepth;
            }
        }

        return indexOfMaxDepth;
    }

    public void sortByLargestDepth(ArrayList<QuakeEntry> in) {

        for (int i = 0; i < 50; i++) {

            int indexMax = getLargestDepth(in, i);
            QuakeEntry cur = in.get(i);
            QuakeEntry max = in.get(indexMax);
            in.set(i, max);
            in.set(indexMax, cur);

        }

        System.out.println("***********");
        System.out.println("After 50 passes ");

    }

    void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in) {

        for (int i = 0; i < in.size(); i++) {
            int minIdx = getSmallestMagnitude(in, i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i, qmin);
            in.set(minIdx, qi);
            if (checkInSortedOrder(in)) {
                System.out.printf("we needed %s passes to sort \n", i + 1);
                break;
            }
        }
    }

    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "C:\\Coding\\Java\\EarthquakeSortStarterProgram\\" +
                "data\\earthQuakeDataWeekDec6sample2.atom";
        ArrayList<QuakeEntry> list = parser.read(source);

        System.out.println("read data for " + list.size() + " quakes");
        //  sortByMagnitude(list);
        //sortByMagnitudeWithBubbleSort(list);
        //sortByMagnitudeWithBubbleSortWithCheck(list);
//        sortByMagnitudeWithCheck(list);
//        sortByLargestDepth(list);
//          sortByMagnitudeWithCheck(list);
        sortByMagnitudeWithBubbleSortWithCheck(list);
         System.out.println("After sorting: ");
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }

    }


    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for (QuakeEntry qe : list) {
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                    qe.getLocation().getLatitude(),
                    qe.getLocation().getLongitude(),
                    qe.getMagnitude(),
                    qe.getInfo());
        }

    }
}
