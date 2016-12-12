package AmazonTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	private String fileName;
	List<String> displaySizes = new ArrayList<String>(); 
	List<String> modelYears = new ArrayList<String>(); 
	
	public ReadExcel(String fileName){
		this.fileName = fileName;
	}
	
	public void readFile() throws IOException{
		FileInputStream fis = new FileInputStream(fileName);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheetAt(0);
		int rowsCount = sheet.getLastRowNum();

		for(int i = 1; i <= rowsCount; i++){
			Row row = sheet.getRow(i);
			int colCounts = row.getLastCellNum();
			//System.out.println("Total Number of Cols: " + colCounts);

			for(int j = 0; j < colCounts; j ++){
				Cell cell = row.getCell(j);
				if(cell!=null){
					CellType type = cell.getCellTypeEnum(); //deprecated
					String valueString;
					if(type==CellType.NUMERIC){
						Double dcv = cell.getNumericCellValue();
						//assign it to a dcv which is a instance of Double class, which has 2016.0 in it. 
						int icv = dcv.intValue();
						//take dcv and call the intValue method and take whatever the value in this instance(double) and return it to int. 
						valueString = String.valueOf(icv);
						//this calls the valueOf method of String class(static method), take the argument of icv and return to a string. 
						
						//cell is instance of Cell, valueOf is a method of String class. 
						//take the numeric and return to a string 
					}else{
						valueString = cell.getStringCellValue();
					}
					System.out.println("[" + i + "," + j + "]=" + valueString);
					displaySizes.add(valueString);
				}	
			}
		}
	}
	
		public List<String> getDisplaysizes(){
			return this.displaySizes;
		}
	
		public List<String> getModelYears(){
			return this.modelYears;
		}
		
		public static void main (String[] args) throws IOException {
			ReadExcel myClass = new ReadExcel ("C:\\Users\\dawnw\\workspace\\AmazonTest\\TestData.xlsx");
			myClass.readFile();
			for(String eachDS: myClass.getDisplaysizes()){
				System.out.println("DS: " + eachDS + ", ");	
				for(String eachMY: myClass.getModelYears()){
					System.out.println("MY: " + eachMY);	
				}
			}
		}
}