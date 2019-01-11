package EarthQuakeData;

import java.util.ArrayList;

public interface Filter {

    public boolean satisfies(QuakeEntry qe);

    public String getName();

}
