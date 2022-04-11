package find;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;


public class FindList {
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
            MongoCursor<Document> documentMongoCursor = collection.find()
                    .projection(projectionFields)
                    .sort(Sorts.descending("host.host_response_rate"))
                    .limit(5)
                    .iterator();

            System.out.println("--- 결과 S ---");
            try{
                while (documentMongoCursor.hasNext()){
                    System.out.println(documentMongoCursor.next().toJson());
                }
            }finally {
                documentMongoCursor.close();
            }
            System.out.println("--- 결과 E ---");
        }
    }
}
