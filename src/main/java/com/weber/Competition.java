
package job;

import java.util.Date;

public class Competition  {

	private Competitor homeCompetitor;
	private Competitor awayCompetitor;
	private Competitor [] competitors;
	private int id;
	private String spread;
	private double overUnder;
	private double homeTeamPrice;
	private double awayTeamPrice;
	private boolean completed;
	private int period;
	private String date;
	private String displayclock;
	private String gameTime;

	public String getGameTime() {
		return gameTime;
	}

	public void setGameTime(String gameTime) {
		this.gameTime = gameTime;
	}

	public double getHomeTeamPrice() {
		return homeTeamPrice;
	}

	public void setHomeTeamPrice(double homeTeamPrice) {
		this.homeTeamPrice = homeTeamPrice;
	}

	public double getAwayTeamPrice() {
		return awayTeamPrice;
	}

	public void setAwayTeamPrice(double awayTeamPrice) {
		this.awayTeamPrice = awayTeamPrice;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public String getDisplayclock() {
		return displayclock;
	}

	public void setDisplayclock(String displayclock) {
		this.displayclock = displayclock;
	}

	

	
	public Competitor getHomeCompetitor() {
		return homeCompetitor;
	}
	public void setHomeCompetitor(Competitor homeCompetitor) {
		this.homeCompetitor = homeCompetitor;
	}
	public Competitor getAwayCompetitor() {
		return awayCompetitor;
	}
	public void setAwayCompetitor(Competitor awayCompetitor) {
		this.awayCompetitor = awayCompetitor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpread() {
		return spread;
	}
	public void setSpread(String spread) {
		this.spread = spread;
	}
	public double getOverUnder() {
		return overUnder;
	}
	public void setOverUnder(double overUnder) {
		this.overUnder = overUnder;
	}
	public Competition(){

	}
	public Competition(Competitor homeCompetitor, Competitor awayCompetitor) {
		this.homeCompetitor = homeCompetitor;
		this.awayCompetitor = awayCompetitor;
	}

	

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
