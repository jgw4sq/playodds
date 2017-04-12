package job;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class Jobs implements Job {
	int x =0;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
			try {
				Date date = new Date();
				getNBA(date);
				getMLB(date);
				date.setDate(date.getDate()-1);
				getNBA(date);
				getMLB(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		//updateData();
	}
	
	public void updateData(){
		System.out.println(x);
		x+=1;
	}
	
	
	public static void getNBA(Date date) throws Exception{
		
		String dateString = date.getYear()+1900+""+String.format("%02d",date.getMonth()+1)+String.format("%02d",date.getDate());
		String fullHtml= getHTML("http://www.espn.com/nba/scoreboard/_/date/"+dateString);
		String [] split = fullHtml.split(";window.espn.scoreboardSettings");
		//System.out.println("Split 0: "+split[0]);
		String [] goodStuff = split[0].split("window.espn.scoreboardData 	=");
		//System.out.println("Length: "+goodStuff.length);
		String actualGoodStuff = goodStuff[1].trim();
		//System.out.println(actualGoodStuff);
		try {
			JSONObject jsonObject = new JSONObject(actualGoodStuff);
			//System.out.println(jsonObject.getJSONArray("leagues"));
			JSONArray events = jsonObject.getJSONArray("events");
			//System.out.println(events.toString());
			ArrayList<Competition> games = new ArrayList<Competition>();
			for (int i = 0; i < events.length(); i++) {
				Competition game = new Competition();
				//System.out.println(events.get(i));
				JSONObject event = new JSONObject(events.get(i).toString());
				//System.out.println("Event: "+event.toString());
				//System.out.println(event.toString());
				game.setId(Integer.parseInt(event.getString("id")));
				JSONArray competitions = event.getJSONArray("competitions");
				for (int j = 0; j < competitions.length(); j++) {

					JSONObject competition = new JSONObject(competitions.get(j).toString());
					//System.out.println(competition.toString());
					if (competition.has("odds")) {
						JSONArray odds = competition.getJSONArray("odds");
						if (odds.length() >= 1) {
							JSONObject odd = new JSONObject(odds.get(0).toString());
							if(odd.has("details")){
							game.setSpread(odd.getString("details"));
							}
							if(odd.has("overUnder")){
							game.setOverUnder((double)(odd.getDouble(("overUnder"))));
							}

						}

					}
					JSONObject status = competition.getJSONObject("status");
					int period = status.getInt("period");
					String displayClock = status.getString("displayClock");
					JSONObject type = status.getJSONObject("type");
					boolean completed = type.getBoolean("completed");
					String state = type.getString("state");
					if (state.equals("pre")){
						game.setStarted(false);
					}else{
						game.setStarted(true);
					}
					game.setPeriod(period);
					game.setDisplayclock(displayClock);
					game.setCompleted(completed);
					String dateOfComp = type.getString("shortDetail");
					game.setGameTime(dateOfComp);


					JSONArray competitors = competition.getJSONArray("competitors");
					for (int k = 0; k < competitors.length(); k++) {
						Competitor competitorToAdd = new Competitor();
						JSONObject competitor = new JSONObject(competitors.get(k).toString());
						JSONObject team = competitor.getJSONObject("team");
						JSONArray records = competitor.getJSONArray("records");
						for (int z = 0; z < records.length(); z++) {
							JSONObject record = new JSONObject(records.get(z).toString());
							String recordString = record.getString("summary");
							String recordType = record.getString("type");
							if (recordType.equals("total")) {
								competitorToAdd.setTotalRecord(recordString);
							} else if (recordType.equals("home")) {
								competitorToAdd.setHomeRecord(recordString);
							} else if (recordType.equals("road")) {
								competitorToAdd.setAwayRecord(recordString);
							}
						}
						String name = team.getString("displayName");
						competitorToAdd.setName(name);
						competitorToAdd.setScore(Double.parseDouble(competitor.getString("score")));
						competitorToAdd.setAbbreviation(team.getString("abbreviation"));
						System.out.println(team.getString("abbreviation"));
						//System.out.println(name);
						if (competitor.getString("homeAway").equals("home")) {
							game.setHomeCompetitor(competitorToAdd);
						} else {
							game.setAwayCompetitor(competitorToAdd);
						}
					}
				}
				

				games.add(game);


			}
			Statement stmt =null;
			
			Class.forName("com.mysql.jdbc.Driver");  
			 Connection con=DriverManager.getConnection(  
						"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
				stmt = con.createStatement();
				for( int index=0; index<games.size(); index++){
					Competition comp = games.get(index);
					System.out.println("Checking: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					String sql= "SELECT COUNT(id) as total FROM Games WHERE id="+comp.getId()+";";
					stmt= con.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					int total=0;
					if(rs.next()){
					 total = rs.getInt("total");
					}
					
					if(total==0){
						System.out.println("Adding: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

						sql = "INSERT INTO Games (homeTeam,awayTeam,id,spread,completed,date,displayClock,homeTeamScore,awayTeamScore,gameTime,homeTeamAbbreviation,awayTeamAbbreviation,started,homeTeamTotalRecord,homeTeamHomeRecord,homeTeamAwayRecord,awayTeamTotalRecord,awayTeamHomeRecord,awayTeamAwayRecord,gameType) VALUES ('"+comp.getHomeCompetitor().getName()+"','"+comp.getAwayCompetitor().getName()+"',"+comp.getId()+",'"+comp.getSpread()+"',"+comp.isCompleted()+",'"+comp.getDate()+"','"+comp.getDisplayclock()+"',"+comp.getHomeCompetitor().getScore()+","+comp.getAwayCompetitor().getScore()+",'"+comp.getGameTime()+"','"+comp.getHomeCompetitor().getAbbreviation()+"','"+comp.getAwayCompetitor().getAbbreviation()+"',"+comp.isStarted()+",'"+comp.getHomeCompetitor().getTotalRecord()+"','"+comp.getHomeCompetitor().getHomeRecord()+"','"+comp.getHomeCompetitor().getAwayRecord()+"','"+comp.getAwayCompetitor().getTotalRecord()+"','"+comp.getAwayCompetitor().getHomeRecord()+"','"+comp.getAwayCompetitor().getAwayRecord()+"','NBA')";
						stmt.execute(sql);
						System.out.println("Added: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					}else{
						System.out.println("Updating: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());
						System.out.println("home: "+comp.getHomeCompetitor().getScore() + ", away: "+comp.getAwayCompetitor().getScore());
						sql = "UPDATE Games SET started="+comp.isStarted()+", completed="+comp.isCompleted()+", displayClock='"+comp.getDisplayclock()+"', homeTeamScore="+comp.getHomeCompetitor().getScore()+", awayTeamScore="+comp.getAwayCompetitor().getScore()+", gameTime='"+comp.getGameTime()+"' WHERE id="+comp.getId();
					
						stmt.execute(sql);
						System.out.println("Updated: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					}
					
					
					
					
				}
				
				stmt.close();
				
				//stmt= con.createStatement();
				//sql ="UPDATE SHIFTS SET guard=null , email=null WHERE guard='"+firstname+" "+lastname+"';";
				 //rs = stmt.executeUpdate(sql);
				 //stmt.close();
				con.close();

			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void getMLB(Date date) throws Exception{
		
		String dateString = date.getYear()+1900+""+String.format("%02d",date.getMonth()+1)+String.format("%02d",date.getDate());
		String fullHtml= getHTML("http://www.espn.com/mlb/scoreboard/_/date/"+dateString);
		String [] split = fullHtml.split(";window.espn.scoreboardSettings");
		//System.out.println("Split 0: "+split[0]);
		String [] goodStuff = split[0].split("window.espn.scoreboardData 	=");
		//System.out.println("Length: "+goodStuff.length);
		String actualGoodStuff = goodStuff[1].trim();
		//System.out.println(actualGoodStuff);
		try {
			JSONObject jsonObject = new JSONObject(actualGoodStuff);
			//System.out.println(jsonObject.getJSONArray("leagues"));
			JSONArray events = jsonObject.getJSONArray("events");
			//System.out.println(events.toString());
			ArrayList<Competition> games = new ArrayList<Competition>();
			for (int i = 0; i < events.length(); i++) {
				Competition game = new Competition();
				//System.out.println(events.get(i));
				JSONObject event = new JSONObject(events.get(i).toString());
				//System.out.println("Event: "+event.toString());
				//System.out.println(event.toString());
				game.setId(Integer.parseInt(event.getString("id")));
				JSONArray competitions = event.getJSONArray("competitions");
				for (int j = 0; j < competitions.length(); j++) {

					JSONObject competition = new JSONObject(competitions.get(j).toString());
					//System.out.println(competition.toString());
					if (competition.has("odds")) {
						JSONArray odds = competition.getJSONArray("odds");
						if (odds.length() >= 1) {
							JSONObject odd = new JSONObject(odds.get(0).toString());
							if(odd.has("details")){
							game.setSpread(odd.getString("details"));
							}
							if(odd.has("overUnder")){
							game.setOverUnder((double)(odd.getDouble(("overUnder"))));
							}

						}

					}
					JSONObject status = competition.getJSONObject("status");
					int period = status.getInt("period");
					String displayClock = status.getString("displayClock");
					JSONObject type = status.getJSONObject("type");
					String state = type.getString("state");
					if (state.equals("pre")){
						game.setStarted(false);
					}else{
						game.setStarted(true);
					}
					boolean completed = type.getBoolean("completed");
					game.setPeriod(period);
					game.setDisplayclock(displayClock);
					game.setCompleted(completed);
					String dateOfComp = type.getString("shortDetail");
					game.setGameTime(dateOfComp);


					JSONArray competitors = competition.getJSONArray("competitors");
					for (int k = 0; k < competitors.length(); k++) {
						Competitor competitorToAdd = new Competitor();
						JSONObject competitor = new JSONObject(competitors.get(k).toString());
						JSONObject team = competitor.getJSONObject("team");
						JSONArray records = competitor.getJSONArray("records");
						for (int z = 0; z < records.length(); z++) {
							JSONObject record = new JSONObject(records.get(z).toString());
							String recordString = record.getString("summary");
							String recordType = record.getString("type");
							if (recordType.equals("total")) {
								competitorToAdd.setTotalRecord(recordString);
							} else if (recordType.equals("home")) {
								competitorToAdd.setHomeRecord(recordString);
							} else if (recordType.equals("road")) {
								competitorToAdd.setAwayRecord(recordString);
							}
						}
						String name = team.getString("displayName");
						competitorToAdd.setName(name);
						competitorToAdd.setScore(Double.parseDouble(competitor.getString("score")));
						competitorToAdd.setAbbreviation(team.getString("abbreviation"));
						System.out.println(team.getString("abbreviation"));
						//System.out.println(name);
						if (competitor.getString("homeAway").equals("home")) {
							game.setHomeCompetitor(competitorToAdd);
						} else {
							game.setAwayCompetitor(competitorToAdd);
						}
					}
				}
				

				games.add(game);


			}
			Statement stmt =null;
			
			Class.forName("com.mysql.jdbc.Driver");  
			 Connection con=DriverManager.getConnection(  
						"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
				stmt = con.createStatement();
				for( int index=0; index<games.size(); index++){
					Competition comp = games.get(index);
					System.out.println("Checking: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					String sql= "SELECT COUNT(id) as total FROM Games WHERE id="+comp.getId()+";";
					stmt= con.createStatement();
					ResultSet rs = stmt.executeQuery(sql);
					int total=0;
					if(rs.next()){
					 total = rs.getInt("total");
					}
					
					if(total==0){
//						System.out.println("Adding: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

						sql = "INSERT INTO Games (homeTeam,awayTeam,id,spread,completed,date,displayClock,homeTeamScore,awayTeamScore,gameTime,homeTeamAbbreviation,awayTeamAbbreviation,started,homeTeamTotalRecord,homeTeamHomeRecord,homeTeamAwayRecord,awayTeamTotalRecord,awayTeamHomeRecord,awayTeamAwayRecord,gameType) VALUES ('"+comp.getHomeCompetitor().getName()+"','"+comp.getAwayCompetitor().getName()+"',"+comp.getId()+",'"+comp.getSpread()+"',"+comp.isCompleted()+",'"+comp.getDate()+"','"+comp.getDisplayclock()+"',"+comp.getHomeCompetitor().getScore()+","+comp.getAwayCompetitor().getScore()+",'"+comp.getGameTime()+"','"+comp.getHomeCompetitor().getAbbreviation()+"','"+comp.getAwayCompetitor().getAbbreviation()+"',"+comp.isStarted()+",'"+comp.getHomeCompetitor().getTotalRecord()+"','"+comp.getHomeCompetitor().getHomeRecord()+"','"+comp.getHomeCompetitor().getAwayRecord()+"','"+comp.getAwayCompetitor().getTotalRecord()+"','"+comp.getAwayCompetitor().getHomeRecord()+"','"+comp.getAwayCompetitor().getAwayRecord()+"','MLB')";
						stmt.execute(sql);
						//System.out.println("Added: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					}else{
						//System.out.println("Updating: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

						sql = "UPDATE Games SET started="+comp.isStarted()+", completed="+comp.isCompleted()+", displayClock='"+comp.getDisplayclock()+"', homeTeamScore="+comp.getHomeCompetitor().getScore()+", awayTeamScore="+comp.getAwayCompetitor().getScore()+", gameTime='"+comp.getGameTime()+"' WHERE id="+comp.getId();
					
						stmt.execute(sql);
						//System.out.println("Updated: "+comp.getHomeCompetitor().getName()+" vs. "+comp.getAwayCompetitor().getName());

					}
					
					
					
					
				}
				
				stmt.close();
				
				//stmt= con.createStatement();
				//sql ="UPDATE SHIFTS SET guard=null , email=null WHERE guard='"+firstname+" "+lastname+"';";
				 //rs = stmt.executeUpdate(sql);
				 //stmt.close();
				con.close();

			
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
