package courseRegistration;

import java.util.ArrayList;
/**
* Lab 3 Exercise 2 Code Course Class
* 
* @author Nathan Jack
* @version 1.0
* @since Oct 13th, 2020
* 
*        Sources: Code requirements from assignment
* 
*        Description: BAse code from D2L. Added equals override for hash set comparisons.
*/
public class Course {

	private String courseName;
	private int courseNum;
	private ArrayList<Course> preReq;
	private ArrayList<Offering> offeringList;

	public Course(String courseName, int courseNum) {
		setCourseName(courseName);
		setCourseNum(courseNum);

		// This is not composition, because we are not constructing
		// an Offering Object(i.e. not calling the constructor of Offering).
		// We are simply constructing an array
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<Offering>();
	}

	// Adding a single offering to a course
	public void addOffering(Offering theOffering) {
		offeringList.add(theOffering);

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseNum() {
		return courseNum;
	}

	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	public ArrayList<Course> getPreReq() {
		return preReq;
	}

	public void setPreReq(ArrayList<Course> preReq) {
		this.preReq = preReq;
	}

	public ArrayList<Offering> getOfferingList() {
		return offeringList;
	}

	public void setOfferingList(ArrayList<Offering> offeringList) {
		this.offeringList = offeringList;
	}
	
	@Override
	/**
	 * Course objects are to be compared to other registrations using Student IDs.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Course) {
			Course c = (Course) obj;
			if (c != null && this.getCourseName().toLowerCase().strip().compareTo(c.getCourseName().toLowerCase().strip()) == 0 && this.getCourseNum() == c.getCourseNum() ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String st = "";
		String emptyofferings = "";
		st += "\n";
		st += courseName + " " + courseNum;
		st += "\n";
		for (Offering offering : offeringList)
			if (offering.getStudentList().size() != 0) {
				st += offering;
			} else {
				emptyofferings += offering.getSectionNum() + ", ";
			}
		emptyofferings = emptyofferings.replaceAll(", $", ""); // removes comma at end of string.
		st += "Sections: " + emptyofferings + " are empty.\n";
		st += String.format("+-----------------+---------+----------+-----------+%n");
		return st;
	}

}
