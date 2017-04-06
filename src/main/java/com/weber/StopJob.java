package job;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class StopJob
 */
@WebServlet("/StopJob")
public class StopJob extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StopJob() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		 Scheduler scheduler;
		try {
			scheduler = schedulerFactory.getScheduler();
			 List<JobExecutionContext> currentlyExecuting = scheduler.getCurrentlyExecutingJobs();

			 for( JobExecutionContext jobExecutionContext : currentlyExecuting)
			 {
			      if( jobExecutionContext.getJobDetail().getKey().getName().equals( "myJob"))
			      {
			            scheduler.interrupt( jobExecutionContext.getJobDetail().getKey());
			            System.out.println("Stopped");
			      }
			 }
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			response.getWriter().write("<html><a href=\"DataJob\"> Start Job </a></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
