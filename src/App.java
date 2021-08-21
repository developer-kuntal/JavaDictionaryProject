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
    public static final MongoDatabase database = mongoClient.getDatabase("dictionary_db"); // testdb
    public static final MongoCollection<Document> collection = database.getCollection("words");
    public static final List<Document> documents = new ArrayList<Document>();

    public static void readLines(File f) throws IOException {
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int lineCount = 1;
        String baseUrl = "https://www.google.com/search?q=";
        String completeUrl = null;

        while((line = br.readLine()) != null) {
            System.out.println("Word: "+line.toUpperCase()+" Line Count: "+lineCount++);
            // line = "acerbity";
            // System.out.println(line);
            try {
                // https://translate.google.co.in/?sl=en&tl=bn&text=ABLAUT&op=translate
                // completeUrl = "https://translate.google.co.in/?sl=en&tl=bn&text="+line.toLowerCase()+"&op=translate";
                String queryString = line.toLowerCase()+" meaning in bengali google translate";
                String encodedQuery = URLEncoder.encode(queryString, "UTF-8");
                completeUrl = baseUrl + encodedQuery;
            } catch (UnsupportedEncodingException e) {
                //TODO: handle exception
                System.out.println("Issue while decoding" +e.getMessage());
            }
            // completeUrl = "https://translate.google.co.in/?sl=en&tl=bn&text="+line.toLowerCase()+"&op=translate";

            WebScrapping web = new WebScrapping(completeUrl);

            try {
                ArrayList<String> bnList = new ArrayList<>();
                bnList = web.WebScrappingData();
                // System.out.println("List of Word: "+bnList);
                Document doc = new Document();

                doc.put("word", line.toUpperCase());
                doc.put("bengali_meanings", bnList);

                documents.add(doc);
                // MongoOperation mOperation = new MongoOperation(line, bnList);
                // mOperation.saveDocument();
                // collection.insertOne(doc);
                
                // Set Perticular Document Field to the UpperCase in MongoDB
                // db.getCollection('words').find({}).forEach(function(doc) { 
                //     db.getCollection('words').update(
                //         { _id: doc._id},
                //         { $set : { 'word' : doc.word.toUpperCase() } },
                //         { multi: true }
                //     )
                //  })

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
        
        File f = new File("D:\\JavaProject\\DictionaryProject\\data\\xbq");
        try {
            long start = System.currentTimeMillis();
            readLines(f);
            long end = System.currentTimeMillis();
            float sec = (end - start) / 1000F; 
            System.out.println(sec/60F + " minutes");
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
    }
}
