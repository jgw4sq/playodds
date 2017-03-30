import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/NCAAMService")
public class NCAAMService {

	
	
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
	
	
	@Path("{date}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("date") String date) throws Exception {

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
		String fullHtml= getHTML("http://www.espn.com/mens-college-basketball/scoreboard/_/date/"+dateString);
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
