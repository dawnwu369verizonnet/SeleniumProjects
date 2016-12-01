package AmazonTest.AmazonTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *  Implements Selenium Tests for Amazon Website
 */
public class AmazonTest {
		private WebDriver driver; 
		String browser = "IE";

		@BeforeTest
		public void instantiateWebDriver() throws InterruptedException{
			
			driver.findElement(By.id("s-result-count")).click();
			
			
//			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
//			dc.setCapability(CapabilityType.BROWSER_NAME,  "IE");
//			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,  true);
//			System.setProperty("webdriver.ie.driver", "D:\\Selenium\\Software\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
//			driver = new InternetExplorerDriver(dc);
			
			driver = getDriver("Chrome");
			driver.get("https://www.amazon.com/");		
			Thread.sleep(500);
		}
		
		private WebDriver getDriver(String browserType){
			WebDriver d = null;
			if(browserType.equalsIgnoreCase(ConstantsFile.IE_BROWSER)){
				System.setProperty("webdriver.ie.driver", "D:\\Selenium\\Software\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
				d = new InternetExplorerDriver();						
			}else if(browserType.equalsIgnoreCase(ConstantsFile.CHROME_BROWSER)){
				System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\Software\\chromedriver_win32\\chromedriver.exe");
				d = new ChromeDriver();
			}
			return d;
		}
		
		@AfterTest
		public void closeBrowser(){
			driver.quit();
		}
		
		@Test
		public void myAmazonTest(){
			
		}

		
}
