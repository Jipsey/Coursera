package BuildRecommendationSystem;

public class MinuteFilter implements Filter {

    private int maxMinutes;
    private int minMinutes;

    public MinuteFilter(int minMinutes, int maxMinutes) {
        this.minMinutes = minMinutes;
        this.maxMinutes = maxMinutes;

    }

    @Override
    public boolean satisfies(String id) {

        int filmDuration = MovieDatabase.getMinutes(id);

        return  filmDuration >= minMinutes && filmDuration <= maxMinutes;
    }
}

