package BuildRecommendationSystem;

import java.util.ArrayList;
import java.util.Collections;

public class ThirdRatings {

    private ArrayList<PlainRater> myPlainRaters;

    public ThirdRatings() {
        this("ratings.csv");
    }

    public ThirdRatings(String ratingsfile) {
        FirstRatings firstRatings = new FirstRatings();
        myPlainRaters = firstRatings.loadRaters(ratingsfile);
    }


    public int getRaterSize() {
        return myPlainRaters.size();
    }

    private double getAverageByID(String id, int minimalRaters) {

        double averageRating = 0.0;

        int counter = 0;
        for (PlainRater plainRater : myPlainRaters) {
            if (plainRater.hasRating(id)) {
                counter++;
                averageRating += plainRater.getRating(id);
            }
        }
        if (counter != 0 && counter >= minimalRaters)
            return averageRating / counter;

        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> ratingArrayList = new ArrayList<>();

        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String id : movies) {
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating != 0.0)
                ratingArrayList.add(new Rating(id, avgRating));
        }

        Collections.sort(ratingArrayList);
        return ratingArrayList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {

        ArrayList<Rating> ratingArrayList = new ArrayList<>();

        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);

        for (String id : filteredMovies) {
            double avg = getAverageByID(id, minimalRaters);
            if (avg != 0.0) {
                ratingArrayList.add(new Rating(id, avg));
            }
        }
        return ratingArrayList;
    }
}
