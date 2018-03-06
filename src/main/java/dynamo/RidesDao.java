package dynamo;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;


/**
 * Data accessor object for rides DynamoDB
 */
public class RidesDao {
	public String tableName = "RideTable";
    private AmazonDynamoDB dbClient;
    private DynamoDB dynamoDB;

    // Needs to deal with the credentials...
    public RidesDao() {
        dbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_1).build();
        dynamoDB = new DynamoDB(dbClient);
    }

	// inserts a new person into the table with an email & a name
	public void insert(String email, String name, int year, String phoneNumber, String church, boolean attendance) {

		Table table = dynamoDB.getTable("RideTable");

		final Map<String, Object> infoMap = new HashMap<String, Object>();
		infoMap.put("Email", email);
		infoMap.put("Name", name);
		infoMap.put("Church", church);
		infoMap.put("year", year);
		infoMap.put("Phone Number", phoneNumber);
		infoMap.put("attendance", attendance);


		try {
			System.out.println("Adding a new item...");
			PutItemOutcome outcome = table
					.putItem(new Item().withPrimaryKey("Email", email).withMap("info", infoMap));

			System.out.println("PutItem succeeded:\n" + outcome.getPutItemResult());

		} catch (Exception e) {
			System.err.println("Unable to add " + email);
			System.err.println(e.getMessage());
		}
	}

	//updates table based on email & primary key
	public void update(String newEmail, String oldEmail) {// use email & rideInfo object as parameter
		Table table = dynamoDB.getTable("RideTable");

		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Email", oldEmail)
				.withUpdateExpression("set info.Email = :e").withValueMap(new ValueMap().withString(":e", newEmail))
				.withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			System.out.println("Updating the item...");
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());

		} catch (Exception e) {
			System.err.println("Unable to update "  + oldEmail + " with the new email: " + newEmail);
			System.err.println(e.getMessage());
		}
	}

    public void read(String email) {}

    public void readAll() {}

    public void delete(String email) {}

}
