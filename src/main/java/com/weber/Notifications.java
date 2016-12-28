package com.weber;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Notifications
 */
@WebServlet("/Notifications")
public class Notifications extends HttpServlet {
	public static final String ACCOUNT_SID = "AC4e3c68dfa43b870497b841c8f83992d2";
	  public static final String AUTH_TOKEN = "97a41ac9350a752f082ceb100cd0696e";
	  public static final String twilioNumber = "+18045062007";
	private static final String emailUsername = "schedulemepronotifications@gmail.com";
	private static final String emailPassword = "apples1290";
	private static final long serialVersionUID = 1L;
	private static final String [] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
	private static final String [] monthsOfYear = {"January", "February", "March", "April", "May", "June", "July","August","September","October","November","December"};
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Notifications() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	public static void sendShiftUpdateTextNotification(Shift originalShift, Shift newShift){
		boolean newEmployee = (!originalShift.getEmail().equals(newShift.getEmail()));
		boolean newStartTime = (!originalShift.getStartTime().equals(newShift.getStartTime()));

		boolean newEndTime = (!originalShift.getEndTime().equals(newShift.getEndTime()));

		boolean newPosition = (!originalShift.getPosition().equalsIgnoreCase(newShift.getPosition()));
		
		
		sendTextShiftChange( originalShift,  newShift,  newEmployee, newStartTime, newEndTime, newPosition);
		
		
	}
	private static void sendTextShiftChange(Shift originalShift,
			Shift newShift, boolean newEmployee, boolean newStartTime,
			boolean newEndTime, boolean newPosition) {
		// TODO Auto-generated method stub
		 Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		 com.twilio.rest.api.v2010.account.Message message =  com.twilio.rest.api.v2010.account.Message.creator(new PhoneNumber("+18046178850"), new PhoneNumber(twilioNumber),
		            "This is the ship that made the Kessel Run in fourteen parsecs?")
		        .create();

		    System.out.println(message.getSid());
		
	}

	public static void sendShiftUpdateEmailNotification(Shift originalShift, Shift newShift){
		
		//changed employee
		boolean newEmployee = (!originalShift.getEmail().equals(newShift.getEmail()));
		boolean newStartTime = (!originalShift.getStartTime().equals(newShift.getStartTime()));

		boolean newEndTime = (!originalShift.getEndTime().equals(newShift.getEndTime()));

		boolean newPosition = (!originalShift.getPosition().equalsIgnoreCase(newShift.getPosition()));
		
		
		sendEmailShiftChange( originalShift,  newShift,  newEmployee, newStartTime, newEndTime, newPosition);
		
	}

	private static void sendEmailShiftChange(Shift originalShift,
			Shift newShift, boolean newEmployee, boolean newStartTime, boolean newEndTime, boolean newPosition) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(emailUsername, emailPassword);
					}
				  });

				try {
					System.out.println("attempting to send Message of new position");
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("username"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(newShift.getEmail()));
					message.setSubject("Change in Shift on "+daysOfWeek[newShift.getStartTime().getDay()]+", "+monthsOfYear[newShift.getStartTime().getMonth()]+" "+newShift.getStartTime().getDate());
					
					
					String messageBody= "The following changes have been made to the shift you are assigned to: \n";
					if(newStartTime){
						messageBody+= "\n Start Time Changed! \n Original Start Time: "+ originalShift.getStartTime().toString()+" \n New Start Time : "+newShift.getStartTime().toString()+"\n\n";
					}
					if(newEndTime){
						messageBody+= "\n End Time Changed! \n Original End Time: "+ originalShift.getEndTime().toString()+" \n New End Time : "+newShift.getEndTime().toString()+"\n\n";
					}
					if(newPosition){
						messageBody+= "\n Position Changed! \n Original Position: "+ originalShift.getPosition()+" \n New Position : "+newShift.getPosition()+"\n\n";
					}
					
					if(newEmployee){
						messageBody = "You have been assigned a new shift with the following details: \n\n";
						messageBody += "Location: "+ newShift.getPool()+"\n";
						messageBody += "Start Time: "+ newShift.getStartTime().toString()+"\n";
						messageBody += "End Time: "+ newShift.getEndTime().toString()+"\n";
						messageBody += "Position: "+ newShift.getPosition()+"\n";
						
					}
					
					
					message.setText(messageBody);

					Transport.send(message);


				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}
	

	
}
