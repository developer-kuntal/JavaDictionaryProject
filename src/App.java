// import org.apache.log4j.Logger;  
import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;

public class App {

    public static String uri = "mongodb://localhost:27017";
    public static final MongoClient mongoClient = MongoClients.create(uri);
    public static final MongoDatabase database = mongoClient.getDatabase("testdb");
    public static final MongoCollection<Document> collection = database.getCollection("words");
    public static final List<Document> documents = new ArrayList<Document>();

    public static void readLines(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String baseUrl = "https://www.google.com/search?q=";
        String completeUrl = null;

        while((line = br.readLine()) != null) {

            // System.out.println(line);
            try {
                String queryString = line+"meaning+in+bengali";
                String encodedQuery = URLEncoder.encode(queryString, "UTF-8");
                completeUrl = baseUrl + encodedQuery;
            } catch (UnsupportedEncodingException e) {
                //TODO: handle exception
                System.out.println("Issue while decoding" +e.getMessage());
            }

            WebScrapping web = new WebScrapping(completeUrl);

            try {
                ArrayList<String> bnList = new ArrayList<>();
                bnList = web.WebScrappingData();
                // System.out.println("List of Word: "+bnList);
                Document doc = new Document();

                doc.put("word", line);
                doc.put("bengali_meaning", bnList);

                documents.add(doc);
                // MongoOperation mOperation = new MongoOperation(line, bnList);
                // mOperation.saveDocument();
                // collection.insertOne(doc);

                System.out.println(doc.toJson());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        collection.insertMany(documents);
        System.out.println("Done!!!");
        br.close();
        fr.close();
    }

    public static void main( String[] args ) {
        
        File f = new File("D:\\JavaProject\\DictionaryProject\\data\\xab");
        try {
            readLines(f);
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
