

/* The Method which can devide an necessary URL adress from URLResource
 */

package StringsFirstAssignments;


import edu.duke.URLResource;

public class Part4 {


    public void findURL(String url) {
        URLResource res = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");

        String quotation = "\"";
        for (String line : res.lines()) {
            String temp = line.toLowerCase();

            if (temp.contains(url.toLowerCase())) {
                while (line.contains(quotation)) {
                    line = line.substring(line.indexOf(quotation) + 1, line.lastIndexOf(quotation));
                }
                System.out.println(line);
            }

        }
    }


    public void testFindURL() {

        findURL("youtube.com");
    }
}
