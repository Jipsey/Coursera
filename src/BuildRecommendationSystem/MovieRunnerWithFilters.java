package BuildRecommendationSystem;

import java.util.ArrayList;

public class MovieRunnerWithFilters {

    private String ratingsfile;
    private String moviefile;

    private ThirdRatings thirdRatings;

    private ArrayList<Rating> arrayList;


    public MovieRunnerWithFilters() {
        ratingsfile = "ratings.csv";
        moviefile = "ratedmoviesfull.csv";
        thirdRatings = new ThirdRatings(ratingsfile);
        MovieDatabase.initialize(moviefile);

    }


    public void printAverageRatings() {

        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        arrayList = thirdRatings.getAverageRatings(35);

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

        arrayList = thirdRatings.getAverageRatingsByFilter(20, new YearAfterFilter(2000));

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

        arrayList = thirdRatings.getAverageRatingsByFilter(20, new GenreFilter("Comedy"));

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

        arrayList = thirdRatings.getAverageRatingsByFilter(5, new MinuteFilter(105, 135));

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

        arrayList = thirdRatings.getAverageRatingsByFilter(4,
                new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski," +
                        "Nora Ephron,Ridley Scott,Sydney Pollack"));

        System.out.printf("Found %s movies \n", arrayList.size());

        for (Rating rating : arrayList) {
            String id = rating.getItem();
            double avg = rating.getValue();
            String director = MovieDatabase.getDirector(id);
            String title = MovieDatabase.getTitle(id);
            System.out.printf("%s %s\n %s \n", avg, title, director);
        }


    }

    public void printAverageRatingsByYearAfterAndGenre() {

        GenreFilter genreFilter = new GenreFilter("Drama");
        YearAfterFilter yearAfterFilter = new YearAfterFilter(1990);
        ArrayList<Rating> listFilteredByGenreFilter =
                thirdRatings.getAverageRatingsByFilter(8, genreFilter);
        ArrayList<Rating> listFilteredByYearAfterFilter =
                thirdRatings.getAverageRatingsByFilter(8, yearAfterFilter);

        ArrayList<Rating> filteredList = new ArrayList<>();

        for (Rating ratingFromFirstList : listFilteredByYearAfterFilter) {

            for (Rating ratingFromSecondList : listFilteredByGenreFilter) {
                if (ratingFromSecondList.getItem().
                        equals(ratingFromFirstList.getItem()))
                    filteredList.add(ratingFromFirstList);
            }
        }

        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
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


    public void printAverageRatingsByDirectorsAndMinutes() {
        AllFilters allFilters = new AllFilters();
        allFilters.addFilter(new MinuteFilter(90, 180));
        allFilters.addFilter(new DirectorsFilter(
                "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));

        ArrayList<Rating> ratingArrayList = thirdRatings.getAverageRatings(3);
        ArrayList<Rating> filteredList = new ArrayList<>();
        for (Rating rating : ratingArrayList) {
            String id = rating.getItem();
            if (allFilters.satisfies(id))
                filteredList.add(rating);
        }


        System.out.printf("Number of raters %s\n", thirdRatings.getRaterSize());
        System.out.printf("Number of movies from database %s\n", MovieDatabase.size());

        System.out.printf("Found %s movies \n", filteredList.size());

        for (Rating rat : filteredList) {
            String id = rat.getItem();
            double rating = rat.getValue();
            int minutes = MovieDatabase.getMinutes(id);
            String title = MovieDatabase.getTitle(id);
            String directors = MovieDatabase.getDirector(id);
            System.out.printf("%s Time: %s %s\n\t%s\n ",
                    rating, minutes, title, directors);
        }

    }


}
