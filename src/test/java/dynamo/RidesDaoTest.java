package dynamo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class RidesDaoTest {

    private DynamoDB dynamoDB;
    public String tableName = "RideTableTest";

    public RidesDaoTest() {
    		AmazonDynamoDB dbClient = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_1).build();
    		dynamoDB = new DynamoDB(dbClient);
    }
    
    public void createTable() {
        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName, Arrays.asList(new KeySchemaElement("Email", KeyType.HASH)), // Partition
                    Arrays.asList(new AttributeDefinition("Email", ScalarAttributeType.S)),
                                  new ProvisionedThroughput(10L, 10L));
                                table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }
    }

    // Delete Table
    public void deleteTable() {
        Table table = dynamoDB.getTable(tableName);
        try {
            System.out.println("Issuing DeleteTable request for " + tableName);
            table.delete();

            System.out.println("Waiting for " + tableName + " to be deleted...this may take a while...");

            table.waitForDelete();
            System.out.println("Success! Table Deleted");
        } catch (Exception e) {
            System.err.println("Delete Table request failed for " + tableName);
            System.err.println(e.getMessage());
        }
    }
    
    
    
    
    
    // Test Helper Function 
	public String getEmailFromDynamo(String email) {
		HashMap<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.put(":email", email);
		Table table = dynamoDB.getTable("RideTableTest");
		
		QuerySpec querySpec = new QuerySpec().withKeyConditionExpression("#yr = :yyyy")
	    .withValueMap(valueMap);
        ItemCollection<QueryOutcome> items = null;
        Iterator<Item> iterator = null;
        Item item = null;

        try {
            items = table.query(querySpec);

            iterator = items.iterator();
            while (iterator.hasNext()) {
                item = iterator.next();
                return item.getString("Email");
            }

        }
        catch (Exception e) {
            System.err.println("Unable to find " + email);
            System.err.println(e.getMessage());
        }
		return "";
	}
	public String getNameFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getYearFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getPhoneFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getChurchFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getDriverFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getNumSeatsFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getNotesFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public String getAttendFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return "";
	}
	public Long getTimeStampFromDynamo(String email) {

		Table table = dynamoDB.getTable("RideTable");
		return (long) 0;
	}
	
    @Test
    void testInsert() {
    	RidesDaoTest obj = new RidesDaoTest();
    	RidesDao object = new RidesDao();
    	object.insert("Email", "Ryan", 2021, "7605555555", "LightHouse", false);
    	assert(obj.getEmailFromDynamo("Email") == "Email");

    }

}
