package EarthQuakeData;

public class MagnitudeFilter implements Filter {

    private double maxMagn;
    private double minMagn;

    public MagnitudeFilter(double minMagn, double maxMagn) {
        this.maxMagn = maxMagn;
        this.minMagn = minMagn;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {

        double magn = qe.getMagnitude();
        return (magn >= minMagn && magn <= maxMagn);

    }

    @Override
    public String getName(){
        return this.getClass().getName();
    }
}
