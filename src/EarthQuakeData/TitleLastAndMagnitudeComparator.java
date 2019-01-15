package EarthQuakeData;

import java.util.Comparator;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {

    @Override
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        String title1 = q1.getInfo();
        String title2 = q2.getInfo();

        String[] arr1 = title1.split(" ");
        String[] arr2 = title2.split(" ");

        String lastWordTitle1 = arr1[arr1.length - 1];
        String lastWordTitle2 = arr2[arr2.length - 1];

        int answer = lastWordTitle1.compareTo(lastWordTitle2);

        if(answer == 0){
            return Double.compare(q1.getMagnitude(),q2.getMagnitude());
        }

       return answer;
    }
}
