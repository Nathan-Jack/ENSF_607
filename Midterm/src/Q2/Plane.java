package Q2;

public class Plane extends Vehicle {
	private String flightNum;
	
	public Plane(String flightNum, double speed) {
		super(speed);
		this.flightNum = flightNum;
		
	}
	
	public double calcTravelTime(City c1, City c2) {
		double distance = c1.calcDistance(c2);
		
		return (distance/this.getAverageSpeed());
	}
} 


