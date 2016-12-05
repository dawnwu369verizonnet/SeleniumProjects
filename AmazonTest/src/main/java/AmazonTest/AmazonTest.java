package AmazonTest;

import java.util.List;

import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.NetworkMode;

/**
 *  Implement Selenium Tests for Amazon Website
 *  Date: November 2016
 */
public class AmazonTest {
		private WebDriver driver; 
		String delimiter = " ";
		ExtentReports logger;
		ExtentTest test;
		
		@BeforeTest
		public void instantiateWebDriver() throws InterruptedException{
			logger = new ExtentReports("C:\\Users\\dawnw\\workspace\\AmazonTest\\advanceReport.html", true, DisplayOrder.OLDEST_FIRST, NetworkMode.ONLINE);
			test = logger.startTest("Testing website: Http://www.amazon.com");
//			DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
//			dc.setCapability(CapabilityType.BROWSER_NAME,  "IE");
//			dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,  true);
//			System.setProperty("webdriver.ie.driver", "D:\\Selenium\\Software\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
//			driver = new InternetExplorerDriver(dc);
			driver = getDriver("Chrome");
			driver.get("https://www.amazon.com/");		
			Thread.sleep(5000);
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
			logger.endTest(test);
			logger.flush();	
		}
		
		@Test
		public void myAmazonTest() throws InterruptedException{
			driver.findElement(By.id("searchDropdownBox")).click();
			Thread.sleep(800);
			
			Select objSelect = new Select(driver.findElement(By.id("searchDropdownBox")));
			objSelect.selectByVisibleText("Electronics");
			
			test.log(LogStatus.INFO,"Electronics is selected. " );
			
			
			//Requirement 2: the following steps to enter the keyword of Samsung in search box and click Search button. 
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Samsung");
			//Requirement 3: The following steps to click the search button after entering the keyword of Samsung. 
			driver.findElement(By.xpath(".//*[@id='nav-search']/form/div[2]/div/input")).click();
			String result1 = new String(driver.findElement(By.id("s-result-count")).getText());
			String result1Arr[] = result1.split(delimiter);
			test.log(LogStatus.INFO,"Requirement 3: the total number of results by searching the keyword of Samsung is " + result1Arr[2]);

			//Requirement 4-1: The following steps to clear the search field and append TV to Samsung and click the search button.
			driver.findElement(By.id("twotabsearchtextbox")).clear();
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Samsung TV");
			driver.findElement(By.xpath(".//*[@id='nav-search']/form/div[2]/div/input")).click();
			Thread.sleep(1000);
			
			//Requirement 4-2: The following steps to report the total no. 
			//of search results along with the no. of results in current page. 
			String result2 = driver.findElement(By.id("s-result-count")).getText();
			Thread.sleep(500);
			String result2Arr[] = result2.split(delimiter);
			test.log(LogStatus.INFO,"Requirement 4: the total no. of search results by searching the keyword of 'Samsung TV' is " + result2Arr[2]);
			String result2PageArr[] = result2Arr[0].split("-");
			test.log(LogStatus.INFO,"the total no. of current page is " + result2PageArr[1]);
			
			//filter(".//*[@id='ref_1232878011']/li[1]/a/span[1]", ".//*[@id='ref_4972967011']/li[1]/a/span");
			filter("32 Inches & Under", "2016");
			test.log(LogStatus.PASS, "Testing is completed");
		}

			//Requirement 5: Parameterize 2 of the filtering parameters of TV displaySize and TV modelYear and display the filter.. 
			public void filter(String displaySize, String modelYear) throws InterruptedException{
				////div[contains(text(),'noreply@somedomain.com')]
				String xpathDisplaySize = "//span[contains(text(), '" + displaySize + "')]";
				String xpathModelYear = "//span[@class='refinementLink' and contains(text(), '" + modelYear + "')]";
				driver.findElement(By.xpath(xpathDisplaySize)).click();//choose TV displaySize
				//".//*[@id='ref_1232878011']/li[1]/a/span[1]"
				Thread.sleep(5000);
				driver.findElement(By.xpath(xpathModelYear)).click();//choose TV modelYear
				//".//*[@id='ref_4972967011']/li[1]/a/span"
				Thread.sleep(1000);
				
				//Requirement 6-1: Following steps to report total no. of search results 
				String result3 = driver.findElement(By.id("s-result-count")).getText();

				test.log(LogStatus.INFO,"After filtering by displaySize and modelYear is result3: " + result3);
				String result3Arr[] = result3.split(delimiter);

				test.log(LogStatus.INFO,"The total no. after filtering by displaySize and modelYear is result3Arr[0] is: " + result3Arr[0]);
				//Requirement 7: Following steps to report the star rating of each of the result in the first result page. 
				try{
					Thread.sleep(500);
					WebElement stars = driver.findElement(By.xpath(".//*[@id='result_0']/div/div/div/div[2]/div[3]/div[2]/div[1]/span/span/a/i[1]"));
					//5 star image.
					
					Actions action = new Actions(driver);
					action.moveToElement(stars).build().perform();
					Thread.sleep(2000);
					
					//WebElement one = driver.findElement(By.xpath(".//div[@class='a-popover-content']"));
					
					String result4 = driver.findElement(By.xpath(".//div[@class='a-popover-content']/div/div/div/div/span")).getText();
					// ".//div[@class='a-popover-content']/div/div/div/div[@class='a-section']/span
					//this is the rating: 
					test.log(LogStatus.INFO,"The star rating for this product is " + result4);
				}catch(Exception e){
					test.log(LogStatus.FAIL, "Exception handled, capturing the msg of the Star rating is failed. " + e.getMessage());
				}
				
				//Requirement 8: Following step to click and expand the first result from the search results. 
				driver.findElement(By.xpath(".//*[@id='result_0']/div/div/div/div[1]/div/div/a/img")).click();
				Thread.sleep(500);
				
				//Requirement 9: Following steps to log critical information of the selected product details, 
				//for the reporting purpose like price, product details, Technical details etc. 
				
				try {
					test.log(LogStatus.INFO,"The price of this product is " +
							(driver.findElement(By.id("priceblock_ourprice"))).getText());//get the price of the product.
				} catch (Exception e) {
					test.log(LogStatus.INFO, "No price is available for this product");
				}
				test.log(LogStatus.INFO,"The product details is" +
						(driver.findElement(By.id("feature-bullets"))).getText());//get product details of the product. 
				List<WebElement> comments = driver.findElements(By.xpath("//div[@id='revMHRL']/div/div/div[@class='a-section']"));
				//Requirement 10: Following steps to report the first 6 customer reviews. 
				test.log(LogStatus.INFO,"The first customer review is " 
						+ comments.get(0).getText());
				test.log(LogStatus.INFO,"The second customer review is " 
						+ comments.get(1).getText());
				test.log(LogStatus.INFO,"The third customer review is " 
						+ comments.get(2).getText());
		}	
}