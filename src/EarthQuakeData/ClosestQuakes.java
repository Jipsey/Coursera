package EarthQuakeData;
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData,
                                            Location current, int howMany) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> temp = new ArrayList<>(quakeData);
        double minDist = 0;
        QuakeEntry closestQE = null;
         Location loc = null;

        for (int i = 0; i < howMany; i++) {
            for (QuakeEntry qe : temp) {

                double dist = (qe.getLocation().distanceTo(current))/1000;
                if (minDist == 0 || dist < minDist) {
                    minDist = dist;
                    closestQE = qe;
                }
            }
            if(temp.remove(closestQE)){
               answer.add(closestQE);
               minDist = 0;
            }
        }
            return answer;
    }


    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }


    
}
