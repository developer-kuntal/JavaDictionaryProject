// import org.apache.log4j.Logger;  

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.util.ArrayList;


public class App {

    public static void readLines(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null) {
            // System.out.println(line);
            WebScrapping web = new WebScrapping("https://www.google.com/search?q="+line+"meaning+in+bengali");
            try {
                ArrayList<String> bnList = new ArrayList<>();
                bnList = web.WebScrappingData();
                // System.out.println("List of Word: "+bnList);
                MongoOperation mOperation = new MongoOperation(line, bnList);
                mOperation.saveDocument();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("Done!!!");
        br.close();
        fr.close();
    }

    public static void main( String[] args ) {
        
        File f = new File("D:\\JavaProject\\DictionaryProject\\data\\dictionary.txt");
        try {
            readLines(f);
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
