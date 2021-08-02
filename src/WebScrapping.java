import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class WebScrapping {

    public String url;

    public WebScrapping(String url) { 
        this.url = url;
    }

    public ArrayList<String> WebScrappingData() throws IOException {
        try {
            // fetch the document over HTTP
            Document doc = Jsoup.connect(url).get();
            
            // get the page title
            // String title = doc.title();
            // System.out.println("title: " + title);
            ArrayList<String> listofWord = new ArrayList<>();
            // get all links in page
            Elements bnList = doc.select(".hrcAhc");
            for (Element list : bnList) {
                // get the value from the href attribute
                listofWord.add(list.text());
                // System.out.println("List of Word: " + list.text());
            }
            return listofWord;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
