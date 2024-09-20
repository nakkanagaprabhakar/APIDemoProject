package package1;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.PayLoad;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {
	
	@Test
	public void complexJsonParseTest()
	{
		//there is jsonPath class >> need to create object for that
		JsonPath jp = new JsonPath(PayLoad.CourcesAndPrices());
		
		//1. Print No of courses returned by API
		int CourcesCount = jp.getInt("courses.size()");
		System.out.println(CourcesCount);
		
		//2.Print Purchase Amount    
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//3. Print Title of the first course
		String firstCourseTitle = jp.getString("courses[0].title");
		System.out.println(firstCourseTitle);
		
		//4. Print All course titles and their respective Prices
		for(int i=0;i<CourcesCount;i++)
		{
			String CourseTitle = jp.getString("courses["+i+"].title");
			int CoursePrice = jp.getInt("courses["+i+"].price");
			
			System.out.println(CourseTitle);
			System.out.println(CoursePrice);	
		}
		
		//5. Print no of copies sold by RPA Course
		for(int i=0;i<CourcesCount;i++)
		{
			String CourseTitle = jp.getString("courses["+i+"].title");
			if(CourseTitle.equalsIgnoreCase("RPA"))
			{
				int CourseCopies = jp.getInt("courses["+i+"].copies");
				System.out.println(CourseCopies);
				break;
			}	
		}
		
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int sum = 0;
		for(int i=0;i<CourcesCount;i++)
		{
			
			int CoursePrice = jp.getInt("courses["+i+"].price");
			int CourseCopies = jp.getInt("courses["+i+"].copies");
			
			int TotalAmount = CoursePrice * CourseCopies;
			sum = sum + TotalAmount;
		}
		
		System.out.println(sum);
		System.out.println(purchaseAmount);
		Assert.assertEquals(sum, purchaseAmount);
		
		
		
	}

}





