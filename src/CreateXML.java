import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.HashMap;

public class CreateXML {
	
	static Document doc = null;
	static HashMap<String, String> matchInfoMap = new HashMap<>();
	static HashMap<String, String> team1Map = new HashMap<>();
	static HashMap<String, String> team2Map = new HashMap<>();
	


	 public static void main(String[] args)
	            throws ParserConfigurationException, TransformerException {

	        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	        
	                // root elements
	        doc = docBuilder.newDocument();
	        Element rootElement = doc.createElement("root");
	        doc.appendChild(rootElement);

	        Element matchdetails = doc.createElement("matchdetails");
	        rootElement.appendChild(matchdetails);
	        
	        
	        // Create object of ManipulateJSON
	        ManipulateJSON detailsObj = new ManipulateJSON();
	        
	    	detailsObj.handleMatchInfo();
	    	
	    	detailsObj.teamDetails();
	    	detailsObj.totalTeamScore();
	    	detailsObj.totalTeamWicket();
	    	detailsObj.teamOverPlayed();
	    	matchInfoMap = detailsObj.matchInfoMap();
	    	team1Map = detailsObj.team1Map();
	    	team2Map = detailsObj.team2Map();
	    	
	    	 // Create object of ManipulateJSON
	    	
	        Element result = doc.createElement("result");
	        result.setTextContent(matchInfoMap.get("result"));
	        
	        Element toss_winner = doc.createElement("toss_winner");
	        toss_winner.setTextContent(matchInfoMap.get("toss_winner"));
	        
	        Element batting_first_team = doc.createElement("batting_first_team");
	        batting_first_team.setTextContent(matchInfoMap.get("batting_first_team"));
	        
	        Element player_of_the_match = doc.createElement("player_of_the_match");
	        player_of_the_match.setTextContent(matchInfoMap.get("player_of_the_match"));
	        
	        matchdetails.appendChild(result);
	        matchdetails.appendChild(toss_winner);
	        matchdetails.appendChild(batting_first_team);
	        matchdetails.appendChild(player_of_the_match);

	        matchdetails.appendChild(team1());
	        matchdetails.appendChild(team2());
	        
	        

	        // write dom document to a file
	        try (FileOutputStream output =
	                     new FileOutputStream("C:\\Users\\Ranjeet Saw\\Downloads\\output.xml")) {
	            writeXml(doc, output);
	            System.out.println("XML file created SucessFully");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }

	    // write doc to output stream
	    private static void writeXml(Document doc,
	                                 OutputStream output)
	            throws TransformerException {

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(output);

	        transformer.transform(source, result);

	    }
	    
	    
	    // Team 1 Element creation
	    public static Element team1() {
	    	
	    	// Create object of ManipulateJSON
	    	

	        
	        Element team1 = doc.createElement("team1");
	        
	        Element name = doc.createElement("name");
	        name.setTextContent(team1Map.get("name"));
	        
	        Element score = doc.createElement("score");
	        score.setTextContent(team1Map.get("score"));
	        
	        Element wicket = doc.createElement("wicket");
	        wicket.setTextContent(team1Map.get("wicket"));
	        
	        Element over_played = doc.createElement("over_played");
	        over_played.setTextContent(team1Map.get("over_played"));
	        
	        team1.appendChild(name);
	        team1.appendChild(score);
	        team1.appendChild(wicket);
	        team1.appendChild(over_played);
	        
	        team1.appendChild(score_card(true));
	        
	        return team1;
	    	
	    }
	    
	    
	 // Team 2 Element creation
	    public static Element team2() {
	    	
	    	// Create object of ManipulateJSON
	    	

	        
	        Element team2 = doc.createElement("team2");
	        
	        Element name = doc.createElement("name");
	        name.setTextContent(team2Map.get("name"));
	        
	        Element score = doc.createElement("score");
	        score.setTextContent(team2Map.get("score"));
	        
	        Element wicket = doc.createElement("wicket");
	        wicket.setTextContent(team2Map.get("wicket"));
	        
	        Element over_played = doc.createElement("over_played");
	        over_played.setTextContent(team2Map.get("over_played"));
	        
	        team2.appendChild(name);
	        team2.appendChild(score);
	        team2.appendChild(wicket);
	        team2.appendChild(over_played);
	        
	        team2.appendChild(score_card(false));
	        
	        return team2;
	    	
	    }
	    
	    
	    
	    // common Score Card 
	    public static Element score_card(boolean b) {
	    	
	        ManipulateJSON players = new ManipulateJSON();
	        
	        HashMap<String, JSONObject> PlayerMap = new HashMap<String, JSONObject>();
	        PlayerMap = players.ausPlayers();
	        
	      
	        if(b) {
	        	PlayerMap = players.ausPlayers();
	        }else {
	        	PlayerMap = players.engPlayers();
	        }
	        
	        Element score_card = doc.createElement("score_card");
	        
	        
	        for(JSONObject obj : PlayerMap.values()) {
	        	
	        	Element players_detail = doc.createElement("player");
	        	
	        	Element player_Name = doc.createElement("player_Name");
	  	        player_Name.setTextContent(obj.getString("player_Name"));
	  	        
	  	        Element is_batted = doc.createElement("is_batted");
	  	        is_batted.setTextContent(obj.getString("is_batted"));
	  	        
	  	        Element player_run = doc.createElement("player_run");
	  	        int run = obj.getInt("player_run");
	  	        String run_toString = run + "";
	  	        player_run.setTextContent(run_toString);
	  	        
	  	        Element player_bowl_faced = doc.createElement("player_bowl_faced");
	  	        int bowl_faced = obj.getInt("player_bowl_faced");
	  	        String bowl_faced_toString = bowl_faced + "";
	  	        player_bowl_faced.setTextContent(bowl_faced_toString);
	  	        
	  	        Element batting_strike_rate = doc.createElement("batting_strike_rate");
	  	        float strike_rate = obj.getFloat("batting_strike_rate");
	  	        String strike_rate_toString = strike_rate + "";
	  	        batting_strike_rate.setTextContent(strike_rate_toString);
	  	        
	  	        Element is_out = doc.createElement("is_out");
	  	        is_out.setTextContent(obj.getString("is_out"));
	  	        
	  	        Element wicket_taken = doc.createElement("wicket_taken");
	  	        int wicket = obj.getInt("wicket_taken");
	  	        String wicket_toString = wicket + "";
	  	        wicket_taken.setTextContent(wicket_toString);
	  	        
	  	        Element over_bowled = doc.createElement("over_bowled");
	  	        float over = obj.getFloat("over_bowled");
	  	        String over_toString = over + "";
	  	        over_bowled.setTextContent(over_toString);
	  	        
	  	        Element bowling_economy = doc.createElement("bowling_economy");
	  	        float economy = obj.getFloat("bowling_economy");
	  	        String economy_toString = economy + "";
	  	        bowling_economy.setTextContent(economy_toString);
	  	        
	  	        players_detail.appendChild(player_Name);
	  	        players_detail.appendChild(is_batted);
	  	        
	  	        players_detail.appendChild(player_run);
	  	        players_detail.appendChild(player_bowl_faced);
	  	        players_detail.appendChild(batting_strike_rate);
	  	  
	  	        players_detail.appendChild(is_out);
	  	        players_detail.appendChild(wicket_taken);
	  	        players_detail.appendChild(over_bowled);
	  	        players_detail.appendChild(bowling_economy);
	  	      	
	  	        score_card.appendChild(players_detail);
	  	      
	        }
	    	
	        
	        return score_card;
	    	
	    }

}
