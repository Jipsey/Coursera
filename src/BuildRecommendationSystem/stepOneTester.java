package BuildRecommendationSystem;

public class stepOneTester {


    public static void main(String[] args)  {
//        FirstRatings fr = new FirstRatings();
//        fr.testLoadRaters();
//        fr.findMaximumNumberOfRatingByRater();
//        fr.findAllRatedFilms();

//        MovieRunnerAverage mra = new MovieRunnerAverage();
//        mra.getAverageRatingOneMovie();
//          MovieRunnerWithFilters mrwf = new MovieRunnerWithFilters();
//          MovieRunnerSimilarRating mrsr = new MovieRunnerSimilarRating();
//          mrsr.printSimilarRatings();
           RecomendationRunner rr = new RecomendationRunner();
           rr.printRecommendationsFor("65");

    }

}
