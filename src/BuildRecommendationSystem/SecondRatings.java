package BuildRecommendationSystem;

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<PlainRater> myPlainRaters;

    public SecondRatings() {
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRating firstRating = new FirstRating();
        myMovies = firstRating.loadMovies(moviefile);
        myPlainRaters = firstRating.loadRaters(ratingsfile);
    }


    public int getMoviesSize() {
        return myMovies.size();
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

        for (Movie movie : myMovies) {
            String id = movie.getID();
            double avgRating = getAverageByID(id, minimalRaters);
            if (avgRating != 0.0)
                ratingArrayList.add(new Rating(id, avgRating));
        }

        Collections.sort(ratingArrayList);
        return ratingArrayList;
    }

    public String getTitle(String id) {

        for (Movie movie : myMovies) {
            if (movie.getID().equals(id))
                return movie.getTitle();
        }

        return "ID NOT FOUND!";
    }

   public String getID(String title){

        for (Movie movie:myMovies){
          if(movie.getTitle().equals(title))
           return movie.getID();
        }
        return "NO SUCH TITLE!";
    }
}
