// import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class MongoOperation {

     /* Get actual class name to be printed on */
    // final Logger log = Logger.getLogger(App.class.getName());
    // Replace the uri string with your MongoDB deployment's connection string
    public String uri = "mongodb://localhost:27017";
    public final MongoClient mongoClient = MongoClients.create(uri);
    public final MongoDatabase database = mongoClient.getDatabase("dictionary_database");
    public final MongoCollection<Document> collection = database.getCollection("words");

    public String word;
    public ArrayList<String> wordMeanings;

    public MongoOperation(String word, ArrayList<String> bnList ) {
        this.word = word;
        this.wordMeanings = bnList;
    }

    public void saveDocument() {
    
        try (mongoClient) {
            // Document doc = collection.find(eq("word", "ABERRANT")).first();
            Document doc = new Document();

            ArrayList<String> List = new ArrayList<>();
            List = wordMeanings;

            doc.put("word", word);
            doc.put("bengali_meaning", List);

            collection.insertOne(doc);

            System.out.println(doc.toJson());
            // mongoClient.close();
        } catch(Exception e) {
            e.printStackTrace();
            
            // List<Document> documents = new ArrayList<Document>();
            // for (int i = 0; i < 100; i++) {
            //     documents.add(new Document("i", i));
            // }
            // collection.insertMany(documents);
        }
    }
}