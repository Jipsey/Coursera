package EarthQuakeData;

import java.util.ArrayList;

public class MatchAllFilter implements Filter {

    private ArrayList<Filter> filters;

    public MatchAllFilter() {
        filters = new ArrayList<>();
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {

        for (Filter filter : filters) {
            if (!filter.satisfies(qe))
                return false;
        }
        return true;
    }

    public void addFilter(Filter filter) {filters.add(filter);
    }

    void testMatchAllFilter(ArrayList<QuakeEntry>list){
       ArrayList<QuakeEntry> filteredList
                  = new ArrayList<>();

       for(QuakeEntry qe:list){
           if(satisfies(qe))
               filteredList.add(qe);
       }

       for (QuakeEntry qe:filteredList){
           System.out.println(qe);
       }
        System.out.println("---------------------------------");
        System.out.printf("Found %s quakes ",
                filteredList.size());
    }


    @Override
    public String getName(){
        StringBuilder sb = new StringBuilder();

        sb.append("Filter used are: ");
        for(Filter filter:filters){
            sb.append(filter.getName())
              .append(" ");
        }
        return sb.toString();
    }
}
