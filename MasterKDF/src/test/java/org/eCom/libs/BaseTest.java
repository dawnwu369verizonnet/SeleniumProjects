package org.eCom.libs;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import org.testng.annotations.AfterClass;
import org.testng.AssertJUnit;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.apache.commons.codec.binary.Base64;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eCom.utils.ConstantsFile;
import org.eCom.utils.EnumeValue.Action;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
//import com.relevantcodes.extentreports.Chart;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

public class BaseTest {
	protected WebDriver driver;
	protected WebElement webelm;
	protected long timeout = 90;
	public Set<String> errorSet=new HashSet<String>();
	public boolean actionStatus=false;
	protected String fileName;
	protected int lineNumber;
	protected String methodName;
	protected Properties prop;
	protected Boolean isPresent;
	protected ScreenRecorder screenRecorder;
	
	protected FileInputStream fInput;
	protected XSSFWorkbook workBook;
	protected XSSFSheet sheet;
	protected XSSFSheet sheet1;
	protected XSSFSheet sheet2;
	protected int totalRows;
	protected int totalRows1;
	protected int totalRows2;
	protected Row row;
	protected Cell cell;
	protected Row row2;
	protected Row row5;
	protected Cell cell2;
	protected String objectNameOut;
	protected List<String> al1;
	protected ExtentReports logger;
	
	protected byte[] imageByteArray;
	protected String imageByteArray1;
	protected String base64String;
	protected String setFlag="fail";
	protected String getEditValue;
	protected String getDropDownValue;
	protected SimpleDateFormat dateFormat = new SimpleDateFormat(
	        "yyyy-MM-dd HH.mm.ss");
	protected String browser1;
	
