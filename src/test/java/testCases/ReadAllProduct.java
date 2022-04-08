package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class ReadAllProduct {
	
	@Test
	public void readAllProduct () {
		
		
		Response response =
				
		given()
			.baseUri("https://techfios.com/api-prod/api/product")
			.headers("Content-Type","application/json; charset=UTF-8")
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
		
	}

}
