package Academy;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

	public static WebDriver driver;
	public Properties prop;
public WebDriver initializeDriver() throws IOException
{
	
 prop= new Properties();
FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");

prop.load(fis);
String browserName=prop.getProperty("browser");
System.out.println(browserName);

if(browserName.equals("chrome"))
{
	 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\java\\resources\\chromedriver.exe");
	driver= new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//execute in chrome driver
	
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

	/**
	 * Waits for any button to be clickable
	 *
	 * @param element    The element to wait for
	 * @param secsToWait The time to wait in seconds
	 * @return WebElement or throws timeout exception
	 */
	protected WebElement waitForButton(WebElement element, int secsToWait) {
		return (new WebDriverWait(driver, secsToWait))
				.until(ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Selects a value from a dropdown based on the label/text preceding it.
	 *
	 * @param label      Exact text preceding the field (Case sensitive)
	 * @param option     The value inside the dropdown to select
	 * @param secsToWait How long to wait for the value to become available
	 */
	public void selectFromDropdown(String label, String option, int secsToWait) {
		WebElement input = null;
		try {
			input = waitForButton((driver.findElement(By.xpath(("//span[contains(text(),'" + label + "')]//following::select[1]")))), secsToWait);
		} catch (Exception e) {
			try {
				input = waitForButton((driver.findElement(By.xpath(("//td[contains(text(),'" + label + "')]//following::select[1]")))), secsToWait);
			} catch (Exception e1) {
				Assert.fail("Couldn't select from dropdown: " + label);
			}
		}
		selectFromDropdown(input, option);
	}

	/**
	 * selects an element from a dropdown menu that exactly matches the option provided
	 *
	 * @param element the selector of the dropdown menu
	 * @param option  the value we are setting in the dropdown menu
	 */
	public void selectFromDropdown(WebElement element, String option) {
		Select selector = new Select(waitForElement(element));
		selector.selectByVisibleText(option);
	}

	/**
	 * Selects a value from a dropdown based on the label/text preceding it. Default wait option.
	 *
	 * @param label  Exact text preceding the field (Case sensitive)
	 * @param option The value inside the dropdown to select
	 */
	public void selectFromDropdown(String label, String option) {
		selectFromDropdown(label, option, 10);
	}

	public WebElement waitForElement(WebElement element, int secsToWait) {
		WebElement find;
		WebDriverWait wait = new WebDriverWait(driver, secsToWait);
		try {
			Thread.sleep(secsToWait * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		find = wait.until(ExpectedConditions.visibilityOf(element));
		return find;
	}

	/**
	 * waits for an element to become present on the page for 10 seconds
	 *
	 * @param element the selector of the element being waited for
	 * @return whether the element was found on the page in the allotted time
	 */
	protected WebElement waitForElement(WebElement element) {
		WebElement find;
		WebDriverWait wait = new WebDriverWait(driver, 20);
		find = wait.until(ExpectedConditions.visibilityOf(element));
		return find;
	}

	/**
	 * enters value into field type input
	 * @param label the name of the field
	 * @param value the value of the field
	 * @return void
	 */
	public void enterValueIntoField(String label,String value){
		WebElement element = waitForElement(driver.findElement(By.xpath("//span[text()='"+label+"']//following::input[2]")));
		element.sendKeys(value);
	}

	/**
	 * switch to a new window
	 */
	public void switchWindow() {

		for (String winHandle : driver.getWindowHandles()) {
			try {
				driver.switchTo().window(winHandle);
				//System.out.println(" pagina "+ winHandle);
			} catch (NoSuchWindowException e) {

			}
		}
	}

	/**
	 * wait 5 seconds for all windows to close when multiple popup windows close simultaneously
	 */
	public void waitForWindowAndSwitch() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.switchWindow();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(driver.findElement(By.id("gsft_main")));
	}

	/**
	 * Select a sublink-Add New Related Agency from the topFr under Agency Relationships->RelationshipType->Add New Related Agency.
	 *
	 * @param label Exact text of the link on the page (Case sensitive)
	 */
	public void clickSearchGlassButton(String label) {
		WebElement input = null;

		try {
			input = waitForButton((driver.findElement(By.xpath(("//span[text()='" + label + "']//following::button")))), 10);
		} catch (Exception e) {
			Assert.fail("Couldn't click search button of: " + label);
		}
		input.click();
	}

	/**
	 * Select the current day from the calendar
	 *
	 * @return void
	 */


	public void calendarDatePicker(String label) throws InterruptedException {
		//Select the current date
		DateFormat dateFormat2 = new SimpleDateFormat("dd");
		Date date2 = new Date();
		String today = dateFormat2.format(date2);

		WebElement calendarButton = waitForElement(driver.findElement(By.xpath("//span[text() = '"+label+"']//following::span[@class = 'input-group-btn'][1]")));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", calendarButton);
		Thread.sleep(500);
		waitForElement(calendarButton).click();
		waitForElement(calendarButton).click();

		//calendarButton.click();
		//Find the calendar
		WebElement dataWidget = driver.findElement(By.className("calTable"));
		List<WebElement> columns = dataWidget.findElements(By.tagName("a"));
		for (WebElement value : columns){
			if(value.getText().equals(today)) {
				value.click();
				//value.sendKeys(Keys.ENTER);
				break;
			}
		}
	}

	public void clickLinkInTable(String label){
		WebElement element = driver.findElement(By.xpath("(//a[contains(@aria-label,'"+label+"')])[2]"));
		element.click();
	}

	public WebElement populateInputField(String label, String value){
		WebElement element = driver.findElement(By.xpath("//span[text() = '" + label + "']//following::input"));
		element.sendKeys(value);
		return element;
	}

	public String readSNField(String label){
		WebElement element = driver.findElement(By.xpath("//span[text()='"+ label +"']//following::input[1]"));
		return element.getAttribute("value");
	}

	//## Description: This method is used to sort the ccolumn
	//## History: Hozefa-- 18/Oct/2019-- Created
	public void sortColumnMostRecent(String columnName){
		System.out.println("Sorting by most recent");
		WebElement column = driver.findElement(By.xpath("(//*[@glide_label='"+columnName+"'])[1]/span/i"));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@glide_label='"+columnName+"'])[1]/span/i")));
		column.click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(createdColumnSortZtoA));
		driver.findElement(By.xpath("//*[contains(text(),'Sort (z to a)')]")).click();
	}



}
