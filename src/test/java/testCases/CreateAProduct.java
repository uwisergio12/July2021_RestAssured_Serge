package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;

public class CreateAProduct {
	
	@Test
	public void createAProduct () {
		/*{
		    "name": "Samsung Galaxy S21",
		    "price": "750",
		    "description": "Amazing phone.",
		    "category_id": 2
		}
		*/
		
		HashMap<String,String> payload = new HashMap<String,String>();
		payload.put("name","Samsung Galaxy S21");
		payload.put("price","750");
		payload.put("description","Amazing phone.");
		payload.put("category_id","2");
		
		Response response =
				
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
//			.body(new File("src\\main\\java\\data\\CreatePayload.json"))
			.body(payload)
//			.auth().preemptive().basic("userName","password").
			.header("Authorization", "Bearer HJKLSSLKMNCBHB$").
		when()
			.post("/create.php").
		then()
			.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 201);
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String productMessage = jp.get("message");
		System.out.println("productId:" + productMessage);
		Assert.assertEquals(productMessage, "Product was created.");
		
	}

}
