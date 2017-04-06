package job;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

/**
 * Servlet implementation class DataJob
 */
@WebServlet("/DataJob")
public class DataJob extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
	 private Scheduler sched;
	  private boolean started= false;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataJob() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html");
		
		if(!started){

		try {
			sched = schedFact.getScheduler();
			  sched.start();
			  // define the job and tie it to our HelloJob class
			  JobDetail job = newJob(Jobs.class)
			      .withIdentity("myJob", "group1")
			      .build();

			  // Trigger the job to run now, and then every 40 seconds
			  Trigger trigger = newTrigger()
			      .withIdentity("myTrigger", "group1")
			      .startNow()
			      .withSchedule(simpleSchedule()
			          .withIntervalInSeconds(5)
			          .repeatForever())
			      .build();

			  // Tell quartz to schedule the job using our trigger
			  sched.scheduleJob(job, trigger);
			  started=true;
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		response.getWriter().write("<html><form action=\"DataJob\" method=\"post\"><input type=\"submit\" value=\"Stop\" /></form>"+"<form action=\"DataJob\" method=\"get\"><input type=\"submit\" value=\"Start\" /></form></html>");
	
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	    response.setContentType("text/html");

		
		try {
			sched = schedFact.getScheduler();
			sched.shutdown();
			started=false;
			/**
			 List<JobExecutionContext> currentlyExecuting = sched.getCurrentlyExecutingJobs();

			 for( JobExecutionContext jobExecutionContext : currentlyExecuting)
			 {
			      if( jobExecutionContext.getJobDetail().getKey().getName().equals("group1.myJob"))
			      {
			            sched.interrupt( jobExecutionContext.getJobDetail().getKey());
			            System.out.println("Stopped");
			            started=false;
			      }
			 }
			 */
			System.out.println("Stopped job");
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.getWriter().write("<html><form action=\"DataJob\" method=\"post\"><input type=\"submit\" value=\"Stop\" /></form>"+"<form action=\"DataJob\" method=\"get\"><input type=\"submit\" value=\"Start\" /></form></html>");


	}

}
