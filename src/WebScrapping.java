import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

// import java.io.File;
// import java.io.BufferedWriter;
// import java.io.FileWriter;

public class WebScrapping  extends Thread {

    public String url;
    public ArrayList<String> listofWord = new ArrayList<>();
    public Document doc = null;
    public Connection.Response html;
    public int i = 1;
    public InternetConnectionAvailibily pc;
    // public boolean success = false;
    public boolean hasInternetConnection;
    public String setCssClass;
    public boolean wordMeaningsFound = false;

    public WebScrapping(String url) {
        this.url = url;
        this.pc = new InternetConnectionAvailibily();
    }

    public ArrayList<String> WebScrappingData() throws IOException {
        while( i <= 3 ) {
            
            if( i > 1 ) {
                
                if( ( this.listofWord.remove("TimeOut") || this.listofWord.remove("Error") ) 
                    && hasInternetConnection ) {
                    try {
                        Thread.sleep(1 * 1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else if( !hasInternetConnection ) {
                    System.out.println("Please connect your PC to the Internet...");
                } else {
                    this.listofWord.remove("Not Found");
                }

            }

            if(pc.hasInternetConnection()) {
                // System.out.println("PC has internet connection? ["+pc.hasInternetConnection()+"]");
                this.hasInternetConnection = pc.hasInternetConnection();
                try {
                
                
                    // System.out.println("Entering...."+i);
                    // fetch the document over HTTP
                    // doc = Jsoup.connect(url).
                        // header("Accept-Encoding", "gzip, deflate").
                        // header("Content-Type", "text/html, application/xhtml+xml, application/xml;q=0.9, image/webp, */*;q=0.8").
                        // userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36").
                        // timeout(80000).followRedirects(true).get();
                    this.html = Jsoup.connect(url)
                                    .maxBodySize(0)
                                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36")
                                    // .timeout(60 * 1000)
                                    .execute();
    
                    this.doc = this.html.parse();
    
                    // final File f = new File("filename.html");
                    // BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    // bw.write(doc.html());
                    // bw.close();
    
                    // Elements test = doc.getElementsByClass("hrcAhc");
                    // System.out.println(test);
                    // System.out.println("Page: "+html.body());
                    System.out.println("Length: "+this.html.body().toString().length()/(1024)+"KB or "+this.html.body().toString().length()/(1024*1024)+" MB");
                    // Elements items = html.parse().getElementsByTag("span");
                    
                    // System.out.println("ITEM: "+items);
                    // doc = conn.get();
                    // if( doc.select(".hrcAhc").isEmpty() == true ) {
                    //     System.out.println("Not Found"+i);
                    //     // break;
                    // }
    
                    if( this.doc != null ) {
                        System.out.println("Entering...");
                        // if( this.doc.getElementsByClass(".KnIHac").isEmpty() == false ) {
                        //     this.setCssClass = ".KnIHac"; 
                        //     this.success = true;
                        //     break;
                        // }
                        if (!this.doc.select(".hrcAhc").isEmpty() || !this.doc.select(".Y2IQFc").isEmpty() || !this.doc.select(".hgKElc").isEmpty() ) {
                            if( this.doc.select(".hrcAhc").isEmpty() == false ) { 
                                this.setCssClass = ".hrcAhc"; 
                                // this.success = true;
                                this.wordMeaningsFound = true;
                                break; 
                            }
                            if( this.doc.select(".Y2IQFc").isEmpty() == false ) {
                                this.setCssClass = ".Y2IQFc"; 
                                // this.success = true;
                                this.wordMeaningsFound = true;
                                break;  
                            }
                            if( this.doc.select(".hgKElc").isEmpty() == false ) {
                                this.setCssClass = ".hgKElc"; 
                                // this.success = true;
                                this.wordMeaningsFound = true;
                                break; 
                            } 
                        } else {
                            this.listofWord.add("Not Found");
                            this.wordMeaningsFound = false;
                            break;
                        }
    
                    } else {
                        System.out.println("Not Found "+i);
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
                    System.out.println("Timeout: URL# "+this.url);
                    this.listofWord.add("TimeOut");
                    this.wordMeaningsFound = false;
                } catch (IOException e) {
                    System.out.println("IOException "+e.getMessage());
                    this.listofWord.add("Error");
                    this.wordMeaningsFound = false;
                }
    
                if( !this.wordMeaningsFound && listofWord.isEmpty() ) {
                    this.listofWord.add("Not Found");
                    System.out.println("Word Meaning Not Found in the Given Site<www.google.com> Page");
                    break;
                } else if(!this.wordMeaningsFound && listofWord.isEmpty() && i == 3) {
                    System.out.println("The URL Hit maximum(3) attempts");
                    this.listofWord.add("Max Attempts");
                    break;
                } else {
                    i++;
                } 
                
            } else {
                this.hasInternetConnection = pc.hasInternetConnection();
                // System.out.println("PC has internet connection? ["+pc.hasInternetConnection()+"]");
                try {
                    System.out.println("Retry After 15sec.");
                    Thread.sleep(15 * 1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
            
            
        }

        if(this.wordMeaningsFound) {
            // Selector code ...
            Elements bnList = this.doc.select(this.setCssClass);
            for (Element list : bnList) {
                // get the value from the href attribute
                this.listofWord.add(list.text());
                // System.out.println("List of Word: " + list.text());
            }
            return this.listofWord;
        } else {
            // this.listofWord.add("TimeOut/Not Found");
            return this.listofWord;
        }
        
    }

}
