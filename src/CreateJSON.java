import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class CreateJSON {
	
	
	JSONObject matchDetails = new JSONObject();
	
	public JSONObject CreateMatchDetailsJSON() {

		matchDetails.put("result", "India");
		matchDetails.put("toss_winner", "India");
		matchDetails.put("batting_first_team", "India");
		matchDetails.put("player_of_the_match", "India");
		matchDetails.put("team1", CreateTeam1JSON());
		matchDetails.put("team2", CreateTeam2JSON());
		
		return matchDetails;
		
	}
	
	
	public JSONObject CreateTeam1JSON() {
		
		JSONObject teamDetails = new JSONObject();
		
		
		teamDetails.put("name", "");
		teamDetails.put("score", "");
		teamDetails.put("wicket", "");
		teamDetails.put("over_played", "");
		teamDetails.put("score_card", scoreCard());
		
		return teamDetails;
		
	}
	
	
	public JSONObject CreateTeam2JSON() {
		
		JSONObject teamDetails = new JSONObject();
		
		teamDetails.put("name", "");
		teamDetails.put("score", "");
		teamDetails.put("wicket", "");
		teamDetails.put("over_played", "");
		teamDetails.put("score_card", scoreCard());
		
		return teamDetails;
		
	}
	

	public JSONArray scoreCard() {
		
        JSONArray scorecardArray = new JSONArray();

        return scorecardArray;
    }
		
	


}
