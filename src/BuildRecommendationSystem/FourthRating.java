package BuildRecommendationSystem;

import java.util.ArrayList;
import java.util.Collections;

public class FourthRating {


    public FourthRating(String ratingsfile) {
        RaterDatabase.initialize(ratingsfile);
    }

    private double getAverageByID(String id, int minimalRaters) {

        double averageRating = 0.0;

        int counter = 0;
        for (Rater rater : RaterDatabase.getRaters()) {
            if (rater.hasRating(id)) {
                counter++;
                averageRating += rater.getRating(id);
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
        Collections.sort(ratingArrayList);
        return ratingArrayList;
    }

    private double dotProduct(Rater me, Rater r) {

        double result = 0.0;
        for (String id : me.getItemsRated()) {
            if (r.hasRating(id))
                result += (me.getRating(id) - 5) * (r.getRating(id) - 5);
        }
        return result;
    }

    private ArrayList<Rating> getSimilarities(String id) {

//        the method returns a list of Rating objects,
//        the fields of the Rating object contains the Rater ID and the dotProd value
//        (value of coefficient similarities for Rater objects)


        ArrayList<Rating> ratingArrayList = new ArrayList<>();

        Rater me = RaterDatabase.getRater(id);

        for (Rater r : RaterDatabase.getRaters()) {
            if (!r.getID().equals(id)) {
                double dotProduct = dotProduct(me, r);
                if (dotProduct > 0)
                    ratingArrayList.add(new Rating(r.getID(), dotProduct));
            }
        }

        Collections.sort(ratingArrayList, Collections.reverseOrder());

        return ratingArrayList;
    }

    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters,
                                               int minimalRaters) {

        ArrayList<Rating> movieArrayList = new ArrayList<>();

        ArrayList<Rating> similarityRaters = getSimilarities(id);

        if(similarityRaters.size() > numSimilarRaters)
        similarityRaters = new ArrayList<>(similarityRaters.subList(0, numSimilarRaters));

        for (String movieID : MovieDatabase.filterBy(new TrueFilter())) {

            double rating = 0;
            int raterCounter = 0;
            for (Rating similarRater : similarityRaters) {

                String raterID = similarRater.getItem();//Rater ID
                double index = similarRater.getValue();//get index of similarity
                double movieRating = RaterDatabase.getRater(raterID).getRating(movieID);
                if (RaterDatabase.getRater(raterID)
                        .hasRating(movieID)) {

                    rating += index * movieRating;
                    raterCounter++;
                }
            }

//            if (raterCounter >= minimalRaters &&
//                    !RaterDatabase.getRater(id).hasRating(movieID))
            if (raterCounter >= minimalRaters) {

                double wghtAvgMovieRating = rating / raterCounter; // weighted average rating
                Rating movieRating = new Rating(movieID, wghtAvgMovieRating); // Rating for Movie
                movieArrayList.add(movieRating);
            }
        }

        Collections.sort(movieArrayList, Collections.reverseOrder());
        return movieArrayList;
    }
}
