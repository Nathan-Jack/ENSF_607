package Q2;

public class TravelApp {
	
	public static void main (String [] args) {
		
		City cityA = new City ("cityA", new Location (10, 20));
		City cityB = new City ("cityB", new Location (1000, 2000));
		
		Car c = new Car ("Toyota", 2020, 80);
		Plane p = new Plane ("AC111", 800);
		
		System.out.println(c.calcTravelTime(cityA, cityB));
		System.out.println(p.calcTravelTime(cityA, cityB));
		
	}

}


