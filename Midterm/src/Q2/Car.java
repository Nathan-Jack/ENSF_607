package Q2;

public class Car extends Vehicle {
	
	private String make;
    private int year;
	
	public Car(String make,int year,double speed) {
		 super(speed); // calling vehicle constructor for inherited values
	        this.make = make;
	        this.year = year;
	    }

	
	@Override
	public double calcTravelTime(City c1, City c2) {
		double distance = c1.calcDistance(c2);
		
		return (distance/this.getAverageSpeed());
	}
} 

