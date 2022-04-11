package find;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;


public class FindOne {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/";
        String dbNm ="sample_airbnb";
        String CollectionNm = "listingsAndReviews";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(dbNm);
            MongoCollection<Document> collection = database.getCollection(CollectionNm);

            Bson projectionFields = Projections.fields(
                    Projections.include("name", "listing_url"),
                    Projections.excludeId());
            Document doc = collection.find(eq("_id", "10006546"))
                    .projection(projectionFields)
                    .sort(Sorts.descending("host.host_response_rate"))
                    .first();

            System.out.println("--- 결과 S ---");
            if (doc == null) {
                System.out.println("No results found.");
            } else {
                System.out.println(doc.toJson());
            }
            System.out.println("--- 결과 E ---");
        }
    }
}
