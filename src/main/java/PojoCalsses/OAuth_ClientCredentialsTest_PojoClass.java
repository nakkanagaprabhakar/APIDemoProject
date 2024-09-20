package PojoCalsses;

import org.testng.annotations.Test;

import PojoCalsses.GetCources_Main;
import PojoCalsses.api_SubSub;
import PojoCalsses.webAutomation_SubSub;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;


import java.util.Arrays;
import java.util.List;

public class OAuth_ClientCredentialsTest_PojoClass {
	
	@Test
	public void OAuthTest()
	{
		// DeSerialization and Response Type
		
		// Add Authoraisation Server 
		
		String responce = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.formParam("grant_type", "client_credentials")
			.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().extract().response().asString();
		
		System.out.println(responce);
		JsonPath jp = new JsonPath(responce);
		String accessToken = jp.getString("access_token");
		System.out.println(accessToken);
		
		
		//Get Authoraisation Details  or Get Cources Details
		
		GetCources_Main gc = given().log().all().queryParam("access_token", accessToken)
			.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
			.then().log().all().extract().response().as(GetCources_Main.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		
		// Get Api or soapUI cource Title and Price
		
//		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
//		System.out.println(gc.getCourses().getApi().get(1).getPrice());
		
		List<api_SubSub> apiCources = gc.getCourses().getApi();
		for(int i=0;i<apiCources.size();i++)
		{
			if(apiCources.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
//				System.out.println(gc.getCourses().getApi().get(i).getCourseTitle());
//				System.out.println(gc.getCourses().getApi().get(i).getPrice());
				System.out.println(apiCources.get(i).getCourseTitle());
				System.out.println(apiCources.get(i).getPrice());
			}
		}
		
		
		// Get API or WebAutomation All CourceTitles 
		
		String[] ExpectedcourseTitlesArray = {"Selenium Webdriver Java", "Cypress", "Protractor"};    // This is an Array
		List<String> ExpectedcourseTitlesList = Arrays.asList(ExpectedcourseTitlesArray);  // Array converted into List
		
		
		
		List<webAutomation_SubSub> getWebAutomationCources = gc.getCourses().getWebAutomation();
		for(int i=0;i<getWebAutomationCources.size();i++)
		{
			System.out.println(getWebAutomationCources.get(i).getCourseTitle());
			
		}
		
		
		
		
	}

}












