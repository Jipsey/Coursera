package EarthQuakeData;

public class DistanceFilter implements Filter {

    private Location location;
    private double maxDistantion;

    public DistanceFilter(Location location, double maxDistantion) {
        this.location = location;
        this.maxDistantion = maxDistantion;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {

        double dist = qe.getLocation().distanceTo(location);
        return (dist <= maxDistantion);
    }

    @Override
    public String getName(){
        return this.getClass().getName();
    }
}
