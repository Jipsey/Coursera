package BuildRecommendationSystem;

import java.util.ArrayList;

public interface Rater {

    void addRating(String item, double rating);

    double getRating(String id);

    boolean hasRating(String item);

    String getID();

    int numRatings();

    ArrayList<String> getItemsRated();

}
