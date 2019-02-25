package BuildRecommendationSystem;

import java.util.ArrayList;

public class MovieRunnerAverage {

    public void printAverageRatings() {
        SecondRatings secondRatings = new SecondRatings();
        System.out.printf("Number of movies %s\n", secondRatings.getMoviesSize());
        System.out.printf("Number of raters %s\n", secondRatings.getRaterSize());


        ArrayList<Rating> arrayList = secondRatings.getAverageRatings(20);

        for (Rating rating : arrayList) {
            double avg = rating.getValue();
            String title = secondRatings.getTitle(rating.getItem());
            System.out.printf("%s %s \n", avg, title);
        }
        System.out.println(arrayList.size());
    }

    public void getAverageRatingOneMovie() {

        SecondRatings secondRatings = new SecondRatings();
        String titleForSearch = "Identity Thief";
        ArrayList<Rating> ratingArrayList = secondRatings.getAverageRatings(12);

        for (Rating rating : ratingArrayList) {
            if (titleForSearch.equals(secondRatings.getTitle(rating.getItem())))
                System.out.println(rating.getValue());
        }
    }
}
