package BuildRecommendationSystem;

import java.util.ArrayList;

public interface Rater {

    void addRating(String item, double rating);

    boolean hasRating(String item);

    String getID();

    int numRatings();

    ArrayList<String> getItemsRated();

}
