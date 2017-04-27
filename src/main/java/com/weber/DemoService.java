package job;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/DemoService")
public class DemoService {

	
	
	@GET
	  @Produces("application/json")
	  public Response convertFtoC() throws Exception {
		Date date = new Date();
		String dateString = date.getYear()+1900+""+String.format("%02d",date.getMonth()+1)+String.format("%02d",date.getDate());
		String fullHtml= getHTML("http://www.espn.com/mens-college-basketball/scoreboard");
		String [] split = fullHtml.split(";window.espn.scoreboardSettings");
		//System.out.println("Split 0: "+split[0]);
		String [] goodStuff = split[0].split("window.espn.scoreboardData 	=");
		//System.out.println("Length: "+goodStuff.length);
		String actualGoodStuff = goodStuff[1].trim();
		return Response.status(200).entity(actualGoodStuff).build();
	  }
	
	
	@Path("{instruction}")
	  @GET
	  @Produces("application/json")
	  public Response signUpUser(@PathParam("instruction") String instruction) throws Exception {

		Statement stmt =null;
		JSONObject object = new JSONObject();
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql ="";
			
			if(instruction.equals("createGame")){
				Competition comp = new Competition();
				Competitor home = new Competitor();
				Competitor away = new Competitor();
				home.setAbbreviation("UVA");
				away.setAbbreviation("VT");
				home.setName("Virginia Cavaliers");
				away.setName("Virginia Tech Hokies");
				home.setScore(0);
				away.setScore(0);
				home.setTotalRecord("100-0");
				away.setTotalRecord("0-100");
				comp.setStarted(false);
				comp.setCompleted(false);
				comp.setAwayCompetitor(away);
				comp.setHomeCompetitor(home);
				comp.setId(1111);
				comp.setSpread("UVA -200");
				comp.setDisplayclock("0:00");
				comp.setGameTime("4/27 - 3:00 PM EDT");
				comp.setVenue("John Paul Jones Arena");
				
				
				
				sql = "INSERT INTO Games (homeTeam,awayTeam,id,spread,completed,date,displayClock,homeTeamScore,awayTeamScore,gameTime,homeTeamAbbreviation,awayTeamAbbreviation,started,homeTeamTotalRecord,homeTeamHomeRecord,homeTeamAwayRecord,awayTeamTotalRecord,awayTeamHomeRecord,awayTeamAwayRecord,gameType,venue) VALUES ('"+comp.getHomeCompetitor().getName()+"','"+comp.getAwayCompetitor().getName()+"',"+comp.getId()+",'"+comp.getSpread()+"',"+comp.isCompleted()+",'"+comp.getDate()+"','"+comp.getDisplayclock()+"',"+comp.getHomeCompetitor().getScore()+","+comp.getAwayCompetitor().getScore()+",'"+comp.getGameTime()+"','"+comp.getHomeCompetitor().getAbbreviation()+"','"+comp.getAwayCompetitor().getAbbreviation()+"',"+comp.isStarted()+",'"+comp.getHomeCompetitor().getTotalRecord()+"','"+comp.getHomeCompetitor().getHomeRecord()+"','"+comp.getHomeCompetitor().getAwayRecord()+"','"+comp.getAwayCompetitor().getTotalRecord()+"','"+comp.getAwayCompetitor().getHomeRecord()+"','"+comp.getAwayCompetitor().getAwayRecord()+"','MLB','"+comp.getVenue()+"')";
				stmt.execute(sql);
				
				
				
			}
					
		

	
			return null;
	
}
	
	public static String getHTML(String urlToRead) throws Exception {
	    StringBuilder result = new StringBuilder();
	    URL url = new URL(urlToRead);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;

	    while ((line = rd.readLine()) != null) {
	  	  //System.out.println("hello");
	  	  if(line.contains("window.espn.scoreboardData")){
	  		 // System.out.println(line);
		         result.append(line);

	  	  }
	    }
	    rd.close();
	    return result.toString();
	 }
	
}
