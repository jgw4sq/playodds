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

@Path("/SignUpService")
public class SignUpService {

	
	
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
	
	
	@Path("{username}/{email}/{password}/{firstName}/{lastName}")
	  @GET
	  @Produces("application/json")
	  public Response signUpUser(@PathParam("username") String userName,@PathParam("email") String email,@PathParam("password") String password,@PathParam("firstName") String firstName,@PathParam("lastName") String lastName ) throws Exception {

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
			if(rs.next()){
				//user already exists
				object.put("result", "userExists");
			}else{
				sql = "INSERT INTO STOCKUSERS (username,email,password,firstName,lastName,availableBalance,accountBalance,usedBalance) VALUES ('"+userName+"','"+email+"','"+password+"','"+firstName+"','"+lastName+"',"+balance+","+balance+","+used+")";
				stmt.execute(sql);
				object.put("result", "success");

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
