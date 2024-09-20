package RA_GraphQL;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class Query_Mutation {
	
	@Test
	public void Query_Mutation_GraphQLTest()
	{
		// QueryTest
		String locationname = "kalluru";
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String QueryResponce = given().log().all().header("content-type", "application/json")
								.body("{\"query\":\"query($locationname:String!)\\n{\\n  \\n  location(locationId:13766)\\n  {\\n    name\\n    type\\n    dimension\\n    id\\n  }\\n  \\n  episode(episodeId:10508)\\n  {\\n    name\\n    air_date\\n    episode\\n    id\\n  }\\n  \\n  locations(filters:{name:$locationname})\\n  {\\n    info\\n    {\\n      count\\n      pages\\n    }\\n    result\\n    {\\n      id\\n      name\\n      dimension\\n    }\\n  }\\n  \\n}\\n\",\"variables\":{\"locationname\":\""+locationname+"\"}}")
								.when().post("/gq/graphql")
								.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(QueryResponce);
		JsonPath jp = new JsonPath(QueryResponce);
		String locationName = jp.getString("data.location.name");
		System.out.println(locationName);
		
		
		
		//MutationTest
//        String locationname = "kalluru";
		String episodename = "prabhapisode";
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String MutationResponce = given().log().all().header("content-type", "application/json")
								.body("{\"query\":\"mutation($locationname:String!, $episodename:String!)\\n{\\n  \\n  createLocation(location: {name:$locationname,type:\\\"ITEMP\\\", dimension:\\\"General\\\"})\\n  {\\n    id\\n  }\\n  \\n  \\n  createEpisode(episode: {name:$episodename ,air_date:\\\"2000\\\" ,episode:\\\"1\\\"})\\n  {\\n    id\\n  }\\n  \\n  deleteLocations(locationIds:[13762])\\n  {\\n    locationsDeleted\\n  }\\n  \\n  \\n}\\n\\n\",\"variables\":{\"locationname\":\""+locationname+"\",\"episodename\":\""+episodename+"\"}}")
								.when().post("/gq/graphql")
								.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(MutationResponce);
		JsonPath jp1 = new JsonPath(MutationResponce);
		String locationID = jp1.getString("data.createLocation.id");
		String episodeID = jp1.getString("data.createEpisode.id");
		System.out.println(locationID);
		System.out.println(episodeID);
		
	}

}












