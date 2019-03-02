package BuildRecommendationSystem;

import java.util.ArrayList;
import java.util.Iterator;

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

        for (int i = 0; i < 5; i++) {

            String movieTitle = MovieDatabase
                    .getTitle(similarRaters.get(i).getItem());

            System.out.println(movieTitle);

        }
    }


    private ArrayList<Rating> filterMovieList(String raterID, int minimalRaters, int numSimilarRaters, Filter filter) {

        ArrayList<Rating> similarRaters =
                fourthRatings.getSimilarRatings(raterID, numSimilarRaters, minimalRaters);

        Iterator iterator = similarRaters.iterator();
        while (iterator.hasNext()) {

            Rating rating = (Rating) iterator.next();
            String movieID = rating.getItem();
            if (!filter.satisfies(movieID))
                iterator.remove();
        }

        return similarRaters;
    }


    public void printSimilarRatingsByGenre() {


        String raterID = "65";
        int minimalRaters = 5;
        int numSimilarRaters = 20;
        GenreFilter genreFilter = new GenreFilter("Action");

        ArrayList<Rating> similarRaters = filterMovieList(raterID, minimalRaters, numSimilarRaters, genreFilter);


        for (int i = 0; i < 5; i++) {

            String movieTitle = MovieDatabase
                    .getTitle(similarRaters.get(i).getItem());

            System.out.println(movieTitle);

        }
    }

    public void printSimilarRatingsByDirector() {

        String raterID = "1034";
        int minimalRaters = 3;
        int numSimilarRaters = 10;
        DirectorsFilter directorFilter =
                new DirectorsFilter("Clint Eastwood,Sydney Pollack,David Cronenberg,Oliver Stone");

        ArrayList<Rating> similarRaters = filterMovieList(raterID, minimalRaters, numSimilarRaters, directorFilter);

        for (int i = 0; i < 1; i++) {

            String movieTitle = MovieDatabase
                    .getTitle(similarRaters.get(i).getItem());

            System.out.println(movieTitle);

        }
    }

    public void printSimilarRatingsByGenreAndMinutes() {
        String raterID = "65";
        int minimalRaters = 5;
        int numSimilarRaters = 10;
        GenreFilter genreFilter = new GenreFilter("Adventure");
        MinuteFilter minuteFilter = new MinuteFilter(100, 200);

        ArrayList<Rating> similarRatersByGenre = filterMovieList(raterID, minimalRaters, numSimilarRaters, genreFilter);
        ArrayList<Rating> similarRatersByMinutes = filterMovieList(raterID, minimalRaters, numSimilarRaters, minuteFilter);

        for (Rating rating:similarRatersByGenre  ) {
            System.out.println( MovieDatabase.getTitle( rating.getItem()));
        }

        System.out.println("================");
        for (Rating rating:similarRatersByMinutes  ) {
            System.out.println( MovieDatabase.getTitle( rating.getItem()));
        }

        Iterator iterator = similarRatersByGenre.iterator();

        while (iterator.hasNext()) {

            Rating rating = (Rating) iterator.next();
            if (!similarRatersByMinutes.contains(rating))
                iterator.remove();
        }

        for (int i = 0; i < 1; i++) {

            String movieTitle = MovieDatabase
                    .getTitle(similarRatersByGenre.get(i).getItem());

            System.out.println(movieTitle);

        }
    }


      public void printSimilarRatingsByYearAfterAndMinutes(){

          String raterID = "65";
          int minimalRaters = 5;
          int numSimilarRaters = 10;
          YearAfterFilter yearAfterFilter = new YearAfterFilter(2000);
          MinuteFilter minuteFilter = new MinuteFilter(80, 100);

          ArrayList<Rating> similarRatersByYear = filterMovieList(raterID, minimalRaters, numSimilarRaters, yearAfterFilter);
          ArrayList<Rating> similarRatersByMinutes = filterMovieList(raterID, minimalRaters, numSimilarRaters, minuteFilter);

          Iterator iterator = similarRatersByYear.iterator();

          while (iterator.hasNext()) {
              boolean flag = false;
              Rating rating = (Rating) iterator.next();
              String movieID = rating.getItem();
              for (Rating r:similarRatersByMinutes) {
                  if (r.getItem().equals(movieID))
                      flag = true;
              }
              if(!flag)
                  iterator.remove();
          }
          for (int i = 0; i < 1; i++) {

              String movieTitle = MovieDatabase
                      .getTitle(similarRatersByYear.get(i).getItem());

              System.out.println(movieTitle);

          }


      }
}
