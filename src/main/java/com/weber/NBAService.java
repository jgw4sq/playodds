package job;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/NBAService")
public class NBAService {

	
	
	@GET
	  @Produces("application/json")
	  public Response todaysGames() throws Exception {
		Statement stmt =null;
		
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
		String sql = "Select * From Games ";
		ResultSet rs = stmt.executeQuery(sql);
		String returnString = "";
		JSONArray array= new JSONArray();
		ResultSet set = null;
		while(rs.next()){
			JSONObject object = new JSONObject();
			String homeTeam = rs.getString("homeTeam");
			String awayTeam = rs.getString("awayTeam");
			int id = rs.getInt("id");
			String spread = rs.getString("spread");
			boolean completed = rs.getBoolean("completed");
			boolean started = rs.getBoolean("started");
			String date = rs.getString("date");
			String displayClock = rs.getString("displayClock");
			int homeTeamScore = rs.getInt("homeTeamScore");
			int awayTeamScore = rs.getInt("awayTeamScore");
			String gameTime = rs.getString("gameTime");
			int homeTeamId = rs.getInt("homeTeamId");
			int awayTeamId = rs.getInt("awayTeamId");
		
			object.put("homeTeam", homeTeam);
			object.put("awayTeam", awayTeam);
			object.put("id", id);
			object.put("spread", spread);
			object.put("completed", completed);
			object.put("started", started);
			object.put("date", date);
			object.put("displayClock", displayClock);
			object.put("homeTeamScore", homeTeamScore);
			object.put("awayTeamScore", awayTeamScore);
			object.put("gameTime", gameTime);
			object.put("homeTeamId", homeTeamId);
			object.put("awayTeamId", awayTeamId);
			array.put(object);

		
		
		}
			
			
		return Response.status(200).entity(array.toString()).build();
		
		/**
		Date date = new Date();
		String dateString = date.getYear()+1900+""+String.format("%02d",date.getMonth()+1)+String.format("%02d",date.getDate());
		String fullHtml= getHTML("http://www.espn.com/nba/scoreboard");
		String [] split = fullHtml.split(";window.espn.scoreboardSettings");
		//System.out.println("Split 0: "+split[0]);
		String [] goodStuff = split[0].split("window.espn.scoreboardData 	=");
		//System.out.println("Length: "+goodStuff.length);
		String actualGoodStuff = goodStuff[1].trim();
		return Response.status(200).entity(actualGoodStuff).build();
		*/
	  }
	
	
	@Path("{date}")
	  @GET
	  @Produces("application/json")
	  public Response gamesFromDate(@PathParam("date") String date) throws Exception {

		Date date1 = new Date();
		String year = date.substring(0,4);
		System.out.println(year);
		String month = date.substring(4,6);
		System.out.println(month);

		String day = date.substring(6);
		System.out.println(day);

		date1.setYear(Integer.parseInt(year)-1900);
		date1.setMonth(Integer.parseInt(month)-1);
		date1.setDate(Integer.parseInt(day));
		String dateString = date1.getYear()+1900+""+String.format("%02d",date1.getMonth()+1)+String.format("%02d",date1.getDate());
		String fullHtml= getHTML("http://www.espn.com/nba/scoreboard/_/date/"+dateString);
		System.out.println(dateString);
		String [] split = fullHtml.split(";window.espn.scoreboardSettings");
		System.out.println(split[0]);
		System.out.println(split[1]);
		//System.out.println("Split 0: "+split[0]);
		String [] goodStuff = split[0].split("window.espn.scoreboardData 	=");
		//System.out.println("Length: "+goodStuff.length);
		System.out.println(goodStuff[1]);
		String actualGoodStuff = goodStuff[1].trim();
		return Response.status(200).entity(actualGoodStuff).build();
		
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
