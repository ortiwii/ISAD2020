package ehu.isad.controller.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MongoDBKudeatzailea {

    private static MongoDBKudeatzailea instantzia = new MongoDBKudeatzailea();

    public static MongoDBKudeatzailea getInstantzia() {

        return instantzia;
    }

    private MongoDBKudeatzailea() {

    }

    public List<String> getZerbitzariak() {
        List<String> emaitza = new ArrayList<>();
        try (MongoClient client = new MongoClient("192.168.0.27", 27017)) {

            MongoDatabase database = client.getDatabase("whatweb");
            MongoCollection<Document> collection = database.getCollection("whatweb");

            // Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

            Document query = new Document();

            Document projection = new Document();

            query.append("http_status", 200L);

            projection.append("target", "$target");
            projection.append("_id", 0);
            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    emaitza.add((String) document.get("target"));
                    System.out.println(document.get("target"));
                }
            };

            collection.find(query).projection(projection).forEach(processBlock);


        } catch (MongoException e) {
            // handle MongoDB exception
        }
        return emaitza;
    }
}


