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
				object.put("result", "success");

				return Response.status(200).entity(object.toString()).build();

				
				
			}else if(instruction.equals("startGame")){
				sql = "UPDATE Games set started=1, homeTeamScore=56, awayTeamScore=44 WHERE id=1111";
				stmt.execute(sql);
				object.put("result", "success");
				
				return Response.status(200).entity(object.toString()).build();

				
			}else if(instruction.equals("finishGame")){
				sql = "UPDATE Games set completed=1, homeTeamScore=99, awayTeamScore=66 WHERE id=1111";
				
				stmt.execute(sql);

				updateBets();
				object.put("result", "success");
				
				return Response.status(200).entity(object.toString()).build();
			}
			
			
			else if(instruction.equals("reset")){
				sql = "DELETE FROM Games WHERE id=1111";
				stmt.execute(sql);
				sql="DELETE FROM Bets WHERE gameId=1111";
				stmt.execute(sql);
				object.put("result", "success");
				
				return Response.status(200).entity(object.toString()).build();
				
			}else if(instruction.equals("zeroOutUser")){
				sql="UPDATE STOCKUSERS set accountBalance=0, availableBalance=0, usedBalance=0 WHERE email='gradedemo@gmail.com'";
				stmt.execute(sql);
				sql="UPDATE STOCKUSERS set accountBalance=0, availableBalance=0, usedBalance=0 WHERE email='classdemo@gmail.com'";
				stmt.execute(sql);
				sql="UPDATE STOCKUSERS set accountBalance=0, availableBalance=0, usedBalance=0 WHERE email='wx'";
				stmt.execute(sql);
				object.put("result", "success");
				
				return Response.status(200).entity(object.toString()).build();
				
			}
					
		

	
			return null;
	
}
	
	
	public static void updateBets() throws Exception{
		Statement stmt =null;
		
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql = "SELECT * FROM Bets WHERE gameComplete=0";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int gameId = rs.getInt("gameId");
				String team = rs.getString("teamName");
				String betHomeTeamAbbreviaiton = rs.getString("homeTeamAbbreviation");
				String betAwayTeamAbbreviaiton = rs.getString("awayTeamAbbreviation");
				String odds = rs.getString("odds");
				String gameType = rs.getString("gameType");
				double pointsToRisk = rs.getDouble("pointsToRisk");
				double pointsToWin = rs.getDouble("pointsToWin");
				String email = rs.getString("email");
				sql = "SELECT * FROM Games WHERE id="+gameId;
				Statement stmt2 = con.createStatement();
				ResultSet rs1 = stmt2.executeQuery(sql);
				String homeTeamAbbreviation = null;
				String awayTeamAbbreviation = null;
				int homeTeamScore = 0;
				int awayTeamScore = 0;
				boolean complete= false;
				
				while(rs1.next()){
					//System.out.println("Getting game data");
					homeTeamAbbreviation = rs1.getString("homeTeamAbbreviation");
					awayTeamAbbreviation = rs1.getString("awayTeamAbbreviation");
					homeTeamScore = rs1.getInt("homeTeamScore");
					awayTeamScore = rs1.getInt("awayTeamScore");
					complete = rs1.getBoolean("completed");
					
					
				}
				sql = "UPDATE Bets SET homeTeamScore="+homeTeamScore+", awayTeamScore="+awayTeamScore+", homeTeamAbbreviation='"+homeTeamAbbreviation+"', awayTeamAbbreviation='"+awayTeamAbbreviation+"', gameComplete="+complete+" WHERE gameId="+gameId;
				Statement stmt3 = con.createStatement();
				stmt3.executeUpdate(sql);
				//System.out.println("updated bet data:, hometeam: "+homeTeamAbbreviation+", awayteam: "+awayTeamAbbreviation+ "hometeamscore: "+homeTeamScore+", awayTeamScore: "+awayTeamScore+" , complete: "+complete);
				
				if(complete){
					Statement stmt4 = con.createStatement();
					if(gameType.equals("MLB")){
						if(team.equals(betHomeTeamAbbreviaiton)){
							if(homeTeamScore>awayTeamScore){
								System.out.println("Chenging account balance: MLB Bet home team Won");
								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									available+=pointsToWin;
									used -= pointsToRisk;
									balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
								
								
							}else if(homeTeamScore==awayTeamScore){
								System.out.println("Chenging account balance: MLB Bet home team draw");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									//balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else{
								System.out.println("Chenging account balance: MLB Bet home team lost");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									//available-= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									balance-=pointsToRisk;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}
						}else{
							if(awayTeamScore>homeTeamScore){
								System.out.println("Chenging account balance: MLB Bet away team won");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									available+=pointsToWin;
									used -= pointsToRisk;
									balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
								
							}else if(awayTeamScore==homeTeamScore){
								System.out.println("Chenging account balance: MLB Bet away team draw");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									//balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else{
								System.out.println("Chenging account balance: MLB Bet away team lost");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									//available-= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									balance-=pointsToRisk;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}
						}
					}else{
						double spread = Double.parseDouble(odds);
						if(team.equals(betHomeTeamAbbreviaiton)){
							if(homeTeamScore+spread>awayTeamScore){
								System.out.println("Chenging account balance: NBA Bet home team Won");
								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									available+=pointsToWin;
									used -= pointsToRisk;
									balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else if(homeTeamScore+spread==awayTeamScore){
								System.out.println("Chenging account balance: NBA Bet home team draw");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									//balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else{
								System.out.println("Chenging account balance: NBA Bet home team lost");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									//available-= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									balance-=pointsToRisk;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}
							
							
						}else{
							if(awayTeamScore+spread>homeTeamScore){
								System.out.println("Chenging account balance: NBA Bet away team won");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									available+=pointsToWin;
									used -= pointsToRisk;
									balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else if(awayTeamScore+spread==homeTeamScore){
								System.out.println("Chenging account balance: NBA Bet away team draw");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									available+= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									//balance+=pointsToWin;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}else{
								System.out.println("Chenging account balance: NBA Bet away team lost");

								sql= " SELECT * FROM STOCKUSERS WHERE email='"+email+"'";
								ResultSet rs3 = stmt4.executeQuery(sql);
								double balance = 0.0;
								double used =0.0;
								double available= 0.0;
								if(rs3.next()){
									 balance = rs3.getDouble("accountBalance");
									used = rs3.getDouble("usedBalance");
									available = rs3.getDouble("availableBalance");
									//available-= pointsToRisk;
									//available+=pointsToWin;
									used -= pointsToRisk;
									balance-=pointsToRisk;
									
								}
								sql = "UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
								int rs4 = stmt4.executeUpdate(sql);
							}
						}
					}
				}
				
				stmt3.close();
				stmt2.close();
				
			}
			stmt.close();
			con.close();
		
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
