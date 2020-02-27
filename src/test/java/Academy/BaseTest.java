package Academy;

import com.google.common.util.concurrent.Uninterruptibles;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

	public static WebDriver driver;
	public Properties prop;


	@BeforeSuite
	public void initialDelay(){
		//intentionally added this as chrome/firefox containers take few ms to register
		//to hub - test fails saying hub does not have node!!
		//very rare
		Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
	}

public WebDriver initializeDriver() throws IOException
{
	
 //prop= new Properties();
/*FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");

prop.load(fis);*/
//String browserName=prop.getProperty("browser");
//System.out.println(browserName);

//String browserName = "chrome";
String browserName = "remoteChrome";

if(browserName.equals("chrome"))
{
	 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/main/java/resources/chromedriver.exe");
	driver= new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//execute in chrome driver
	
}
else if(browserName.equals("remoteChrome")){
    //DesiredCapabilities dc = DesiredCapabilities.chrome()
			;

    /*if (System.getProperty("browser").equals("firefox"))
        dc = DesiredCapabilities.firefox();

    String host = System.getProperty("seleniumHubHost");*/

    //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);

	ChromeOptions chromeOptions = new ChromeOptions();
	chromeOptions.addArguments("--verbose");
	chromeOptions.addArguments("--whitelisted-ips=''");
	//chromeOptions.addArguments("--proxy-server=93.180.7.246:8080");
	String host = System.getProperty("seleniumHubHost");
	driver = new RemoteWebDriver(new URL("http://"+host+":4444/wd/hub"),chromeOptions);
	//driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),chromeOptions);
}
else if (browserName.equals("firefox"))
{
	 driver= new FirefoxDriver();

	//firefox code
}
else if (browserName.equals("IE"))
{
//	IE code
}

driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
return driver;


}

public void getScreenshot(String result) throws IOException
{
	File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(src, new File("C://test//"+result+"screenshot.png"));
	
}



}
