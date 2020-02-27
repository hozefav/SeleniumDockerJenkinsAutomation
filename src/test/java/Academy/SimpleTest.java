package Academy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageObjects.LandingPage;
import pageObjects.LoginPage;

import java.io.IOException;

public class SimpleTest extends BaseTest {
	

	@Test
	
	public void base1PageNavigation() throws IOException
	{
		Assert.assertTrue(true);
		
		}

	@Test

	public void base2PageNavigation() throws IOException
	{
		Assert.assertTrue(true);

	}


	
}
