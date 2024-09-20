package Ecommerce_APITesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;    // given and when and then

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Ecommerce_APITestAll {
	
	@Test
	public void EcommerceAllApiTest()
	{
		// Login An Application "https://rahulshettyacademy.com/client/auth/login"
		
		RequestSpecification loginRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
											.setContentType(ContentType.JSON).build();                              // build is must
		Login_Pojo_request lp = new Login_Pojo_request();
		lp.setUserEmail("prabhakarnakka@gmail.com");
		lp.setUserPassword("Prabha@33");
		
		Login_pojo_response lpResponse = given().log().all().spec(loginRequest).body(lp)
		.when().post("/api/ecom/auth/login")
		.then().log().all().assertThat().statusCode(200).extract().response().as(Login_pojo_response.class);
		
		String token = lpResponse.getToken();
		String Userid = lpResponse.getUserId();
		System.out.println(token);
		System.out.println(Userid);
		
		
		
		// Create or Add product 
		
		RequestSpecification CreateProductRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		String CreateProductresponse = given().log().all().spec(CreateProductRequest).param("productName", "Wallpaper").param("productAddedBy", Userid)
			.param("productCategory", "fashion").param("productSubCategory", "shirts").param("productPrice", "11500")
			.param("productDescription", "Addias Originals").param("productFor", "women")
			.multiPart("productImage" , new File("\\Users\\prabh\\OneDrive\\Pictures\\Saved Pictures\\god-eater-wallpaper-preview.jpg"))
		.when().post("/api/ecom/product/add-product")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath jp = new JsonPath(CreateProductresponse);
		String Productid = jp.get("productId");
		System.out.println(Productid);
		
		
		
		// Create or Place Order
		
		RequestSpecification CreateOrderRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDatails_Sub ods = new OrderDatails_Sub();
		ods.setCountry("india");
		ods.setProductOrderedId(Productid);
		
		List<OrderDatails_Sub> orderDetailList = new ArrayList<OrderDatails_Sub>();
		orderDetailList.add(ods);                  // need to convert "ods" to list type as json bosy present in that type only.
		
		Orders_main om = new Orders_main();
		om.setOrders(orderDetailList);          // list only it allowa so need to send list
		
		String CreateOrderResponse= given().log().all().spec(CreateOrderRequest).body(om)
		.when().post("/api/ecom/order/create-order")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		System.out.println(CreateOrderResponse);
		
		
		
		// Delete Product
		
		RequestSpecification DeleteProductRequest = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		
		String DeleteProductResponse = given().log().all().spec(DeleteProductRequest).pathParam("productId", Productid)
		.when().delete("/api/ecom/product/delete-product/{productId}")         // need to give "key" value here in flower brackets
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath jp1 = new JsonPath(DeleteProductResponse);;
		System.out.println(jp1.getString("message"));
		
		Assert.assertEquals("Product Deleted Successfully", jp1.getString("message"));

	}

}













