package update;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UpdateList {
	public static void main(String[] args) {
		// Replace the uri string with your MongoDB deployment's connection string
		String uri = "mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/";
		String dbNm ="ifs_db";
		String CollectionNm = "request_ifs_collection";
		try (MongoClient mongoClient = MongoClients.create(uri)) {
			MongoDatabase database = mongoClient.getDatabase(dbNm);
			MongoCollection<Document> collection = database.getCollection(CollectionNm);

			Bson query = eq("title", "리스트1");

			Bson updates = Updates.combine(
					Updates.set("soi", "컬럼 추가염~"),
					Updates.addToSet("addSet", "리스트 추가염~"));

			UpdateOptions options = new UpdateOptions().upsert(true);

			UpdateResult result = collection.updateMany(query, updates, options);

			System.out.println("--- 결과 S ---");
			System.out.println( result.getModifiedCount());
			System.out.println("--- 결과 E ---");
		}
	}
}
