package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class UpdateAProduct {
	
	SoftAssert softAssert = new SoftAssert();
	
	@Test(priority=1)
	public void updateAProduct () {
				
		HashMap<String,String> payload = new HashMap<String,String>();
		payload.put("id","2962");
		payload.put("name","Samsung Galaxy S21 3.0");
		payload.put("price","899");
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
			.put("/update.php").
		then()
			.extract().response();
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);
//		Assert.assertEquals(actualStatusCode, 200,);
		softAssert.assertEquals(actualStatusCode, 201,"StatusCodes are not matching!" );
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
//		Assert.assertEquals(actualHeader, "application/json; charset=UTF-8");
		softAssert.assertEquals(actualHeader, "application/json; charset=UTF-8", "Headers are not matching!");
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
		
		String productMessage = jp.get("message");
		System.out.println("productId:" + productMessage);
//		Assert.assertEquals(productMessage, "Product was updated.");
		softAssert.assertEquals(productMessage, "Product was updated.", "ProductMessages are not matching!");
		
		softAssert.assertAll();
		
	}

//	@Test(priority=2)
	public void readAProduct () {
		
		
		Response response =
				
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
			.queryParam("id", "2962")
//			.auth().preemptive().basic("userName","password").
			.header("Authorization", "Bearer HJKLSSLKMNCBHB$").
		when()
			.get("/read_one.php").
		then()
			.extract().response();
		
		long actualResponseTime= response.getTimeIn(TimeUnit.MILLISECONDS);
		System.out.println("actualResponseTime:" + actualResponseTime);
		if(actualResponseTime<2000) {
			System.out.println("actualResponseTime is within range");
		}else {
			System.out.println("actualResponseTime is out of range");
		}
		
		int actualStatusCode = response.getStatusCode();
		System.out.println("actualStatusCode:" + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200);
		
		String actualHeader = response.getHeader("Content-Type");
		System.out.println("actualHeader:" + actualHeader);
		Assert.assertEquals(actualHeader, "application/json");
		
		String responseBody = response.getBody().asString();
		System.out.println("responseBody:" + responseBody);
		
		JsonPath jp = new JsonPath(responseBody);
			
		String productId = jp.get("id");
		System.out.println("productId:" + productId);
		Assert.assertEquals(productId, "2962");
		
		String productPrice = jp.get("price");
		System.out.println("productPrice:" + productPrice);
		Assert.assertEquals(productPrice, "899");
		
	}
}
