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

@Path("/UserService")
public class UserService {

	
	
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
	
	
	@Path("{email}")
	  @GET
	  @Produces("application/json")
	  public Response updateUser(@PathParam("email") String email) throws Exception {

		Statement stmt =null;
		JSONObject object = new JSONObject();
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql = "SELECT * FROM STOCKUSERS WHERE email ='"+email+"'";
			ResultSet rs = stmt.executeQuery(sql);
			double balance = 200.0;
			double used =0.0;
			double available=0.0;
			if(rs.next()){
				//user already exists
				
				sql="SELECT * FROM Bets WHERE email='"+email+"'";
				Statement stmt2 = con.createStatement();
				ResultSet rs2 = stmt2.executeQuery(sql);
				while(rs2.next()){
					boolean gameComplete = rs2.getBoolean("gameComplete");
					if(!gameComplete){
						used+= rs2.getDouble("pointsToRisk");
					}
					
				}
				
				stmt2.close();
				balance= rs.getDouble("accountBalance");
				available = balance-used;
				
				
				sql = "UPDATE STOCKUSERS set accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
				Statement stmt3 = con.createStatement();
				int rs3 = stmt3.executeUpdate(sql);
				
				
				
				
				object.put("result", "success");
				JSONObject user = new JSONObject();
				user.put("email", rs.getString("email"));
				user.put("username", rs.getString("username"));
				user.put("password", rs.getString("password"));
				user.put("accountBalance", balance);

				user.put("availableBalance", available);

				user.put("usedBalance", used);
				user.put("firstName", rs.getString("firstName"));
				user.put("lastName", rs.getString("lastName"));
				object.put("user", user);





			}else{
				
				object.put("result", "error");

			}
			
			return Response.status(200).entity(object.toString()).build();


		
	}
	
	
	
	@Path("{email}/{replenish}")
	  @GET
	  @Produces("application/json")
	  public Response replenishUser(@PathParam("email") String email,@PathParam("replenish") String replenish) throws Exception {

		Statement stmt =null;
		JSONObject object = new JSONObject();
		Class.forName("com.mysql.jdbc.Driver");  
		 Connection con=DriverManager.getConnection(  
					"jdbc:mysql://127.9.167.130:3306/jake","adminnHxi4B8","fWUk7PSKVlcV");
			stmt = con.createStatement();
			String sql = "SELECT * FROM STOCKUSERS WHERE email ='"+email+"'";
			ResultSet rs = stmt.executeQuery(sql);
			double balance = 200.0;
			double used =0.0;
			double available=200.0;
			if(rs.next()){
				//user already exists
				
				sql="UPDATE STOCKUSERS SET accountBalance="+balance+", availableBalance="+available+", usedBalance="+used+" WHERE email='"+email+"'";
				Statement stmt2 = con.createStatement();
				int rs2 = stmt2.executeUpdate(sql);
				
				if(rs2>0){
				
				
				
				object.put("result", "success");
				JSONObject user = new JSONObject();
				user.put("email", rs.getString("email"));
				user.put("username", rs.getString("username"));
				user.put("password", rs.getString("password"));
				user.put("accountBalance", balance);

				user.put("availableBalance", available);

				user.put("usedBalance", used);
				user.put("firstName", rs.getString("firstName"));
				user.put("lastName", rs.getString("lastName"));
				object.put("user", user);





			}else{
				
				object.put("result", "error");

			}
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

	
	
}
