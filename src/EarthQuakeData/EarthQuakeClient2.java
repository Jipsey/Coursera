package EarthQuakeData;

import java.util.ArrayList;

public class EarthQuakeClient2 {


    ArrayList<QuakeEntry> quakesWithFilter(
            ArrayList<QuakeEntry> quakeData, ArrayList<Filter> filters) {
        ArrayList<QuakeEntry> answer = new ArrayList<>();
        boolean flag = true;
        for (QuakeEntry qe : quakeData) {
            for (Filter filter : filters) {
                flag = filter.satisfies(qe);
                if(!flag)
                    break;
            }
            if (flag)
                answer.add(qe);
        }
        return answer;
    }


    public void testMatchAllFilter2(ArrayList<QuakeEntry> list) {

        ArrayList<QuakeEntry> answer = new ArrayList<>();
        MatchAllFilter maf = new MatchAllFilter();
        maf.addFilter(new MagnitudeFilter(0,5));
   //     maf.addFilter(new DepthFilter(-180000,-30000));
              maf.addFilter( new DistanceFilter(
              new Location(55.7308, 9.1153), 3000000));
        maf.addFilter(new PhraseFilter("any", "e"));

        for (QuakeEntry qe:list) {
           if (maf.satisfies(qe))
               answer.add(qe);
        }

        for(QuakeEntry qe:answer){
            System.out.println(qe);
        }
        System.out.println("Size of filtered data is " + answer.size());
        System.out.println(maf.getName());
    }
}
