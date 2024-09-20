package package1;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData")
	public void AddBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		
		String responce = given().log().all().header("Content-Type","application/json").body(PayLoad.addBook(isbn , aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(responce);
		JsonPath jp = new JsonPath(responce);
		String id = jp.get("ID");
		System.out.println(id);
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//Array  - Collection of Elements
		// Multi dimentional array - Collection of arrays
		
		return new Object[][] { {"abc","123"}, {"def","456"}, {"ghi","789"} };
	}
	
	

}









