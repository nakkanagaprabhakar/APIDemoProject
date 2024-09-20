package package1;

import static io.restassured.RestAssured.*;


import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.parsing.Parser;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

import io.restassured.response.ResponseBody;

public class OAuth2point_O_Test {
	
	@Test
	public void OAuthTest()
	{
		// Add Authoraisation Server for getting accessToken
		
		//UPDATED CODE AS PER GOOGLE UPDATE 
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2FvAHBQUZU6o4WJ719NrGBzSELBFVBI9XbxvOtYpmYpeV47bFVExkaxWaF_XR14PHtTZf7ILSEeamywJKwo_BYs9M&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&session_state=0c32992f0d47e93d273922018ade42d1072b9d1f..a35c&prompt=none#";
		// for getting above URL we needTo sign in bookmyshow using email by manuvally then we will get this URl code
		
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		System.out.println(code);

		// urlEncoding = for special characters remove like % in url
		String response = given().log().all()      
						.queryParams("code",code)
						.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						.queryParams("grant_type", "Authorization_code")
						.queryParams("state", "verifyfjdss")
						.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
						.queryParam("scope", "https://www.googleapis.com/auth/userinfo.email")
						.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
						.when().post("https://www.googleapis.com/oauth2/v4/token")
						.then().log().all().extract().response().asString();

		 System.out.println(response);
		 JsonPath jsonPath = new JsonPath(response);
		 String accessToken = jsonPath.getString("access_token");
		 System.out.println(accessToken);

		 
		 
		//Get Authoraisation Details using above accessToken
		
		 String response1 =  given().contentType("application/json")
				 			.queryParams("access_token", accessToken)
				 			.when().get("https://rahulshettyacademy.com/getCourse.php")
				 			.then().log().all().extract().response().asString();

		 System.out.println(response1);





	}

}
