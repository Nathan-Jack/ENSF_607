
abstract class Shape implements Comparable<Shape> {
	protected Point origin;
	protected Text name;

	abstract protected Double area();

	abstract protected Double perimeter();

	abstract protected Double volume();

	protected Shape(Double x_origin, Double y_origin, String name, Colour colour) {

		origin = new Point(x_origin, y_origin, colour);
		this.name = new Text(name);
	}

	protected Point getOrigin() {
		return origin;
	}

	protected String getName() {
		return name.getText();
	}

	protected Double distance(Shape other) {
		return origin.distance(other.origin);
	}

	protected Double distance(Shape a, Shape b) {
		return Point.distance(a.origin, b.origin);
	}

	protected void move(Double dx, Double dy) {
		origin.setx(origin.getx() + dx);
		origin.sety(origin.gety() + dy);
	}

	@Override
	public String toString() {
		String s = "\nShape name: " + name + "\nOrigin: " + origin;
		return s;
	}

	@Override
	public int compareTo(Shape s) { // for treeset usage to differentiate objects.
		/*
		 * Sorting by last name. compareTo should return < 0 if this(keyword) is
		 * supposed to be less than obj s,return > 0 if this is supposed to be greater than
		 * object s and 0 if they are supposed to be equal.
		 */
		if (s != null && this.getOrigin().getx() == s.getOrigin().getx()
				&& this.getOrigin().gety() == s.getOrigin().gety() && this.getName().equals(s.getName())) {
			return 0;
		} else if (s != null && this.getName().compareTo(s.getName()) > 0) {
			return 1;
		} else if (s != null && this.getName().compareTo(s.getName()) < 0) {
			return -1;
		} else
			return -1;
	}
}
