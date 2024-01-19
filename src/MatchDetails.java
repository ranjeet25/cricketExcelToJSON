
public class MatchDetails {
	
	private int match_id;
	private String season;	
	private String start_date;
	private String venue;
	private int innings;
	private float ball;
	private String batting_team;
	private String bowling_team;
	private String striker;
	private String non_striker;
	private int runs_off_bat;
	private int extras;
	private int wides;
	private int noballs;
	private int byes;
	private int legbyes;
	private int penalty;
	private String wicket_type;
	private String player_dismissed;
	private String other_wicket_type;
	private String other_player_dismissed;
	
	
	 // Constructor
    public MatchDetails(int match_id, String season, String start_date, String venue, int innings, float ball,
                        String batting_team, String bowling_team, String striker, String non_striker,
                        int runs_off_bat, int extras, int wides, int noballs, int byes, int legbyes,
                        int penalty, String wicket_type, String player_dismissed,
                        String other_wicket_type, String other_player_dismissed)
    {
        this.match_id = match_id;
        this.season = season;
        this.start_date = start_date;
        this.venue = venue;
        this.innings = innings;
        this.ball = ball;
        this.batting_team = batting_team;
        this.bowling_team = bowling_team;
        this.striker = striker;
        this.non_striker = non_striker;
        this.runs_off_bat = runs_off_bat;
        this.extras = extras;
        this.wides = wides;
        this.noballs = noballs;
        this.byes = byes;
        this.legbyes = legbyes;
        this.penalty = penalty;
        this.wicket_type = wicket_type;
        this.player_dismissed = player_dismissed;
        this.other_wicket_type = other_wicket_type;
        this.other_player_dismissed = other_player_dismissed;
    }
    
    // Getter and Setter methods 

    public int getMatch_id() {
        return match_id;
    }

	


}
