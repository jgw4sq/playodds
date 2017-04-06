package job;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class Jobs implements Job {
	int x =0;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
			updateData();
	}
	
	public void updateData(){
		System.out.println(x);
		x+=1;
	}

}
