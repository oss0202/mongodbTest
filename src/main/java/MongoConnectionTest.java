import static com.mongodb.client.model.Filters.gte;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
/**
 * @author : 221213
 * @date : 2022. 03. 25
 */
public class MongoConnectionTest {
	public static void main(String[] args) {
		String dbNm = "sample_airbnb";
		String CollectionNm = "listingsAndReviews";
		ConnectionString connectionString = new ConnectionString("mongodb+srv://oss0202:oss0202@osscluster.xuy4o.mongodb.net/" + dbNm + "?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.build();
		MongoClient mongoClient = MongoClients.create(settings);
		MongoDatabase database = mongoClient.getDatabase(CollectionNm);
		System.out.println(database.listCollectionNames());
		System.out.println();
		System.out.println(database.getCodecRegistry());
		System.out.println();
		MongoCollection<Document> documentMongoCollection =  database.getCollection(CollectionNm);
		List<Document> documentList = documentMongoCollection.find(gte("_id", "10006546")).into(new ArrayList<>());

		for (Document document : documentList) {
			System.out.println(document.toJson());
		}

	}
}
