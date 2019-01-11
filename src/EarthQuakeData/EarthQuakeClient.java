package EarthQuakeData;

import java.util.*;

import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
                                                   double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();

        for (QuakeEntry qe : quakeData) {

            double mag = qe.getMagnitude();
            if (mag > magMin) {
                answer.add(qe);
            }

        }

        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
                                                      double distMax,
                                                      Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            Location loc = qe.getLocation();
            double dist = (double) from.distanceTo(loc);
            if (dist < distMax * 1000) {
                answer.add(qe);
            }

        }
        return answer;
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

    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source =
                "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        double minMagn = 5.0;

        System.out.println("read data for " + list.size() + " quakes");
        list = filterByMagnitude(list, minMagn);

        System.out.println("----------------------------------------------------");
        System.out.printf("Found %s quakes which magnitude more than %s \n",
                list.size(), minMagn);
        for (QuakeEntry qe : list) {
            System.out.println(qe.toString());
        }
    }


    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();

        for (QuakeEntry qe : quakeData) {
            double depth = qe.getDepth();
            if (depth > minDepth && depth < maxDepth) {
                answer.add(qe);
            }
        }
        return answer;
    }


    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source =
                "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        ArrayList<QuakeEntry> filteredList =
                filterByDepth(list, -10000, -8000);

        for (QuakeEntry qe : filteredList) {

            System.out.println(qe);
        }
        System.out.printf("Found %s quakes that match hat criteria", filteredList.size());
    }

    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData,
                                                String whereToSearch, String phrase) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();


        switch (whereToSearch) {
            case "start":
                for (QuakeEntry qe : quakeData) {
                    if (qe.getInfo().startsWith(phrase))
                        answer.add(qe);
                }
                break;
            case "end":
                for (QuakeEntry qe : quakeData) {
                    if (qe.getInfo().endsWith(phrase))
                        answer.add(qe);
                }
                break;
            default:
                for (QuakeEntry qe : quakeData) {
                    if (qe.getInfo().contains(phrase))
                        answer.add(qe);
                }
                break;
        }
       return  answer;
    }

    public  void quakeByPhrase(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String whereToSerch = "any";
        String phrase = "Creek";
        String source =
                "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        ArrayList<QuakeEntry> filteredList =
        filterByPhrase(list,whereToSerch,phrase);

        for (QuakeEntry qe:filteredList){
            System.out.println(qe);
        }

        System.out.printf("Found %s quakes that match %s at %s",
                filteredList.size(),phrase,whereToSerch);
    }

    public void closeToMe() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "C:\\Coding\\Java\\SearchingEarthquakeDataStarterProgram\\data\\nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for " + list.size() + " quakes");

        // This location is Durham, NC
        // Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        Location city = new Location(38.17, -118.82);
        double maxDistance = 1000;


        ArrayList<QuakeEntry> filteredList =
                filterByDistanceFrom(list, maxDistance, city);
        System.out.println("--------------------------------------");

        for (QuakeEntry qe : filteredList) {
            System.out.println(qe);
        }
        System.out.printf(
                "Found %s quakes whithin radius of %s km \n",
                filteredList.size(), maxDistance);
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
 }
