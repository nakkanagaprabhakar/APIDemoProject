package package1;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;



public class Basics_1 {
	
	@Test
	public void BasicTest() throws IOException
	{
		// validate if Add Place API is workimg as expected or not 
		//Add place-> Update Place with New Address -> Get Place to validate if New address is present in response
				
		//given - all input details 
		//when - Submit the API -resource,http method
		//Then - validate the response (Output response)
		
		// json file need to convert into Byte format with help of json file path >>  then byte should be convert into String
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String Addresponce = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\prabh\\Downloads\\AddPlace.json"))))
	    .when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
			.extract().response().asString();    //responce = output responce 
		
		System.out.println(Addresponce);
		JsonPath jp = new JsonPath(Addresponce);
		String place_id = jp.getString("place_id");
		System.out.println(place_id);
		
		
		
		
	}

}











