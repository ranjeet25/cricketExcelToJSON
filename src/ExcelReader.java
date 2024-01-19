import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.json.JSONObject;

public class ExcelReader {
	
	// Method to READ MATCH DETAIL EXCEL FILE

    public XSSFSheet excelFileOfMatchDetails()  {
    	
    	XSSFSheet matchDetail_sheet = null;
    	
        try {
            // Specify the path to your Excel file
            String excelFilePathofMatchDetails = "C:\\Users\\Ranjeet Saw\\eclipse-workspace\\DHDS_Projects\\CricketJSON\\JSONproject\\Resources\\matchDetails.xlsx";
           
            // Create a FileInputStream to read the Excel file
            FileInputStream inputStream = new FileInputStream(new File(excelFilePathofMatchDetails));

            // Create a workbook object
            XSSFWorkbook matchDetail_workbook = new XSSFWorkbook(inputStream);

            // Get the first sheet of the workbook
            matchDetail_sheet = matchDetail_workbook.getSheetAt(0);
            
            matchDetail_workbook.close();
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return matchDetail_sheet;
    }
    
    
    
    
    // Method to READ MATCH INFO EXCEL FILE
    
    public XSSFSheet excelFileOfMatchInfo()  {
    	
    	XSSFSheet matchInfo_sheet = null;
    	
        try {
            // Specify the path to your Excel file
            String excelFilePathofMatchInfo = "C:\\Users\\Ranjeet Saw\\eclipse-workspace\\DHDS_Projects\\CricketJSON\\JSONproject\\Resources\\match_info.xlsx";
            
            // Create a FileInputStream to read the Excel file
            FileInputStream inputStream = new FileInputStream(new File(excelFilePathofMatchInfo));

            // Create a workbook object
            XSSFWorkbook matchInfo_workbook = new XSSFWorkbook(inputStream);

            // Get the first sheet of the workbook
            matchInfo_sheet = matchInfo_workbook.getSheetAt(0);
            
            matchInfo_workbook.close();           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return matchInfo_sheet;
    }
    
    
    
}
