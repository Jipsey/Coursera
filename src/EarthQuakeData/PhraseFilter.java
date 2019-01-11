package EarthQuakeData;

public class PhraseFilter implements Filter {

   private String whereToSearch;
   private String phrase;

    public PhraseFilter(String whereToSearch, String phrase) {
        this.whereToSearch = whereToSearch;
        this.phrase = phrase;
    }

    @Override
    public boolean satisfies(QuakeEntry qe) {

        String title = qe.getInfo();
        switch (whereToSearch) {
            case "start":
                return title.startsWith(phrase);
            case "end":
                return title.endsWith(phrase);
            default:
                return title.contains(phrase);
        }
    }

    @Override
    public String getName(){
        return this.getClass().getName();
    }

}