	public BaseTest(){
		//logger = ExtentReports.get(BaseTest.class);
		
		
//		logger.init("D:\\Videos\\Report"+ dateFormat.format(new Date()) +".html", true);
//		logger.config().displayCallerClass(false);
//		logger.config().documentTitle("Regression Test Suite Report");
		String css = "#topbar { background-color: #2E8B57; }" +
		        ".topbar-items-right span { color: white; }" +
		        ".menu span { color: blue; }" +
		        ".menu-item-selected span { border-bottom: 1px solid red; }" +
		        "#dashboard { background-color: transparent; }" +
		        ".test { border: 1px solid darkblue; }" + 
		        ".description { background-color: transparent; border-left: 2px solid orange; padding: 2px 15px;}" + 
		        ".name { color: darkgreen; }" + 
		        ".extent-table { border: 1px solid #800080; }" + 
		        ".extent-table th { background: none repeat scroll 0 0 olivedrab; color: #333399; }" + 
		        ".extent-table td { border-bottom: 1px solid #800080; }";
		 
//		logger.config().addCustomStyles(css);
//		logger.config().useExtentFooter(false);
//		logger.config().displayCallerClass(false);
//		logger.config().reportTitle("Qa Scripts Automation Report");
//		logger.config().reportHeadline("Qa Scripts");
//		logger.config().chartTitle(Chart.TEST, "Test Suite").chartTitle(Chart.TEST_SET, "Test Scripts");
	}
	@BeforeClass
	@Parameters({"Browser", "URL"})
	public void instantiateWebDriver(String browser, String url){
		browser1=browser;
		if (browser.equalsIgnoreCase(ConstantsFile.FIREFOX_BROWSER)){
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		}else if (browser.equalsIgnoreCase(ConstantsFile.IE_BROWSER))
		{
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			System.setProperty("webdriver.ie.driver", "src\\test\\resource\\org\\eCom\\utils\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		
			driver.manage().window().setPosition(new Point(0,0));
			java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
			Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
			driver.manage().window().setSize(dim);
		}else if (browser.equalsIgnoreCase(ConstantsFile.CHROME_BROWSER))
		{
			driver = new ChromeDriver();
	}
		driver.get(url);
		
	}
	@AfterClass
	public void closeBrowsers(){
		driver.quit();
	}
	
	public void readDataFromSuiteExcel(String filePath, String sheetName) throws Exception{
		fInput= new FileInputStream(filePath);
		workBook= new XSSFWorkbook(fInput);
		sheet= workBook.getSheet(sheetName);
		totalRows= sheet.getLastRowNum();		
	}
	
	public void readDataFromSuiteExcel(String filePath, String sheetName,String desc) throws Exception{
		fInput= new FileInputStream(filePath);
		workBook= new XSSFWorkbook(fInput);
		sheet1= workBook.getSheet(sheetName);
		totalRows1= sheet1.getLastRowNum();		
	}
	public void readDataFromSuiteExcel(String filePath, String sheetName,String abc,String a) throws IOException{
		fInput= new FileInputStream(filePath);
		workBook= new XSSFWorkbook(fInput);
		sheet2= workBook.getSheet(sheetName);
		totalRows2= sheet2.getLastRowNum();
	}
	public void writeDataToExcel(String date, String tcName, String status) throws IOException{
		File xlsxFile = new File(ConstantsFile.overalReportPath);
		XSSFWorkbook wb = null;
		XSSFSheet sheet = null;
         Row row = null;
         Cell cell = null;
         XSSFCellStyle myStyle=null;
         XSSFColor my_background=null;
         /* We will now specify a background cell color */

         
         if(xlsxFile.exists()){
             FileInputStream fileInputStream = new FileInputStream(xlsxFile);
             wb = new XSSFWorkbook(fileInputStream);
             myStyle=wb.createCellStyle();
             myStyle.setFillPattern(XSSFCellStyle.FINE_DOTS );
//             XSSFColor my_foreground=new XSSFColor(Color.ORANGE);
             
             
             sheet = wb.getSheet("Status");
           int  rowCount = sheet.getPhysicalNumberOfRows();
           if(rowCount==0){
        	   Row row1 = sheet.createRow(0);
        	   row1.createCell(0).setCellValue("DATE");
               row1.createCell(1).setCellValue("TEST_SCRIPPT");
               row1.createCell(2).setCellValue("STATUS"); 
           }else{
        	   Row row1 = sheet.createRow(rowCount);
               row1.createCell(0).setCellValue(date);
               row1.createCell(1).setCellValue(tcName);
               if(status.equalsIgnoreCase("fail")){
            	   myStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
//            	   myStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            	   
//            	   my_background=new XSSFColor(Color.RED);
//                   myStyle.setFillBackgroundColor(my_background);
            	   cell = row1.createCell(2);
                   
            	   row1.createCell(2).setCellValue(status);
            	   cell.setCellStyle(myStyle);
                  
               }else if(status.equalsIgnoreCase("pass")){
            	   myStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
            	   cell = row1.createCell(2);
            	   row1.createCell(2).setCellValue(status);
                   cell.setCellStyle(myStyle);
               }
               
           }
           FileOutputStream fos = new FileOutputStream(ConstantsFile.overalReportPath);
           wb.write(fos);
            
         }
	}
	
	public List<String> getAllData(String fPath, String sName,int rowNum)
    {
		
		
		List<String> al=new ArrayList<String>();
        try
        {
            FileInputStream file = new FileInputStream(new File(fPath));
 
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);
 
            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheet(sName);
           row2 = sheet.getRow(0);
           row5 = sheet.getRow(rowNum);
           int colCount=row2.getLastCellNum();
           for(int i=0;i<1;i++){
        	   for(int l=0;l<colCount;l++){
        		   String headerValues=row2.getCell(l).getStringCellValue();
        		   String dataValues=row5.getCell(l).getStringCellValue();
        		   String totalValues=headerValues+"="+dataValues;
        		   al.add(totalValues);
        	   }
        		   
        	   
           }
            file.close();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
		return al;
    }
	public void waitForElementPresent(final By element, Action action, String inputText) throws Exception 
	{
		driver.manage().timeouts().implicitlyWait(60l, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 90);
		switch (action) {
		case elementClickable:
			wait.until(ExpectedConditions.elementToBeClickable(element));
			break;
		case elementVisible:
			wait.until(ExpectedConditions.visibilityOfElementLocated(element));
			break;
		case elementTextToBePresent:
			wait.until(ExpectedConditions.textToBePresentInElement(element,inputText));
			break;
		case elementAlert:
			wait.until(ExpectedConditions.titleIs(inputText));
			break;
		}
	}
	
	public void takeScreenShot(String filePath) throws IOException{
		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(filePath));
            //driver.close();
	}

	
	public void captureScreenshot(String folderName, String screenName)
	{
//		String screenShotPath = System.getProperty("user.dir")+ properties.getProperty("screenShotPath");
		
		String screenShotPath = null;
		
    	screenShotPath = screenShotPath + folderName + "\\"+ screenName + ".jpg";
  
   		File srcFile =  null;
   			srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    try {
	    	FileUtils.copyFile(srcFile, new File(screenShotPath));
		} catch (IOException e) {
			System.out.println("Taking screenshot failed due to " + e);
			e.printStackTrace();
		}
	}
	
