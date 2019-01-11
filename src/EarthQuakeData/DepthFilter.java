package EarthQuakeData;

public class DepthFilter implements Filter{

    private double minDepth;
    private double maxDepth;

    public DepthFilter(double minDepth, double maxDepth) {
        this.minDepth = minDepth;
        this.maxDepth = maxDepth;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {

        double depth = qe.getDepth();
        return(minDepth <= depth && maxDepth >= depth);

    }

    @Override
    public String getName(){
        return this.getClass().getName();
    }
}
