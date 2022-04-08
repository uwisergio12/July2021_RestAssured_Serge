package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAProduct {
	
	@Test
	public void readAProduct () {
		
		
		Response response =
				
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
			.queryParam("id", "2941")
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
		Assert.assertEquals(productId, "2941");
		
		String productPrice = jp.get("price");
		System.out.println("productPrice:" + productPrice);
		Assert.assertEquals(productPrice, "199");
	}

}
