package BuildRecommendationSystem;

import java.util.ArrayList;

public class MovieRunnerSimilarRating {

    private String ratingsfile;
    private String moviefile;

    private FourthRating fourthRatings;

    private ArrayList<Rating> arrayList;


    public MovieRunnerSimilarRating() {
        ratingsfile = "ratings.csv";
        moviefile = "ratedmoviesfull.csv";
        fourthRatings = new FourthRating(ratingsfile);
        MovieDatabase.initialize(moviefile);

    }

    public void printAverageRatings() {

        System.out.printf("Number of raters %s\n", RaterDatabase.getRaters().size());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = fourthRatings.getAverageRatings(35);

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            double avg = rating.getValue();
            String title = MovieDatabase.getTitle((rating.getItem()));
            System.out.printf("%s %s \n", avg, title);
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {

        GenreFilter genreFilter = new GenreFilter("Drama");
        YearAfterFilter yearAfterFilter = new YearAfterFilter(1990);
        ArrayList<Rating> listFilteredByGenreFilter =
                fourthRatings.getAverageRatingsByFilter(8, genreFilter);
        ArrayList<Rating> listFilteredByYearAfterFilter =
                fourthRatings.getAverageRatingsByFilter(8, yearAfterFilter);

        ArrayList<Rating> filteredList = new ArrayList<>();

        for (Rating ratingFromFirstList : listFilteredByYearAfterFilter) {

            for (Rating ratingFromSecondList : listFilteredByGenreFilter) {
                if (ratingFromSecondList.getItem().
                        equals(ratingFromFirstList.getItem()))
                    filteredList.add(ratingFromFirstList);
            }
        }

        System.out.printf("Number of raters %s\n", RaterDatabase.getRaters().size());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());


        System.out.printf("Found %s movies \n", filteredList.size());

        for (Rating rating : filteredList) {
            String id = rating.getItem();
            double rat = rating.getValue();
            int year = MovieDatabase.getYear(id);
            String title = MovieDatabase.getTitle(id);

            System.out.printf("%s %s %s \n", rat, year, title);
        }
    }

    public void printSimilarRatings() {

        String raterID = "65";
        int minimalRaters = 5;
        int numSimilarRaters = 20;

        ArrayList<Rating> similarRaters =
                fourthRatings.getSimilarRatings(raterID, numSimilarRaters, minimalRaters);

        for (int i = 0; i < similarRaters.size(); i++) {

            String movieTitle = MovieDatabase
                    .getTitle(similarRaters.get(i).getItem());

            System.out.println(movieTitle);

            if (movieTitle.equals("The Fault in Our Stars"))
                System.out.println("============YES! It`s have!============");
        }
    }
}
