package Q2;

public class Location {
	
	private double x, y;
	
	public Location (double x, double y) {
		this.setX(x);
		this.setY(y);
	}
        //Assume there is more code here (e.g. getters and setters, etc.) 

	private void setX(double x2) {
		this.x = x2;
		
	}
	private void setY(double y2) {
		this.y = y2;
		
		
	}

	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}
}
