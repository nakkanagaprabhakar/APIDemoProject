package Pojo_GoogleAPIContract;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;


import java.util.List;

public class GoogleAPI_RestCode {
	
	@Test
	public void GoogleAPI_RestCode_Test()
	{
		
		// Serialization and Request Type 
		
		Location_Sub ls = new Location_Sub();
		ls.setLat(-38.383494);
		ls.setLng(33.427362);
	
		
		AddPlace_Main apm = new AddPlace_Main();
		apm.setLocation(ls);
		apm.setAccuracy(50);
		apm.setName("Frontline house");
		apm.setPhone_number("(+91) 983 893 3937");
		apm.setAddress("29, side layout, cohen 09");
//		apm.setTypes(null);                               // Need to Create ArrayList but it is not importing java.utils.arrayList  
		apm.setWebsite("http: //google.com");
		apm.setLanguage("French-IN");
		
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String responce = given().log().all().queryParam("key", "qaclick123")
			.body(apm)
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(responce);
		
	}

}










