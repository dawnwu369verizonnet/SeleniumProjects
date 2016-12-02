package AmazonTest.AmazonTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class IEtestforDesiredCapabilities {

	@Test
	public void myCapability(){
		DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
		dc.setCapability(CapabilityType.BROWSER_NAME,  "IE");
		dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,  true);
		
		System.setProperty("webdriver.ie.driver", "D:\\Selenium\\Software\\IEDriverServer_Win32_2.53.1\\IEDriverServer.exe");
		WebDriver driver = new InternetExplorerDriver();
		driver.get("http://www.google.com");	
		
		//The Desired Capabilities class help to set an environment to define the behaviors of the browser environment  
		//on which the test can be executed. 
		//it helps to launch the application in the desired environment having the capabilities that we desire to use. 
	}
}