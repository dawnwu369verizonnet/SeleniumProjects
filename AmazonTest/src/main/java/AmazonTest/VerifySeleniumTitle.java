package AmazonTest;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;



public class VerifySeleniumTitle {
	@Test
	public void verifyTitle(){
		
		//ExtentReports logger = ExtentReports.get(VerifySeleniumTitle.class);
		//ExtentReports(String filePath, Boolean replaceExisting, DisplayOrder displayOrder, NetworkMode networkMode, Locale locale)
		
		ExtentReports logger = new ExtentReports("C:\\Users\\dawnw\\workspace\\AmazonTest\\advanceReport.html", true, DisplayOrder.OLDEST_FIRST, NetworkMode.ONLINE);
		ExtentTest test = logger.startTest("Testing website: Http://www.amazon.com");
		
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\Software\\chromedriver_win32\\chromedriver.exe" );
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");		
	
		test.log(LogStatus.INFO, "Browser is up and running");
		
		//driver.get("http://learn-automation.com/");
		test.log(LogStatus.INFO, "Application is up and running");
		
		String title = driver.getTitle();
		test.log(LogStatus.INFO, "Title captured");
		
		Assert.assertTrue(title.contains("Selenium"));
		test.log(LogStatus.PASS, "Testing is completed");
		
		test.addBase64ScreenShot("C:\\Users\\dawnw\\Pictures\\SuccessJustAhead.jpg");
		
		logger.endTest(test);
		logger.flush();	
	}
}
