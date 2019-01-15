package EarthQuakeData;

import java.util.Comparator;

public class TitleAndDepthComporator implements Comparator<QuakeEntry> {


    @Override
    public int compare(QuakeEntry q1, QuakeEntry q2) {

        int answer = q1.getInfo().compareTo(q2.getInfo());

        if (answer == 0) {

            return Double.compare(q1.getDepth(), q2.getDepth());
        }

        return Integer.compare(answer, 0);

    }

}
