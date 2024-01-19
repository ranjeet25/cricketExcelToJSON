import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.json.JSONArray;
import org.json.JSONObject;


public class ManipulateJSON {
	
	XSSFSheet matchDetail_sheet = null;
	XSSFSheet matchInfo_sheet = null;
	JSONObject matchDetails = null;
	
	
	// ManipulateJSON constructor
	ManipulateJSON() {
		
		 ExcelReader reader = new ExcelReader();
		
		// match Detail sheet from ExcelReader class
		 this.matchDetail_sheet = reader.excelFileOfMatchDetails();
		
		// match Info sheet from ExcelReader class
		 this.matchInfo_sheet = reader.excelFileOfMatchInfo();
		
		CreateJSON obj = new CreateJSON();
		
		// Default JSON from createJSON class
		this.matchDetails = obj.CreateMatchDetailsJSON();

	}
	
	
	// ******** Handles MATCH Infos ********
	public void handleMatchInfo() {

        
        String result = "Winner is"+ " " + matchInfo_sheet.getRow(19).getCell(2).toString() ;
        String toss_winner = matchInfo_sheet.getRow(11).getCell(2).toString();
//		String batting_first_team = matchInfo_sheet.getRow(19).getCell(2).toString();
        String player_of_the_match = matchInfo_sheet.getRow(13).getCell(2).toString();
        
        matchDetails.put("result", result );
        matchDetails.put("toss_winner", toss_winner );
        matchDetails.put("batting_first_team", "Australia" );
        matchDetails.put("player_of_the_match", player_of_the_match );
        

	}
	
	
	// ******** Handles BASIC team Infos ********
	public void teamDetails() {
		
		String team1name = matchInfo_sheet.getRow(2).getCell(2).toString();
		String team2name = matchInfo_sheet.getRow(3).getCell(2).toString();
		
		JSONObject team1 = matchDetails.getJSONObject("team1");
		JSONObject team2 = matchDetails.getJSONObject("team2");
		
		team1.put("name", team1name);
		team2.put("name", team2name);
		
	}
	

