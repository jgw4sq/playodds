package com.weber;

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
	
	private static final String username = "schedulemepronotifications@gmail.com";
	private static final String password = "apples1290";
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
	
	
	public static void sendShiftUpdateNotification(Shift originalShift, Shift newShift){
		System.out.println("attempting to send generic shift update");
		System.out.println("Notification original: "+ originalShift.toString());
		System.out.println("Notification new: "+ newShift.toString());
		//changed employee
		if(!originalShift.getEmail().equals(newShift.getEmail())){
			System.out.println("Different employee");
			sendAssignedNewShift(newShift, newShift.getEmail());
			sendRemovedShift(originalShift, originalShift.getEmail());
		}
		//changed start time
		else if(!originalShift.getStartTime().equals(newShift.getStartTime())){
			System.out.println("Different start time");
			sendChangedStartTime(originalShift, newShift, newShift.getEmail());
			sendChangedPosition(originalShift, newShift, newShift.getEmail());

		}
		//changed end time
		else if(!originalShift.getEndTime().equals(newShift.getEndTime())){
			System.out.println("Different end time");
			sendChangedEndTime(originalShift, newShift, newShift.getEmail());

		}
		//
		else if(!originalShift.getPosition().equalsIgnoreCase(newShift.getPosition())){
			System.out.println("Different position");
			sendChangedPosition(originalShift, newShift, newShift.getEmail());


		}else{
			System.out.println("no difference");
		}
		
	}

	private static void sendChangedPosition(Shift originalShift,
			Shift newShift, String email) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });

				try {
					System.out.println("attempting to send Message of new position");
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("username"));
					message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(email));
					message.setSubject("Change in Shift on "+daysOfWeek[newShift.getStartTime().getDay()]+", "+monthsOfYear[newShift.getStartTime().getMonth()]+" "+newShift.getStartTime().getDate());
					message.setText("The position of your shift was changed from: "+ originalShift.getPosition()+" to: "+newShift.getPosition());

					Transport.send(message);

					System.out.println("Done");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
			}
	

	private static void sendChangedEndTime(Shift originalShift, Shift newShift,
			String email) {
		// TODO Auto-generated method stub
		
	}

	private static void sendChangedStartTime(Shift originalShift,
			Shift newShift, String email) {
		// TODO Auto-generated method stub
		
	}

	private static void sendRemovedShift(Shift shift, String email) {
		// TODO Auto-generated method stub
		
	}

	private static void sendAssignedNewShift(Shift shift, String email) {
		// TODO Auto-generated method stub
		
	}

}
