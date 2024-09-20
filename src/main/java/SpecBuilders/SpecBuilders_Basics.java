package SpecBuilders;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class SpecBuilders_Basics {
	
	@Test
	public void BasicTest()
	{
	//AddPlace
		
		//SpecBuilders - process of separating common things from RestAssured Test and reuse that test in entire code, Where it is needed is called SpecBuilders
						//>> Two Types of spec Builders are there
								//1) RequestSpecBuilder    - for request Code and used for given() common things
								//2) ResponseSpecBuilder - For response code and used for then() common things
		
		//RequestSpecBuilder
		RequestSpecification requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
									.addQueryParam("key", "qaclick123")
									.setContentType(ContentType.JSON).build();  // Build mandatory in last
		
		//ResponseSpecBuilder
		ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200)
									.expectContentType(ContentType.JSON).build();    // Build mandatory in last

		
		String Addresponce = given().spec(requestSpecBuilder).body(PayLoad.AddPlace())
							.when().post("maps/api/place/add/json")
							.then().spec(responseSpecBuilder).extract().response().asString();    
		
		System.out.println(Addresponce);
		JsonPath jp = new JsonPath(Addresponce);
		String place_id = jp.getString("place_id");
		System.out.println(place_id);
		
		
//		//Get Place
//				String Getresponce = given().spec(requestSpecBuilder).queryParam("place_id", place_id)
//				.when().get("maps/api/place/get/json")
//				.then().spec(responseSpecification).extract().response().asString();
//				
//				System.out.println(Getresponce);
//				JsonPath jp1 = new JsonPath(Getresponce);
//				String GetUpdatedAddress = jp1.getString("address");
//				System.out.println(GetUpdatedAddress);
		
		
		
	}

}











