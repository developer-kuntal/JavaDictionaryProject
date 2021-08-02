import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class WebScrapping {

    public String url;
    public ArrayList<String> listofWord = new ArrayList<>();
    public Document doc = null;
    public int i = 0;
    public boolean success = false;

    public Connection session = Jsoup.newSession()
                            .timeout(60 * 1000)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36");

    public WebScrapping(String url) {
        this.url = url;
    }

    public ArrayList<String> WebScrappingData() throws IOException {
        while( i < 3) {
            try {
                // System.out.println("Entering...."+i);
                // fetch the document over HTTP
                // doc = Jsoup.connect(url).
                    // header("Accept-Encoding", "gzip, deflate").
                    // header("Content-Type", "text/html, application/xhtml+xml, application/xml;q=0.9, image/webp, */*;q=0.8").
                    // userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36").
                    // timeout(80000).followRedirects(true).get();

                doc = session.newRequest()
                        .url(url)
                        .get();
                
                if( doc.select(".hrcAhc").isEmpty() == true ) {
                    System.out.println("Not Found"+i);
                }

                if( doc.select(".hrcAhc").isEmpty() == false ) {  
                    success = true;
                    break;
                }
                // get the page title
                // String title = doc.title();
                // System.out.println("title: " + title);
                
                // get all links in page
                // Elements bnList = doc.select(".hrcAhc");
                // for (Element list : bnList) {
                //     // get the value from the href attribute
                //     listofWord.add(list.text());
                //     // System.out.println("List of Word: " + list.text());
                // }
                // return listofWord;

            } catch(SocketTimeoutException e) {
                System.out.println("Timeout: URL"+url);
            }
            catch (IOException e) {
                System.out.println("IOException"+url);
                e.printStackTrace();
            }
            i++;
            
        }

        if(success) {
            // Selector code ...
            Elements bnList = doc.select(".hrcAhc");
            for (Element list : bnList) {
                // get the value from the href attribute
                listofWord.add(list.text());
                // System.out.println("List of Word: " + list.text());
            }
            return listofWord;
        } else {
            listofWord.add("TimeOut/Not Found");
            System.out.println("TimeOut");
            return listofWord;
        }
        
    }

}
