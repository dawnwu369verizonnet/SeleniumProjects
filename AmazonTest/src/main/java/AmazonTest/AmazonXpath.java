package AmazonTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class AmazonXpath {

	@Test
	public void xpathExercise() throws InterruptedException{
		System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\Software\\geckodriver-v0.11.1-win32\\geckodriver.exe");
		WebDriver driver = new FirefoxDriver();
		
//		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\Software\\chromedriver_win32\\chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
		driver.get("http://www.amazon.com");
		Thread.sleep(5000);
		
//		String text = driver.findElement(By.xpath(".//div[@id='nav-main']/div/div/a[@id='nav-link-shopall']/span")).getText();
//		System.out.println(text);
			
	}
	
}
