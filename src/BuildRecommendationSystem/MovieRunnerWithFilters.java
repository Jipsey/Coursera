package BuildRecommendationSystem;

import java.util.ArrayList;

public class MovieRunnerWithFilters {

    private String ratingsfile;
    private String moviefile;

    private ThirdRatings thirdRatings;

    private ArrayList<Rating> arrayList;


    public MovieRunnerWithFilters() {
        ratingsfile = "ratings_short.csv";
        moviefile = "ratedmovies_short.csv";
        thirdRatings = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);

    }


    public void printAverageRatings() {

        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatings(1);

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            double avg = rating.getValue();
            String title = MovieDatabase.getTitle((rating.getItem()));
            System.out.printf("%s %s \n", avg, title);
        }
    }


    public void printAverageRatingsByYear() {

        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatingsByFilter(1, new YearAfterFilter(2000));

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            String id = rating.getItem();
            double avg = rating.getValue();
            int year = MovieDatabase.getYear(id);
            String title = MovieDatabase.getTitle(id);
            System.out.printf("%s %s %s \n", avg, year, title);
        }
    }

    public void printAverageRatingsByGenre() {

        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatingsByFilter(1, new GenreFilter("Crime"));

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            String id = rating.getItem();
            double avg = rating.getValue();
            String genre = MovieDatabase.getGenres(id);
            String title = MovieDatabase.getTitle(id);
            System.out.printf("%s %s\n  %s \n", avg, title, genre);
        }

    }

    public void printAverageRatingsByMinutes() {


        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatingsByFilter(1, new MinuteFilter(110, 170));

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            String id = rating.getItem();
            double avg = rating.getValue();
            int time = MovieDatabase.getMinutes(id);
            String title = MovieDatabase.getTitle(id);
            System.out.printf("%s Time: %s %s \n", avg, time, title);
        }
    }

    public void printAverageRatingsByDirectors() {


        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatingsByFilter(1,
                new DirectorsFilter("Charles Chaplin,Michael Mann,Spike Jonze"));

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            String id = rating.getItem();
            double avg = rating.getValue();
            String director = MovieDatabase.getDirector(id);
            String title = MovieDatabase.getTitle(id);
            System.out.printf("%s %s\n %s \n", avg, title, director);
        }


    }
}
