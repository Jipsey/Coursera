package BuildRecommendationSystem;

import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {

    private String myID;
    private HashMap<String, Rating> myRatings; // Key of the HashMap is a Movie ID

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<>();
    }


    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item, rating));
    }

    public boolean hasRating(String item) {

        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {

        for(String id :myRatings.keySet()){
            if (myRatings.get(id).getItem().equals(item)) {
                return myRatings.get(id).getValue();
            }

//        for (int k = 0; k < myRatings.size(); k++) {
//            if (myRatings.get(k).getItem().equals(item)) {
//                return myRatings.get(k).getValue();
//            }
        }

        return -1;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {

        return new ArrayList<>(myRatings.keySet());
    }


    @Override
    public boolean equals(Object obj) {
        EfficientRater efficientRater = (EfficientRater) obj;
        if (efficientRater == this)
            return true;

        if (this.getID().equals(efficientRater.getID()))
            return true;

        return false;
    }
}