	// ******** CALCULATES Team Scores ********
	public void totalTeamScore() {
		
		int score1 = 0;
		int score2 = 0;
		Iterator<Row> rowIterator = matchDetail_sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getCell(4).getNumericCellValue() == 1) {
				score1 += row.getCell(11).getNumericCellValue() + row.getCell(12).getNumericCellValue();
			}else {
				score2 += row.getCell(11).getNumericCellValue() + row.getCell(12).getNumericCellValue();
			}
		
		}
		
		JSONObject team1 = matchDetails.getJSONObject("team1");
		JSONObject team2 = matchDetails.getJSONObject("team2");
		
		team1.put("score", score1);
		team2.put("score", score2);
		
	}
	
	
	// ******** CALCULATES Team Wickets Down ********
	public void totalTeamWicket() {

		int wickets1 = 0;
		int wickets2 = 0;
		
		Iterator<Row> rowIterator = matchDetail_sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getCell(4).getNumericCellValue() == 2 && row.getCell(18) != null) {
				wickets1++;
			}else if(row.getCell(4).getNumericCellValue() == 1 && row.getCell(18) != null)  {
				wickets2++;
			}
		}
		
		JSONObject team1 = matchDetails.getJSONObject("team1");
		JSONObject team2 = matchDetails.getJSONObject("team2");
		
		team1.put("wicket", wickets1);
		team2.put("wicket", wickets2);
		
	}
	
	
	// ******** CALCULATES Team Over Played ********
	public void teamOverPlayed() {
		
		double overs_played1 = 0.0;
		double overs_played2 = 0.0;
		
		Iterator<Row> rowIterator = matchDetail_sheet.iterator();
		rowIterator.next();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (row.getCell(4).getNumericCellValue() == 1) {
				overs_played1 = row.getCell(5).getNumericCellValue();
			}else {
				overs_played2 = row.getCell(5).getNumericCellValue();
			}
		}
		
		JSONObject team1 = matchDetails.getJSONObject("team1");
		JSONObject team2 = matchDetails.getJSONObject("team2");
		
		team1.put("over_played", overs_played1);
		team2.put("over_played", overs_played2);
	}
	
	
	
	// *********** Calculate Details of Players of Team1 ie. Australia ***********
	public void ausPlayers() {
		
		HashMap<String, JSONObject> ausPlayerMap = new HashMap<String, JSONObject>();	

		
		Iterator<Row> rowIterator = matchInfo_sheet.iterator();
		rowIterator.next();
	
		
		while(rowIterator.hasNext()) {
			
			 Row row = rowIterator.next();
			 String currentPlayer = null;
			 
			 if("player".equals(row.getCell(1).toString()) && "Australia".equals(row.getCell(2).toString())) {
			
			currentPlayer = row.getCell(3).toString();
			
			JSONObject playerScoreCard = new JSONObject();
			 
			 playerScoreCard.put("player_Name", currentPlayer); // 01
			 playerScoreCard.put("is_batted", "No");
			 playerScoreCard.put("player_run", 0);
			 playerScoreCard.put("player_bowl_faced", 0);
			 playerScoreCard.put("batting_strike_rate", 0.0);
	         playerScoreCard.put("is_out", "No");
	         playerScoreCard.put("wicket_taken", 0);
	         playerScoreCard.put("over_bowled", 0.0);
	         playerScoreCard.put("bowling_economy", 0.0);
	         
	         ausPlayerMap.put(currentPlayer,playerScoreCard );
	         
			 }
		}
 		  
		
 		  // MatchDetails Sheet
 		  
		Iterator<Row> rowIterator1 = matchDetail_sheet.iterator();
		rowIterator1.next();
		
		// starts calculating 
		while(rowIterator1.hasNext()) {
			
			Row row = rowIterator1.next();
			
			String currentPlayer = row.getCell(8).toString();
			String currentBaller = row.getCell(10).toString();
			
			if(row.getCell(4).getNumericCellValue() == 2) {
				currentPlayer = currentBaller;
			}
			
			JSONObject currentBatObj = ausPlayerMap.get(currentPlayer);
			//System.out.println(currentBatObj);
			
			int player_run = currentBatObj.getInt("player_run");
			int player_bowl_faced = currentBatObj.getInt("player_bowl_faced");
			float batting_strike_rate = currentBatObj.getInt("batting_strike_rate");
			String is_batted = currentBatObj.getString("is_batted");
			String is_out = currentBatObj.getString("is_out");
			int wicket_taken = currentBatObj.getInt("wicket_taken");
			float over_bowled = currentBatObj.getInt("over_bowled");
			float bowling_economy = currentBatObj.getInt("bowling_economy");
			
			
			// ****** Calculate Details of Team1 BatsMan ********************************
			if(row.getCell(4).getNumericCellValue() == 1) {
				
				currentBatObj.put("player_run", player_run + row.getCell(11).getNumericCellValue()); // 02
				
				// IS Batted
				currentBatObj.put("is_batted", "Yes");  // 03 
				
				// Calculate ball Faced
				currentBatObj.put("player_bowl_faced", ++player_bowl_faced);  // 04
				
				if(row.getCell(19) != null && row.getCell(19).getStringCellValue().equals(currentPlayer)) { // 05
					currentBatObj.put("is_out", "Yes");
				}
				
				// Get Batting Strike Rate    // 06
				currentBatObj.put("batting_strike_rate", (player_run*100)/player_bowl_faced);
			
				
			// ****** Calculate Details of Team1 Bowlers ********************************
			}else { 
					
				// Wicket Taken By Player
				if(row.getCell(18) != null) {
					currentBatObj.put("wicket_taken", ++wicket_taken);  // 07
				}
				
				// Over Bowled || calculating ball bowled not OVERS
				currentBatObj.put("over_bowled", ++over_bowled);  //  08 
				
				
				// Bowling Economy || calculating RUN give by bowler not economy
				currentBatObj.put("bowling_economy", bowling_economy +  row.getCell(11).getNumericCellValue()+ row.getCell(12).getNumericCellValue());  // 09
			
			}
			

			ausPlayerMap.put(currentPlayer,currentBatObj);
 
		}
		
		
		JSONObject aus = matchDetails.getJSONObject("team1");
		JSONArray ausPlayers = aus.getJSONArray("score_card");
		
		for(JSONObject obj :ausPlayerMap.values() ){
			
			//******** Convert ball bowled and run give by bolwer to OVER AND ECONOMY ********
			if(obj.getFloat("over_bowled")>0) {
			obj.put("over_bowled", obj.getFloat("over_bowled")/6);
			obj.put("bowling_economy", obj.getFloat("bowling_economy")/obj.getFloat("over_bowled"));
			}
			
			ausPlayers.put(obj);
		}

	}
	

	// *********** Calculate Details of Players of Team2 ie. EngLand ***********
	public void engPlayers() {
		
		HashMap<String, JSONObject> engPlayerMap = new HashMap<String, JSONObject>();	
		Iterator<Row> rowIterator = matchInfo_sheet.iterator();
		
		rowIterator.next();
	
		
		while(rowIterator.hasNext()) {
			
			 Row row = rowIterator.next();
			 String currentPlayer = null;
			 
			 if("player".equals(row.getCell(1).toString()) && "England".equals(row.getCell(2).toString())) {
			
			currentPlayer = row.getCell(3).toString();
			
			JSONObject playerScoreCard = new JSONObject();
			 
			 playerScoreCard.put("player_Name", currentPlayer); // 01
			 playerScoreCard.put("is_batted", "No");
			 playerScoreCard.put("player_run", 0);
			 playerScoreCard.put("player_bowl_faced", 0);
			 playerScoreCard.put("batting_strike_rate", 0.0);
	         playerScoreCard.put("is_out", "No");
	         playerScoreCard.put("wicket_taken", 0);
	         playerScoreCard.put("over_bowled", 0.0);
	         playerScoreCard.put("bowling_economy", 0.0);
	         
	         engPlayerMap.put(currentPlayer,playerScoreCard );
	         
			 }
		}
 		  
		
 		// MatchDetails Sheet
		Iterator<Row> rowIterator1 = matchDetail_sheet.iterator();
		rowIterator1.next();
		
		// starts calculating 
		while(rowIterator1.hasNext()) {
			
			Row row = rowIterator1.next();
			
			String currentPlayer = row.getCell(8).toString();
			String currentBaller = row.getCell(10).toString();
			
			if(row.getCell(4).getNumericCellValue() == 1) {
				currentPlayer = currentBaller;
			}
			
			JSONObject currentBatObj = engPlayerMap.get(currentPlayer);
			//System.out.println(currentBatObj);
			
			int player_run = currentBatObj.getInt("player_run");
			int player_bowl_faced = currentBatObj.getInt("player_bowl_faced");
			float batting_strike_rate = currentBatObj.getInt("batting_strike_rate");
			String is_batted = currentBatObj.getString("is_batted");
			String is_out = currentBatObj.getString("is_out");
			int wicket_taken = currentBatObj.getInt("wicket_taken");
			float over_bowled = currentBatObj.getInt("over_bowled");
			float bowling_economy = currentBatObj.getInt("bowling_economy");
			
			
			// ****** Calculate Details of Team1 BatsMan ********************************
			if(row.getCell(4).getNumericCellValue() == 2) {
				
				currentBatObj.put("player_run", player_run + row.getCell(11).getNumericCellValue()); // 02
				
				// IS Batted  // 03 
				currentBatObj.put("is_batted", "Yes");  
				
				// Calculate ball Faced		 // 04				
				currentBatObj.put("player_bowl_faced", ++player_bowl_faced);  
				
				// Calculate ISout 		// 05					
				if(row.getCell(19) != null && row.getCell(19).getStringCellValue().equals(currentPlayer)) { 
					currentBatObj.put("is_out", "Yes");
				}
				
				// Get Batting Strike Rate    // 06
				currentBatObj.put("batting_strike_rate", (player_run*100)/player_bowl_faced);
			
				
			// ****** Calculate Details of Team1 Bowlers ********************************
			}else { 
					
				// Wicket Taken By Player		// 07
				if(row.getCell(18) != null) {
					currentBatObj.put("wicket_taken", ++wicket_taken);  
				}
				
				// Over Bowled || calculating ball bowled not OVERS  //  08 
				currentBatObj.put("over_bowled", ++over_bowled);  
				
				
				// Bowling Economy || calculating RUN give by bowler not economy // 09
				currentBatObj.put("bowling_economy", bowling_economy +  row.getCell(11).getNumericCellValue()+ row.getCell(12).getNumericCellValue());  
			
			}
			

			engPlayerMap.put(currentPlayer,currentBatObj);
 
		}
		
		
		JSONObject eng = matchDetails.getJSONObject("team2");
		JSONArray engPlayers = eng.getJSONArray("score_card");
		
		for(JSONObject obj :engPlayerMap.values() ){
			
			//******** Convert ball bowled and run give by bolwer to OVER AND ECONOMY ********
			if(obj.getFloat("over_bowled")>0) {
			obj.put("over_bowled", obj.getFloat("over_bowled")/6);
			obj.put("bowling_economy", obj.getFloat("bowling_economy")/obj.getFloat("over_bowled"));
			}
			
			engPlayers.put(obj);
		}

	}
		
		

// 	**************** Print JSON and Save ****************
	
	public void showJSON() {
		
		 JSONObject json = new JSONObject();
		 json.put("MatchDetails", matchDetails);
		 
		 String match = json.toString();
		 
	     System.out.println(match);
	     
	     String filePath = "C:\\Users\\Ranjeet Saw\\Downloads\\output.json";
	     
	     try {
	         // Create a FileWriter
	         FileWriter fileWriter = new FileWriter(filePath);

	         // Write the JSON string to the file
	         fileWriter.write(match);

	         // Close the FileWriter
	         fileWriter.close();

	         System.out.println("JSON saved to file successfully!");
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	     
	}
	

}
