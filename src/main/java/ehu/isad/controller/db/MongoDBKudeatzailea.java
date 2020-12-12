package ehu.isad.controller.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.function.Consumer;

public class MongoDBKudeatzailea {

    public static void main(String[] args) {

        try (MongoClient client = new MongoClient("localhost", 27017)) {

            MongoDatabase database = client.getDatabase("whatweb");
            MongoCollection<Document> collection = database.getCollection("whatweb");

            // Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

            Document query = new Document();

            Document projection = new Document();

            projection.append("target", "$target");
            projection.append("plugins.Joomla", "$plugins.Joomla");
            projection.append("_id", 0);

            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println(document);
                }
            };

            collection.find(query).projection(projection).forEach(processBlock);

        } catch (MongoException e) {
            // handle MongoDB exception
        }
    }

}