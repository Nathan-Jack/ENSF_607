package Q2;

public abstract class Vehicle {

	private double averageSpeed;

	public Vehicle(double speed) {
		averageSpeed = speed;
	}

	public double getAverageSpeed() {
		return this.averageSpeed;
	}

	public abstract double calcTravelTime(City c1, City c2);
}
// Assume there is more code here (e.g. getters and setters, etc.)
