package insert;

import static com.mongodb.client.model.Filters.in;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertManyResult;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.conversions.Bson;


public class InsertList {
    public static void main(String[] args) {
        // Replace the uri string with your MongoDB deployment's connection string
        String uri = "mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/";
        String dbNm ="ifs_db";
        String CollectionNm = "request_ifs_collection";

        String findStr = "";
        Iterable<Map.Entry<Integer, BsonValue>> iterable = null;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase(dbNm);
            MongoCollection<Document> collection = database.getCollection(CollectionNm);

            List<Document> movieList = Arrays.asList(
                    new Document().append("title", "리스트1")
                    ,new Document().append("title", "리스트1")
                    ,new Document().append("title", "리스트1"));

            try {
                // multi inert 진행
                InsertManyResult result = collection.insertMany(movieList);
                System.out.println("\n\n\n\nInserted document ids: " + result.getInsertedIds());
                iterable = result.getInsertedIds().entrySet();
                //{ _id : { $in : [ObjectId('first'), ObjectId('second'), ...] } }

            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }

            // find
            Bson projectionFields = Projections.fields(
                    Projections.excludeId());
            MongoCursor<Document> documentMongoCursor = collection.find(in("title", "오성식-1", "오성식-2"))
                    .projection(projectionFields)
                    .iterator();

            System.out.println("--- 조회 결과 S ---");
            try{
                while (documentMongoCursor.hasNext()){
                    System.out.println(documentMongoCursor.next().toJson());
                }
            }finally {
                documentMongoCursor.close();
            }
            System.out.println("--- 조회 결과 E ---");
        }
    }
}
