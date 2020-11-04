package Q2;

public class City {
	
	private String cityName;
	private Location cityLoc;
	
	public City (String name, Location loc) {
		cityName = name;
		cityLoc = loc;
	}
	public double calcDistance (City dest) {
		
		return Math.sqrt((dest.getLocation().getY() - this.getLocation().getY()) * (dest.getLocation().getY() - this.getLocation().getY()) + (dest.getLocation().getX() - this.getLocation().getX()) * (dest.getLocation().getX() - this.getLocation().getX()));

	}
	private Location getLocation() {
		return this.cityLoc;
		
	}

      //Assume there is more code here (e.g. getters and setters, etc.) 
}
