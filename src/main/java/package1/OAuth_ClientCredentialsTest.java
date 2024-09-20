package package1;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuth_ClientCredentialsTest {
	
	@Test
	public void OAuthTest()
	{
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
		
		
		//Get Authoraisation Details
		
		String responce1 = given().log().all().queryParam("access_token", accessToken)
			.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
			.then().log().all().extract().response().asString();
		
		System.out.println(responce1);
		JsonPath jp1 = new JsonPath(responce1);
		String instructor = jp.getString("instructor");
		System.out.println(instructor);
		
	}

}












