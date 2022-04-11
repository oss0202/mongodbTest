package delete;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;


public class DeleteOne {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/";
        String dbNm = "ifs_db";
        String CollectionNm = "request_ifs_collection";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(dbNm);
            MongoCollection<Document> collection = database.getCollection(CollectionNm);

            Bson projectionFields = Projections.fields(
                    Projections.excludeId());
            Document doc = collection.find(eq("title", "리스트1"))
                    .projection(projectionFields)
                    .first();

            System.out.println("--- 삭제 전 결과 S ---");
            System.out.println(doc.toJson());
            System.out.println("--- 삭제 전 결과 E ---");
            System.out.println("--- 삭제 후 결과 S ---");

            Bson query = eq("title", "리스트1");
            collection.deleteOne(query);
            System.out.println("--- 삭제 후 결과 E ---");
            doc = collection.find(eq("title", "리스트1"))
                    .projection(projectionFields)
                    .first();

            System.out.println("--- 삭제 후 결과 S ---");
            System.out.println(doc.toJson());
            System.out.println("--- 삭제 후 결과 E ---");
        }
    }
}
