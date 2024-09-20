package package1;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
	
	@Test
	public void BasicTest()
	{
		// validate if Add Place API is workimg as expected or not 
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
				
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response (Output response)
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Addresponce = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(PayLoad.AddPlace())
	    .when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
			.extract().response().asString();    //responce = output responce 
		
		System.out.println(Addresponce);
		JsonPath jp = new JsonPath(Addresponce);
		String place_id = jp.getString("place_id");
		System.out.println(place_id);
		
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
		
		//Update Place
		String NewAdress = "208a kalluru, india";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body("{\r\n"
					+ "\"place_id\": \""+place_id+"\",\r\n"
					+ "\"address\": \""+NewAdress+"\",\r\n"
					+ "\"key\": \"qaclick123\"\r\n"
					+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String Getresponce = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(Getresponce);
		JsonPath jp1 = new JsonPath(Getresponce);
		String GetUpdatedAddress = jp1.getString("address");
		System.out.println(GetUpdatedAddress);
		
		// TestNg or Junit for Assortion as it is out side of restAssured which means java code
		Assert.assertEquals(GetUpdatedAddress, NewAdress);
		
		
	}

}











