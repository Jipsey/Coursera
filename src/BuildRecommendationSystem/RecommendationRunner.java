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

public class RecommendationRunner implements Recommender {

    private String ratingsfile;
    private String moviefile;
    private ArrayList<Rating> arrayList;
    private FourthRating fourthRating;

    public RecommendationRunner() {
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
                fourthRating.getSimilarRatings(webRaterID, 15, 3);
        HashMap<String, ArrayList<String>> movieGenreHashMap;
        Document doc;
        movieGenreHashMap = buildMapByGenre(webRaterID, similarRatings);

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

        Element oops = doc.createElement("oops");

        Element html = doc.createElement("html");// create html tag
        Element link = doc.createElement("link");

        link.setAttribute("href", "https://fonts.googleapis.com/css?family=Pridi");
        link.setAttribute("rel", "stylesheet");
        html.appendChild(link);

        link = doc.createElement("link");
        link.setAttribute("href", "https://fonts.googleapis.com/css?family=Zilla+Slab");
        link.setAttribute("rel", "stylesheet");
        html.appendChild(link);
        Element styleElement = getStyleElements(doc);
        html.appendChild(styleElement);

        if (movieGenreHashMap.size() != 0) {
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
                    int movieMinutes = MovieDatabase.getMinutes(filmID);
                    int movieYear = MovieDatabase.getYear(filmID);
                    String movieCountry = MovieDatabase.getCountry(filmID);
                    String posterLink = MovieDatabase.getPoster(filmID);

                    if (posterLink.equals("N/A")) {
                        posterLink = "http://icons.iconarchive.com/icons/danleech/simple/256/imdb-icon.png";
                    }
                    //  posterLink = "https://trashbox.ru/apk_icons/topic_11831_192.png"; }
                    Element img = doc.createElement("img");
                    Element figure = doc.createElement("figure");
                    Element avg = doc.createElement("avg");
                    Element p = doc.createElement("p");
                    Element figcaption = doc.createElement("figcaption");

                    figure.setAttribute("class", "movie");

                    img.setAttribute("src", posterLink);
                    img.setAttribute("width", "30%");

                    p.appendChild(img);
                    figure.appendChild(p);

                    avg.setTextContent(String.valueOf(averageRatingValue));
                    figcaption.setTextContent(String.valueOf(movieTitle));
                    figure.appendChild(avg);
                    figure.appendChild(figcaption);
                    figcaption = doc.createElement("figcaption");
                    figcaption.setTextContent(movieMinutes + " min, " + movieCountry + ", " + movieYear);

                    figure.appendChild(figcaption);

                    ul.appendChild(figure);

                }

                div.appendChild(h3);
                div.appendChild(ul);
                html.appendChild(div);
            }
        } else {
            String text1 = "Sorry, but your rates of movies was too low to recommend you another movies, or you don`t";
            String text2 ="rate movies. Please rate some movies by better rating, or come back later with better mood";
            String oopsImgURL = "http://cdn.onlinewebfonts.com/svg/download_453364.png";
            Element div = doc.createElement("div");
            Element figure = doc.createElement("figure");
            Element img = doc.createElement("img");
            Element figcaption1 = doc.createElement("figcaption");
            Element figcaption2 =doc.createElement("figcaption");
            figcaption1.setTextContent(text1);
            figcaption2.setTextContent(text2);
            img.setAttribute("src", oopsImgURL);
            img.setAttribute("width", "30%");
            figure.appendChild(img);
            figure.appendChild(figcaption1);
            figure.appendChild(figcaption2);
            div.appendChild(figure);
            html.appendChild(div);

        }


        doc.appendChild(html);
        return doc;
    }

    private Element getStyleElements(Document document) {

        String h3 = "h3\n" +
                "{\n" +
                "  margin:  20px 50px ;\n" +
                "  text-shadow: 2px 2px 5px ;\n" +
                "  font-family:\"Pridi\";\n" +
                "  font-size:190%;\n" +
                "}\n";

        String div = "div{\n" +
                "   margin:  0px 0px 0; /* Отступы */\n" +
                "   display:block;\n" +
                "   float: left;\n" +
                "   border-style: solid;\n" +
                "   background-color: ;\n" +
                "   border-radius: 20px;  \n" +
                "}\n";

        String avg = "avg{\n" +
                "     background-color: orange;    \n" +
                "     display:block;\n" +
                "     position:absolute;\n" +
                "     font-family:\"Zilla Slab\";\n" +
                "     border-style: solid;\n" +
                "     border-radius: 5px;\n" +
                "     border-width: 1px;\n" +
                "     left:31%;\n" +
                "     bottom:80.5%;\n" +
                "     width:9%;\n" +
                "    }\n";

        String figure = "\n" +
                "figure{\n" +
                "    margin:  0 0 0; /* Отступы */\n" +
                "    display: inline-block; /* Блочный элемент */\n" +
                "    position:relative;\n" +
                "    font-family:\"Pridi\";\n" +
                "    font-size: 85%;\n" +
                "    float: left; /* Блоки выстраиваются по горизонтали */\n" +
                "    text-align: center; /* Выравнивание по центру */ \n" +
                "}\n";


        Element style = document.createElement("style");
        style.setTextContent(h3 + "\n" + div + "\n" + avg + "\n" + figure + "\n");

        return style;
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
