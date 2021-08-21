// import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.mongodb.BasicDBObject;
// import com.mongodb.DBCursor;
// import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
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

    public void findDocument() {
        FindIterable<Document> results = collection.find(new BasicDBObject("name", "The name I want to find"));
        // DBCursor results = collection.find(new BasicDBObject("name", "SomeName"),
        //                             new BasicDBObject("address", 0));
        // DBCursor results = collection.find(new BasicDBObject("numberOfOrders", new BasicDBObject("$gt", 10)));
        // DBCursor results = collection.find(new BasicDBObject("name", new BasicDBObject("$gt", 10)));
        // DBObject findLondoners = new BasicDBObject("address.city", "London");
        // collection.find(findLondoners));
        // DBObject jo =                                       // get the document representing jo
        //             (DBObject) jo.put("name", "Jo In Disguise");                   // replace the old name with the new one
        //             collection.updateOne(new BasicDBObject("_id", "jo"),   // find jo by ID
        //                 jo);                              // set the document in the DB to the new document for J
        // collection.updateOne(new BasicDBObject("_id", "jo"),
        //         new BasicDBObject("$set", new BasicDBObject("phone", "5559874321")));
        // collection.update(new BasicDBObject(),
        //         new BasicDBObject("$set", new BasicDBObject("country", "UK")), false, true);
        // collection.update(query, personDocument, true, false);
        // collection.remove(new BasicDBObject("_id", "jo"));
        // collection.remove(new BasicDBObject("address.city", "London"));
        // var query = new BasicDBObject("price",
        //             new BasicDBObject("$gt", 30000));
        //     collection.find(query).forEach((Consumer<Document>) doc ->
        //             System.out.println(doc.toJson()));
        // The excludeId() is a synonym for exclude("_id").
        // FindIterable it = collection.find().projection(excludeId());
        // var docs = new ArrayList<Document>();
        // it.into(docs);
        // for (Document doc : docs) {
        //     System.out.println(doc);
        // }
        // MongoCollection<Document> collection = database.getCollection("cars");
        //     FindIterable<Document> fit = collection.find().skip(2).limit(5);
        // collection.deleteOne(eq("name", "Skoda"));
        //     collection.updateOne(new Document("name", "Audi"),
        //             new Document("$set", new Document("price", 52000)));
        ((Document) results).size();
        for (Document result : results) {
            // do something with each result
            System.out.println(result);
        }
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