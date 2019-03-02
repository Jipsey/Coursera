package BuildRecommendationSystem;

public class DirectorsFilter implements Filter {

    private String directors;

    public DirectorsFilter(String directors) {
        this.directors = directors;
    }

    @Override
    public boolean satisfies(String id) {

        String movieDirectors = MovieDatabase.getDirector(id);
        String[] arr = directors.split(",");

        boolean flag = false;

        for (String director : arr) {
            if (movieDirectors.contains(director))
                flag = true;
        }
        return flag;
    }
}
