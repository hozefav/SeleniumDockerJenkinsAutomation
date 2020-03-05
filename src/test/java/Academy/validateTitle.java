package Academy;

import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pageObjects.LandingPage;

public class validateTitle extends BaseTest {

	@BeforeTest
	public void initialize() throws IOException
	{
	
		 driver =initializeDriver();

			
		//driver.get(prop.getProperty("url"));
		driver.get("http://qaclickacademy.com");

	}
	@Test
	
	public void validateAppTitle() throws IOException
	{
		
		//one is inheritance
		// creating object to that class and invoke methods of it
		LandingPage l=new LandingPage(driver);
		//compare the text from the browser with actual text.- Error..
		Assert.assertEquals(l.getTitle().getText(), "FEATURED COURSES");

		 System.out.println("Test running from Inside docker");
		 System.out.println("Test completed");

		;
	
		
		}
	@AfterTest
	public void teardown()
	{
		
		driver.quit();
		driver=null;
		
	}

	

	
}
