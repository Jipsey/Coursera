package BuildRecommendationSystem;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class RecomendationRunner implements Recommender {

    private String ratingsfile;
    private String moviefile;
    private ArrayList<Rating> arrayList;
    private FourthRating fourthRating;

    public RecomendationRunner() {
        ratingsfile = "ratings.csv";
        moviefile = "ratedmoviesfull.csv";
        fourthRating = new FourthRating(ratingsfile);
        MovieDatabase.initialize(moviefile);
    }


    @Override
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> list = new ArrayList<>();
        int numberOfMovies = 25;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numberOfMovies; ) {

            for (int j = 0; j < 7; j++) {
                sb.append(random.nextInt(10));
            }

            String id = sb.toString();

            if (MovieDatabase.containsID(id)) {
                list.add(id);
                i++;
            }
            sb.delete(0, sb.length());
        }
        return list;
    }

    @Override
    public void printRecommendationsFor(String webRaterID) {

        ArrayList<Rating> similarRatings =
                fourthRating.getSimilarRatings(webRaterID, 15, 5);
        HashMap<String, ArrayList<String>> movieGenreHashMap;

        Document doc;

        movieGenreHashMap = buildMapByGenre(webRaterID, similarRatings);

        ArrayList<Rating> avg = fourthRating.getAverageRatings(5);

        for (Map.Entry entry : movieGenreHashMap.entrySet()) {

            //String genre = entry.getKey().toString();

            System.out.println(entry.getKey());
            ArrayList<String> list = (ArrayList<String>) entry.getValue();


            for (String filmID : list) {

                double averageRatingValue = 0;


                for (Rating rat : avg) {
                    if (filmID.equals(rat.getItem())) {

                        averageRatingValue = new BigDecimal(rat.getValue())
                                .setScale(2, RoundingMode.HALF_UP).doubleValue();
                        break;
                    }
                }
                System.out.printf("\t%s -- %s\n", MovieDatabase.getTitle(filmID), averageRatingValue);

            }
            System.out.println("---------------------------------");
        }


        try {

            doc = buildXmlDocument(movieGenreHashMap);
            printResultToConsole(doc);

        } catch (Exception a) {
            System.out.println("I caught Exception! " + a);
        }
    }

    private HashMap<String, ArrayList<String>> buildMapByGenre(String webRaterID, ArrayList<Rating> similarRatings) {

        HashMap<String, ArrayList<String>> movieGenreHashMap = new HashMap<>();

        Iterator iterator = similarRatings.iterator();
        while (iterator.hasNext()) {

            Rating rating = (Rating) iterator.next();

            String filmID = rating.getItem();
            if (RaterDatabase.getRater(webRaterID).hasRating(filmID))
                iterator.remove();
            String genre = getMainGenre(MovieDatabase.getGenres(filmID));
            if (!movieGenreHashMap.containsKey(genre))
                movieGenreHashMap.put(genre, new ArrayList<>());
            if (movieGenreHashMap.get(genre).size() < 5)
                movieGenreHashMap.get(genre).add(filmID);
        }

        return movieGenreHashMap;
    }

    private Document buildXmlDocument(HashMap<String, ArrayList<String>> movieGenreHashMap) throws Exception {

        Document doc = getXmlBuilder();
        ArrayList<Rating> avgRatingList = fourthRating.getAverageRatings(5);

        Element html = doc.createElement("html");// create html tag
        Element link = doc.createElement("link");

        link.setAttribute("href", "www.leningrad.spb.ru");

        html.appendChild(link);


        for (Map.Entry entry : movieGenreHashMap.entrySet()) {

            String genre = entry.getKey().toString();
            Element div = doc.createElement("div");
            div.setAttribute("class", genre);
            Element h3 = doc.createElement("h3");
            h3.setTextContent(genre);
            Element ul = doc.createElement("ul");


            ArrayList<String> list = (ArrayList<String>) entry.getValue();

            for (String filmID : list) {

                double averageRatingValue = getAverageFilmRating(filmID, avgRatingList);

                String movieTitle = MovieDatabase.getTitle(filmID);
                int filmMinutes = MovieDatabase.getMinutes(filmID);
                int movieYear = MovieDatabase.getYear(filmID);
                String movieCountry = MovieDatabase.getCountry(filmID);
                String posterLink = MovieDatabase.getPoster(filmID);
                //Element em = doc.createElement("em");
                Element li = doc.createElement("li");
                Element img = doc.createElement("img");
                Element movie = doc.createElement("figure");
                Element title = doc.createElement("filmtitle");
                Element minutes = doc.createElement("minutes");
                Element year = doc.createElement("year");
                Element country = doc.createElement("country");
                Element p = doc.createElement("p");
                Element figcaption = doc.createElement("figcaption");

                movie.setAttribute("class", "movie");

                title.setTextContent(movieTitle);
                year.setTextContent(String.valueOf(movieYear));
                minutes.setTextContent(String.valueOf(filmMinutes) + " min ");
                country.setTextContent(movieCountry);
                img.setAttribute("src", posterLink);
                img.setAttribute("width", "7%");

                p.appendChild(img);
                movie.appendChild(p);

                figcaption.appendChild(title);
                figcaption.appendChild(minutes);
                figcaption.appendChild(year);
                figcaption.appendChild(country);

                movie.appendChild(figcaption);
                Element avg = doc.createElement("avg");

                //img.setTextContent(title);
                li.appendChild(movie);


//
//                if (averageRatingValue != -1) {
//                    avg.setTextContent("avgRating: " + averageRatingValue);
//                    li.appendChild(avg);
//                }

               // em.appendChild(li);
                ul.appendChild(li);
            }

            div.appendChild(h3);
            div.appendChild(ul);
            html.appendChild(div);
        }

        doc.appendChild(html);
        return doc;
    }

    private Document getXmlBuilder() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.newDocument();
    }


    private double getAverageFilmRating(String filmID, ArrayList<Rating> avgRatingList) {

        for (Rating rat : avgRatingList) {
            if (filmID.equals(rat.getItem()))
                return new BigDecimal(rat.getValue())
                        .setScale(1, RoundingMode.HALF_UP).doubleValue();
        }
        return -1;
    }

    private void printResultToConsole(Document doc) throws Exception {

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }

    private String getMainGenre(String genres) {
        String[] arr = genres.split(",");
        return arr[0];
    }
}
