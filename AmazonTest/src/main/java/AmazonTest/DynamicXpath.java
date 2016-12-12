package AmazonTest;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class DynamicXpath {

	public static void main(String[] args) throws InterruptedException {
		//WebElement webelement; 
		System.setProperty("webdriver.chrome.driver", "D:\\Selenium\\Software\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.yahoo.com/");
		Thread.sleep(2000);
		
		driver.findElement(By.id("uh-search-box")).sendKeys("Selenium");
		Thread.sleep(5000);
		
		WebElement element = driver.findElement(By.id("uh-search-box"));
		//.//*[contains(@id, 'uh-assist-link')]
		List<WebElement> allOptions = driver.findElements(By.xpath(".//*[contains(@id, 'uh-assist-link')]"));
		System.out.println("There are " + allOptions.size() + " menu in the dropdown list. ");
		WebElement secondElement = allOptions.get(1);
		String visibleText = secondElement.getText();
		
		//System.out.println("The second menu is " + visibleText );
		for(WebElement we: allOptions){
			System.out.println("The " + allOptions.size() + " dropdown lists are " + we.getText());
		}
		secondElement.click();
		
		
//		Select selectMenu = new Select(element);
//		selectMenu.selectByVisibleText("selenium benefits");
		//selectMenu.click();
//		for(WebElement we: allOptions){
//			//driver.sendKeys(Keys.DOWN);
//			System.out.println("All the menu are " + allOptions );
//			//if(we.getText().contains("selenium"))Select.selectByVisibleText("selenium");
//		}
	}
}