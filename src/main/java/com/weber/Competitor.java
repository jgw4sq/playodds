package job;
public class Competitor  {

	private double score;
	private String name;
	private String abbreviation;
	private String totalRecord;
	private String homeRecord;
	private String awayRecord;


	public void setTotalRecord(String record) {
		this.totalRecord = record;
	}

	public Competitor(){

	}
	public Competitor(double score, String name, String abbreviation) {
		this.score = score;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	

	

	public String getAbbreviation() {
		return abbreviation;
	}




	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}




	public double getScore() {
		return score;
	}




	public void setScore(double score) {
		this.score = score;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	

	public String getTotalRecord() {
		return totalRecord;
	}

	public String getHomeRecord() {
		return homeRecord;
	}

	public void setHomeRecord(String homeRecord) {
		this.homeRecord = homeRecord;
	}

	public String getAwayRecord() {
		return awayRecord;
	}

	public void setAwayRecord(String awayRecord) {
		this.awayRecord = awayRecord;
	}

	
}
