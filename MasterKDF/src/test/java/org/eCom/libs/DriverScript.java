package org.eCom.libs;

import java.util.Date;
import org.eCom.utils.ConstantsFile;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

public class DriverScript extends BaseTest{
	public DriverScript() throws Exception{
		readDataFromSuiteExcel(ConstantsFile.Master_DATA_SHEET, "TESTSUITE");
	}
	@Test
	public void mainMethod() throws Exception{
		
		for(int i=1;i<=totalRows;i++){
			if(sheet.getRow(i).getCell(3).getStringCellValue().equalsIgnoreCase("YES")){
				startRecording(sheet.getRow(i).getCell(1).getStringCellValue()+"_"+i+"_"+browser1);
				readDataFromSuiteExcel(ConstantsFile.Test_DATA_SHEET, sheet.getRow(i).getCell(0).getStringCellValue(),sheet.getRow(i).getCell(1).getStringCellValue(),"");
				for(int k=1;k<=totalRows2;k++){
					logger.startTest(sheet.getRow(i).getCell(1).getStringCellValue()+"_"+k+" Executed on <u>"+browser1+"</u>");
					//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>"+ browser1+" launched successfully");
					
					row2 = sheet2.getRow(k);
					al1=getAllData(ConstantsFile.Test_DATA_SHEET,sheet.getRow(i).getCell(0).getStringCellValue(),k);
					readDataFromSuiteExcel(ConstantsFile.Master_DATA_SHEET, sheet.getRow(i).getCell(0).getStringCellValue(),sheet.getRow(i).getCell(1).getStringCellValue());
					for(int j=1;j<=totalRows1;j++){
 						row = sheet1.getRow(j);		
						switch (row.getCell(3).getStringCellValue()) {
						case "clickElement":
							actionPerform(row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue(), "CLICK", al1,row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue());
							break;
						case "sendkeys":
							actionPerform(row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue(), "EDIT", al1,row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue());
							break;
						case "selectOption":
							actionPerform(row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue(), "SELECT", al1,row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue());
							break;
						case "submit":
							actionPerform(row.getCell(4).getStringCellValue(),row.getCell(5).getStringCellValue(), "SUBMIT", al1,row.getCell(6).getStringCellValue(),row.getCell(7).getStringCellValue());
							break;
						}
					}
				}
				
//				break;
			}
			
			
//			homePage();
			//Validate the error.add
			if(errorSet.size()!=0){
				writeDataToExcel(dateFormat.format(new Date()),sheet.getRow(i).getCell(1).getStringCellValue(),"FAIL");
				errorSet.clear();
				//logger.log(LogStatus.FAIL,"<span class='label failure'> FAIL </span>Overal result of the script failed");
			}else{
				writeDataToExcel(dateFormat.format(new Date()),sheet.getRow(i).getCell(1).getStringCellValue(),"PASS");
				//logger.log(LogStatus.PASS, "<span class='label success'> PASS </span>All test steps have passed");
			}
			//logger.endTest();
			stopRecording();
		}
		
		
	}
	
}
