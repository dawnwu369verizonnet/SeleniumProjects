package AmazonTest;

import java.util.List;

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

/**
 *  Implement Selenium Tests for Amazon Website
 *  Date: November 2016
 *  
 */
public class AmazonTest {
		private WebDriver driver; 
		String delimiter = " ";

		@BeforeTest
		public void instantiateWebDriver() throws InterruptedException{
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
		}
		
		@Test(priority = 0)
		public void myAmazonTest() throws InterruptedException{
			driver.findElement(By.id("searchDropdownBox")).click();
			Thread.sleep(800);
			
			Select objSelect = new Select(driver.findElement(By.id("searchDropdownBox")));
			objSelect.selectByVisibleText("Electronics");
			
			System.out.println("Electronics is selected. " );
			
			//Requirement 2: the following steps to enter the keyword of Samsung in search box and click Search button. 
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Samsung");
			//Requirement 3: The following steps to click the search button after entering the keyword of Samsung. 
			driver.findElement(By.xpath(".//*[@id='nav-search']/form/div[2]/div/input")).click();
			String result1 = new String(driver.findElement(By.id("s-result-count")).getText());
			String result1Arr[] = result1.split(delimiter);
			System.out.println("Requirement 3: the total number of results by searching the keyword of Samsung is " + result1Arr[2]);

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
			System.out.println("Requirement 4: the total no. of search results by searching the keyword of 'Samsung TV' is " + result2Arr[2]);
			String result2PageArr[] = result2Arr[0].split("-");
			System.out.println("the total no. of current page is " + result2PageArr[1]);
			
			//filter(".//*[@id='ref_1232878011']/li[1]/a/span[1]", ".//*[@id='ref_4972967011']/li[1]/a/span");
			filter("32 Inches & Under", "2016");	
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

				System.out.println("After filtering by displaySize and modelYear is result3: " + result3);
				String result3Arr[] = result3.split(delimiter);

				System.out.println("The total no. after filtering by displaySize and modelYear is result3Arr[0] is: " + result3Arr[0]);
				//Requirement 7: Following steps to report the star rating of each of the result in the first result page. 
				try{
					Thread.sleep(500);
					WebElement stars = driver.findElement(By.xpath(".//*[@id='result_0']/div/div/div/div[2]/div[3]/div[2]/div[1]/span/span/a/i[1]"));
					//5 star image.
					
					Actions action = new Actions(driver);
					action.moveToElement(stars).build().perform();
					Thread.sleep(1000);
//					stars.click();
					String result4 = driver.findElement(By.xpath(".//*[@id='a-popover-content-1']/div/div/div/div[1]/span")).getText();
					//this is the rating: 
					System.out.println("The star rating for this product is" + result4);
				}catch(Exception e){
					System.out.println("Exception handled, capturing the msg of 'Star rating is failed. " + e.getMessage());
				}
				
				//Requirement 8: Following step to click and expand the first result from the search results. 
				driver.findElement(By.xpath(".//*[@id='result_0']/div/div/div/div[1]/div/div/a/img")).click();
				Thread.sleep(500);
				
				//Requirement 9: Following steps to log critical information of the selected product details, 
				//for the reporting purpose like price, product details, Technical details etc. 
				
				try {
					System.out.println("The price of this product is " +
							(driver.findElement(By.id("priceblock_ourprice"))).getText());//get the price of the product.
				} catch (Exception e) {
					System.out.println("No price is available for this product");
				}
				System.out.println("The product details is" +
						(driver.findElement(By.id("feature-bullets"))).getText());//get product details of the product. 
				List<WebElement> comments = driver.findElements(By.xpath("//div[@id='revMHRL']/div/div/div[@class='a-section']"));
				//Requirement 10: Following steps to report the first 6 customer reviews. 
				System.out.println("The first customer review is " 
						+ comments.get(0).getText());
				System.out.println("The second customer review is " 
						+ comments.get(1).getText());
				System.out.println("The third customer review is " 
						+ comments.get(2).getText());
		}	
}