	public void startRecording(String vFileName) throws Exception
    {    
           File file = new File("D:\\Videos");
                         
//           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//           int width = screenSize.width;
//           int height = screenSize.height;
           int width = 1100;
           int height = 700;             
           Rectangle captureSize = new Rectangle(0,0, width, height);
                          
         GraphicsConfiguration gc = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice()
            .getDefaultConfiguration();

        this.screenRecorder = new org.eCom.utils.SpecializedScreenRecorder(gc, captureSize,new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                 CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                 DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                 QualityKey, 1.0f,
                 KeyFrameIntervalKey, 15 * 60),
            new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                 FrameRateKey, Rational.valueOf(30)),
            null, file, vFileName);
       this.screenRecorder.start();
    
    }

    public void stopRecording() throws Exception
    {
      this.screenRecorder.stop();
    }
	
public void actionPerform(String locator, String Evalue,String action,List inputValue ,String dataField,String elementName) throws Exception {
		
		//objectNameOut=objectName;
		try {
			if (locator.equalsIgnoreCase("id")) 
			{
				isPresent =IsElementPresent(By.id(Evalue));
				if (isPresent){
					webelm = driver.findElement(By.id(Evalue));
				}else{
					takeScreenShot("D:\\Videos\\Click1.png");
					//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element "+elementName+" is not identified on below page. Failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
					errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
			} 
			else if (locator.equalsIgnoreCase("name"))
			{
				isPresent =IsElementPresent(By.name(Evalue));
				if (isPresent){
					webelm = driver.findElement(By.name(Evalue));
				}else{
					takeScreenShot("D:\\Videos\\Click1.png");
					//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element "+elementName+" is not identified on below page. Failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
					errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
			} 
			else if (locator.equalsIgnoreCase("xpath"))
			{
				isPresent =IsElementPresent(By.xpath(Evalue));
				if (isPresent){
					webelm = driver.findElement(By.xpath(Evalue));
				}else{
					takeScreenShot("D:\\Videos\\Click1.png");
					//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element "+elementName+" is not identified on below page. Failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
					errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
				
			} 
			else if (locator.equalsIgnoreCase("link")) 
			{
				isPresent =IsElementPresent(By.linkText(Evalue));
				if (isPresent){
					webelm = driver.findElement(By.linkText(Evalue.toString()));
				}else{
					takeScreenShot("D:\\Videos\\Click1.png");
					//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element "+elementName+" is not identified on below page. Failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
					errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
			}
			else if (locator.equalsIgnoreCase("css"))
			{
				isPresent =IsElementPresent(By.cssSelector(Evalue));
				if (isPresent){
					webelm = driver.findElement(By.cssSelector(Evalue));
				}else{
					takeScreenShot("D:\\Videos\\Click1.png");
					//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element "+elementName+" is not identified on below page. Failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
					errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
				
			}
		switch (action) {
		case "CLICK":
				
				if (isPresent){
					if(webelm.isEnabled()){
						webelm.click();
						driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
						//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Clicked on Element <u>"+elementName+"</u>");
					}else{
						takeScreenShot("D:\\Videos\\Click.png");
						//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Element is <u>"+elementName+"</u> disabled, hence failed at "+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Click1.png");
						errorSet.add("Element "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
					}
				}
			break;
		case "EDIT":
				
				if (isPresent){
					if(webelm.isEnabled()){
						for(int t=0;t<inputValue.size();t++){
							String a[]=inputValue.get(t).toString().split("=");
							if(a[0].equalsIgnoreCase(dataField)){
								getEditValue= a[1];
							}
						}
						
						webelm.sendKeys(getEditValue);
						driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
						//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Entered value in text field <u>"+elementName+"</u>");
					}else{
						takeScreenShot("D:\\Videos\\Edit.png");
						//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Text field <u>"+elementName+"</u> is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Edit.png");
						errorSet.add("Text field "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
					}
				}
			break;
		case "CLEAR":
				if (isPresent){
					if(webelm.isEnabled()){
						webelm.clear();
						driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
						//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Value cleared in text field <u>"+elementName+"</u>");
					}else{
						takeScreenShot("D:\\Videos\\Clear.png");
						//logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Text field <u>"+elementName+"</u> is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Clear.png");
						errorSet.add("Text field is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
					}
				}
			break;	
		case "SELECT":
				if (isPresent){
					if(webelm.isEnabled()){
						Select selDay = new Select(webelm);
						driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
						for(int t=0;t<inputValue.size();t++){
							String a[]=inputValue.get(t).toString().split("=");
							if(a[0].equalsIgnoreCase(dataField)){
								getDropDownValue= a[1];
							}
						}
//						List<WebElement> drpValues=selDay.getOptions();
//						for(int g=0;g<drpValues.size();g++){
//							System.out.println(drpValues.get(g));
//							if (drpValues.get(g).equals(getDropDownValue)){
//								selDay.selectByVisibleText(getDropDownValue);
//								driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
//								logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Value selected from drop down "+elementName);
//							}
//							else{
//								takeScreenShot("D:\\Videos\\Select.png");
//								logger.log(LogStatus.FAIL, "","<span class='label failure'> FAIL </span>Select drop down "+elementName+" is not present:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]), "D:\\Videos\\Select.png");
//								errorSet.add("Select drop down "+getDropDownValue+" is not present in drop down:"+elementName+": Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
//							}
//						}
						selDay.selectByVisibleText(getDropDownValue);
						driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
						//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Value selected from drop down <u>"+elementName+"</u> is <u>"+getDropDownValue+"</u>");
					}else{
						takeScreenShot("D:\\Videos\\Select.png");
						//logger.log(LogStatus.FAIL, "Image", "Image example:", "D:\\Videos\\Select.png");
						errorSet.add("Select drop down "+elementName+" is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
					}
				}
			break;	
		case "SUBMIT":
			if (isPresent){
				if(webelm.isEnabled()){
					driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
					webelm.submit();
					driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
					//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>Clicked on button <u>"+elementName+"</u>");
				}else{
					takeScreenShot("D:\\Videos\\Submit.png");
					//logger.log(LogStatus.FAIL, "Image", "Image example:", "D:\\Videos\\Submit.png");
					errorSet.add("Button "+elementName+" is not identified:: Failed at </span>"+ getLineNumber(new Exception().getStackTrace()[0]));
				}
			}
			break;
		}
		} catch (InterruptedException e1) {
			//logger.log(LogStatus.FAIL, "<span class='label failure'> FAIL </span>actionPerform failed at "+ getLineNumber(new Exception().getStackTrace()[0]));
			errorSet.add("Button is not identified:: Failed at"+ getLineNumber(new Exception().getStackTrace()[0]));
			
		}
	}

    public Boolean IsElementPresent(By locator)
    {
        //Set the timeout to something low
    	driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try
        {
            driver.findElement(locator);
            //If element is found set the timeout back and return true
            driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
           // logger.log(LogStatus.INFO, "Elemenet present");
            return true;
        }
        catch (NoSuchElementException e)
        {
            //If element isn't found, set the timeout and return false
        	driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);
        	//logger.log(LogStatus.ERROR, "Elemenet does not exist");
            return false;
        }
    }

public String getLineNumber(StackTraceElement frame) throws Exception {
	try{
//		StackTraceElement frame = new Exception().getStackTrace()[0];
	    fileName = frame.getFileName();
	    lineNumber= frame.getLineNumber();
	    methodName=frame.getMethodName();
	}catch (Exception e) {
		e.printStackTrace();
		//AssertJUnit.assertTrue(false, "Failed to Delete Projects");
	}
	return " File Name: "+fileName+" | Method Name: "+methodName+" | Line Number: "+lineNumber;
}

}
