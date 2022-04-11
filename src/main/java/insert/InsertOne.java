package insert;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class InsertOne {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/";
        String dbNm ="ifs_db";
        String CollectionNm = "request_ifs_collection";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(dbNm);
            MongoCollection<Document> collection = database.getCollection(CollectionNm);

            ObjectId objectId = new ObjectId();

            // insert 진행
            InsertOneResult result = collection.insertOne(new Document()
                    .append("_id", objectId)
                    .append("title", "고트젤리" + objectId.toString().substring(0,5))
                    .append("location", "선릉구")
                    .append("kindOfBread", Arrays.asList("잠봉뵈르", "고트피자","고트베이글")));


            // find
            Bson projectionFields = Projections.fields(
                    Projections.excludeId());
            Document doc = collection.find(eq("_id", result.getInsertedId()))
                    .projection(projectionFields)
                    .first();

            System.out.println("--- 결과 S ---");

            System.out.print("insert _id : ");
            System.out.println(result.getInsertedId());
            System.out.print("find : ");
            System.out.println(doc.toJson());

            System.out.println("--- 결과 E ---");
        }
    }
}
