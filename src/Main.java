import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) {
		
		// Drivers
		
		ManipulateJSON resultJSON  = new ManipulateJSON();
		resultJSON.handleMatchInfo();
	
		resultJSON.teamDetails();
		resultJSON.totalTeamScore();
		resultJSON.totalTeamWicket();
		resultJSON.teamOverPlayed();
		
		resultJSON.ausPlayers();
		resultJSON.engPlayers();

		resultJSON.showJSON();

	}
}
