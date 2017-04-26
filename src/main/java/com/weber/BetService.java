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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/BetService")
public class BetService {

	
	
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
	
	
	@Path("{email}/{gameId}/{teamName}/{pointsToRisk}/{pointsToWin}/{gameType}/{odds}/{awayTeamAbbreviation}/{homeTeamAbbreviation}")
	  @GET
	  @Produces("application/json")
	  public Response signUpUser(@PathParam("email") String email,@PathParam("gameId") String gameId,@PathParam("teamName") String teamName,@PathParam("pointsToRisk") String pointsToRisk,@PathParam("pointsToWin") String pointsToWin,@PathParam("gameType") String gameType,@PathParam("odds") String odds,@PathParam("awayTeamAbbreviation") String awayTeamAbbreviation,@PathParam("homeTeamAbbreviation") String homeTeamAbbreviation ) throws Exception {
		JSONObject object = new JSONObject();

		try{
		int intGameId = Integer.parseInt(gameId);
		double intPointsToRisk = Double.parseDouble(pointsToRisk);
		double intPointsToWin = Double.parseDouble(pointsToWin);
		Statement stmt =null;
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql = "INSERT INTO Bets (email,gameId,teamName,pointsToRisk,pointsToWin,gameType,odds,awayTeamAbbreviation,homeTeamAbbreviation) VALUES ('"+email+"',"+intGameId+",'"+teamName+"',"+intPointsToRisk+","+intPointsToWin+",'"+gameType+"','"+odds+"','"+awayTeamAbbreviation+"','"+homeTeamAbbreviation+"')";
			int rs = stmt.executeUpdate(sql);
			if(rs>0){
				sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
				ResultSet rs1 = stmt.executeQuery(sql);
				double balance = 200.0;
				double used =0.0;
				double available= 0.0;
				if(rs1.next()){
					 balance = rs1.getDouble("accountBalance");
					used = rs1.getDouble("usedBalance");
					available = rs1.getDouble("availableBalance");
					available-= intPointsToRisk;
					used += intPointsToRisk;
					
				}
				sql = "UPDATE STOCKUSERS SET availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
				rs = stmt.executeUpdate(sql);
				if (rs>0){
					object.put("result", "success");
					 sql = "SELECT * FROM STOCKUSERS WHERE email ='"+email+"'";
					 rs1 = stmt.executeQuery(sql);
					
					if(rs1.next()){
						//user already exists
						object.put("result", "success");
						JSONObject user = new JSONObject();
						user.put("email", rs1.getString("email"));
						user.put("username", rs1.getString("username"));
						user.put("password", rs1.getString("password"));
						user.put("accountBalance", rs1.getDouble("accountBalance"));

						user.put("availableBalance", rs1.getDouble("availableBalance"));

						user.put("usedBalance", rs1.getDouble("usedBalance"));
						user.put("firstName", rs1.getString("firstName"));
						user.put("lastName", rs1.getString("lastName"));
						object.put("user", user);

				}
				}


				
				
				
			}else{
				object.put("result", "error");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("result","error");
		}
			
			
			
			return Response.status(200).entity(object.toString()).build();

		
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

	@Path("{email}")
	  @GET
	  @Produces("application/json")
	  public Response signUpUser(@PathParam("email") String email) throws Exception {
		JSONObject object = new JSONObject();

		try{
	
		Statement stmt =null;
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql = "SELECT * FROM Bets WHERE email='"+email+"'";
			ResultSet rs = stmt.executeQuery(sql);
			JSONArray array = new JSONArray();
			while(rs.next()){
				JSONObject betObject = new JSONObject();
				int gameId = rs.getInt("gameId");
				String teamName = rs.getString("teamName");
				double pointsToRisk = rs.getDouble("pointsToRisk");
				double pointsToWin = rs.getDouble("pointsToWin");
				String gameType = rs.getString("gameType");
				String odds = rs.getString("odds");
				boolean gameComplete = rs.getBoolean("gameComplete");
				int homeTeamScore = rs.getInt("homeTeamScore");
				int awayTeamScore = rs.getInt("awayTeamScore");
				String homeTeamAbbreviation = rs.getString("homeTeamAbbreviation");
				String awayTeamAbbreviation = rs.getString("awayTeamAbbreviation");
				betObject.put("gameId", gameId);
				betObject.put("teamName", teamName);
				betObject.put("pointsToRisk", pointsToRisk);
				betObject.put("pointsToWin", pointsToWin);
				betObject.put("gameType", gameType);
				betObject.put("odds", odds);
				betObject.put("gameComplete", gameComplete);
				betObject.put("homeTeamScore", homeTeamScore);
				betObject.put("awayTeamScore", awayTeamScore);
				betObject.put("homeTeamAbbreviation", homeTeamAbbreviation);

				betObject.put("awayTeamAbbreviation", awayTeamAbbreviation);
				
				sql = "SELECT * FROM Games where id="+gameId;
				Statement stmt2 = con.createStatement();
				ResultSet rs2= stmt2.executeQuery(sql);
				if(rs2.next()){
					JSONObject gameObject = new JSONObject();
					String homeTeam = rs2.getString("homeTeam");
					String awayTeam = rs2.getString("awayTeam");
					int id = rs2.getInt("id");
					String spread = rs2.getString("spread");
					boolean completed = rs2.getBoolean("completed");
					boolean started = rs2.getBoolean("started");
					String date = rs2.getString("date");
					String displayClock = rs2.getString("displayClock");
					int gamehomeTeamScore = rs2.getInt("homeTeamScore");
					int gameawayTeamScore = rs2.getInt("awayTeamScore");
					String gameTime = rs2.getString("gameTime");
					int homeTeamId = rs2.getInt("homeTeamId");
					int awayTeamId = rs2.getInt("awayTeamId");
					String gamehomeTeamAbbreviation = rs2.getString("homeTeamAbbreviation");

					String gameawayTeamAbbreviation = rs2.getString("awayTeamAbbreviation");
					String homeTeamTotalRecord = rs2.getString("homeTeamTotalRecord");
					String homeTeamHomeRecord = rs2.getString("homeTeamHomeRecord");
					String homeTeamAwayRecord = rs2.getString("homeTeamAwayRecord");
					String awayTeamTotalRecord = rs2.getString("awayTeamTotalRecord");
					String awayTeamHomeRecord = rs2.getString("awayTeamHomeRecord");
					String awayTeamAwayRecord = rs2.getString("awayTeamAwayRecord");
					String gamegameType = rs2.getString("gameType");
					gameObject.put("homeTeam", homeTeam);
					gameObject.put("awayTeam", awayTeam);
					gameObject.put("id", id);
					gameObject.put("spread", spread);
					gameObject.put("completed", completed);
					gameObject.put("started", started);
					gameObject.put("date", date);
					gameObject.put("displayClock", displayClock);
					gameObject.put("homeTeamScore", gamehomeTeamScore);
					gameObject.put("awayTeamScore", gameawayTeamScore);
					gameObject.put("gameTime", gameTime);
					gameObject.put("homeTeamId", homeTeamId);
					gameObject.put("awayTeamId", awayTeamId);
					gameObject.put("homeTeamAbbreviation", gamehomeTeamAbbreviation);
					gameObject.put("awayTeamAbbreviation", gameawayTeamAbbreviation);
					gameObject.put("homeTeamTotalRecord", homeTeamTotalRecord);
					gameObject.put("homeTeamHomeRecord", homeTeamHomeRecord);
					gameObject.put("homeTeamAwayRecord", homeTeamAwayRecord);
					gameObject.put("awayTeamTotalRecord", awayTeamTotalRecord);
					gameObject.put("awayTeamHomeRecord", awayTeamHomeRecord);
					gameObject.put("awayTeamAwayRecord", awayTeamAwayRecord);
					gameObject.put("gameType", gamegameType);
					betObject.put("associatedGame", gameObject);

					
				}
				
				array.put(betObject);
				stmt2.close();
				
				
			}
			stmt.close();

		object.put("result", "success");
		object.put("betArray", array);
	}catch(Exception e){
		e.printStackTrace();
		object.put("result", "error");
	}
	
		return Response.status(200).entity(object.toString()).build();
	
}
}
