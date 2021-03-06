package BuildRecommendationSystem;

import edu.duke.*;
import org.apache.commons.csv.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FirstRatings {

    private ArrayList<Movie> listOfComedy = new ArrayList();
    private ArrayList<Movie> listOfTimeFiltered = new ArrayList<>();
    private HashMap<String, ArrayList<Movie>> directors = new HashMap<>();

    public ArrayList<Movie> loadMovies(String fileName) {

        ArrayList<Movie> movies = new ArrayList<>();
        String path = makePath() + fileName;
        FileResource fr = new FileResource(path);
        CSVParser parser = fr.getCSVParser();
        Movie movie;

        for (CSVRecord record : parser) {

            movie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"),
                    record.get("director"), record.get("country"), record.get("poster"),
                    Integer.parseInt(record.get("minutes")));
            movies.add(movie);

            if (movie.getGenres().toLowerCase().contains("comedy".toLowerCase()))
                listOfComedy.add(movie);

            if (movie.getMinutes() > 150)
                listOfTimeFiltered.add(movie);
        }
        return movies;
    }

    public ArrayList<PlainRater> loadRaters(String filename) {

        ArrayList<PlainRater> arrayList = new ArrayList<>();
        String path = makePath() + filename;
        FileResource fr = new FileResource(path);
        CSVParser parser = fr.getCSVParser();
        PlainRater plainRater;

        for (CSVRecord record : parser) {

            plainRater = new PlainRater(record.get("rater_id"));
            if (!arrayList.contains(plainRater)) {
                plainRater.addRating(record.get("movie_id"),
                        Double.parseDouble(record.get("rating")));
                arrayList.add(plainRater);
            } else {
                int index = arrayList.indexOf(plainRater);
                plainRater = arrayList.get(index);
                plainRater.addRating(record.get("movie_id"),
                        Double.parseDouble(record.get("rating")));
            }
        }

        return arrayList;
    }

    public void testLoadRaters() {
        printRates(loadRaters("ratings.csv"));
    }

    private void printRates(ArrayList<PlainRater> arrayList) {

        System.out.println("Total numbers of raters: " + arrayList.size());

        for (PlainRater plainRater : arrayList) {
            String id = plainRater.getID();
            ArrayList<String> list = plainRater.getItemsRated();
            int i = plainRater.numRatings();
            }
        }

    public void findRater(String searchRater) {

        ArrayList<PlainRater> plainRaters = loadRaters("ratings.csv");

        for (PlainRater plainRater : plainRaters) {
            if (plainRater.getID().equals(searchRater)) {

                ArrayList<String> list = plainRater.getItemsRated();

                System.out.println("number of rated items by plainRater with Id "
                        + searchRater + " is " + list.size());
            }
        }
    }

    public void findMaximumNumberOfRatingByRater() {
        ArrayList<PlainRater> plainRaters = loadRaters("ratings.csv");
        ArrayList<PlainRater> listOfRatesrMaxNumerOfRates = new ArrayList<>();
        int maxNumberOfRates = 0;
        for (PlainRater plainRater : plainRaters) {
            int numRatings = plainRater.numRatings();
            if (numRatings > maxNumberOfRates) {
                maxNumberOfRates = numRatings;
            }
        }
        System.out.println("number of max ratings are " + maxNumberOfRates);
        for (PlainRater plainRater : plainRaters) {
            if (plainRater.numRatings() == maxNumberOfRates) {
                listOfRatesrMaxNumerOfRates.add(plainRater);
                System.out.println("id plainRater with max ratings is id_" + plainRater.getID());
            }
        }
        System.out.println("Raters with max qantity ratings " +
                listOfRatesrMaxNumerOfRates.size());
        System.out.println("************************************");
    }

    public void findRatesrByRatedFilm(String entry_movie) {

        ArrayList<PlainRater> plainRaters = loadRaters("ratings.csv");
        //ArrayList<PlainRater> listRatersWithRateEntryFilm = new ArrayList<>();
        for (PlainRater plainRater : plainRaters) {
            if (plainRater.hasRating(entry_movie)) {
                //      listRatersWithRateEntryFilm.add(plainRater);
               // System.out.println(plainRater.getID());
            }
        }
        System.out.printf("Overall film %s has %s rates",entry_movie, plainRaters.size());
    }

    public void findAllRatedFilms() {
        ArrayList<PlainRater> plainRaters = loadRaters("ratings.csv");
        ArrayList<String> listOfFilms = new ArrayList<>();

        for (PlainRater plainRater : plainRaters) {
            ArrayList<String> ratedFilms = plainRater.getItemsRated();
            for (String film_id : ratedFilms) {
               if(!listOfFilms.contains(film_id))
                   listOfFilms.add(film_id);
            }
        }
        System.out.println("Total rated films are " + listOfFilms.size());
    }

    public void parseDirectors() {

        ArrayList<Movie> movies = loadMovies("ratedmoviesfull.csv");
        int mostProdDir = 0;
        String mostProdDirName = "NoName";
        if (!movies.isEmpty())
            for (int i = 0; i < movies.size(); i++) {

                Movie movie = movies.get(i);
                String[] director = movie.getDirector().split(", ");

                ArrayList<Movie> movieArrayList;
                for (String nameOfDirector : director) {
                    if (!directors.containsKey(nameOfDirector)) {
                        movieArrayList = new ArrayList<>();
                        movieArrayList.add(movie);
                        directors.put(nameOfDirector, movieArrayList);
                    } else {
                        movieArrayList = directors.get(nameOfDirector);
                        movieArrayList.add(movie);
                        directors.put(nameOfDirector, movieArrayList);
                    }
                }
            }
        for (Map.Entry<String, ArrayList<Movie>> entry : directors.entrySet()) {
            System.out.println(entry.getKey() + " made the following films");
            System.out.println(entry.getValue());
            System.out.println("************************************************");
            int listSize = entry.getValue().size();
            if (mostProdDir < listSize) {
                mostProdDir = listSize;
                mostProdDirName = entry.getKey();
            }
        }
        System.out.printf("The most productive director is %s. Filmed %s films.",
                mostProdDirName, mostProdDir);
    }


    public void testLoadMovie() {
        ArrayList<Movie> movies =
                loadMovies("ratedmoviesfull.csv");
        System.out.println("number of movies: " + movies.size());
        System.out.println("Movies longer than 150 minutes:" + listOfTimeFiltered.size());
        System.out.println("Movies of comedy genre: " + listOfComedy.size());
        System.out.println("Total directors: "+
                directors.size());
        parseDirectors();
    }

    static String makePath() {
        final String ROOT_DIR = System.getProperty("user.dir");
        final String separ = "\\";
        StringBuilder sb = new StringBuilder();
        stepOneTester s = new stepOneTester();
        String pack = s.getClass().getPackage().getName();
        sb.append(ROOT_DIR)
                .append(separ)
                .append("src")
                .append(separ)
                .append(pack)
                .append(separ + "data")
                .append(separ);
        return sb.toString();
    }

}
