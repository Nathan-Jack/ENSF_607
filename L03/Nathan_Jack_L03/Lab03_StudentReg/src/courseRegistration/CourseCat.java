package courseRegistration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Lab 3 Exercise 2 Code Course Catalog
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 13th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: Aggregation of courses. Can search/Add/and print
 *        courses. Also creates course offerings. Delete function not yet
 *        implemented.
 */
public class CourseCat {

	private ArrayList<Course> courseList;

	public CourseCat() {
		courseList = loadFromDB();
	}

	private static ArrayList<Course> loadFromDB() {
		// In real life course would be loaded for the database or at least some sort of
		// file
		// on disk.

		// So imagine this is being loaded from the database!

		ArrayList<Course> imaginaryDB = new ArrayList<Course>();

		imaginaryDB.add(new Course("ENGG", 233));
		imaginaryDB.add(new Course("ENGG", 404));
		imaginaryDB.add(new Course("ENSF", 607));
		imaginaryDB.add(new Course("PHYS", 259));
		imaginaryDB.add(new Course("WACK", 101));
		imaginaryDB.add(new Course("WACK", 102));
		imaginaryDB.add(new Course("WACK", 103));
		imaginaryDB.add(new Course("WACK", 104));
		imaginaryDB.add(new Course("WACK", 105));
		imaginaryDB.add(new Course("WACK", 106));
		imaginaryDB.add(new Course("WACK", 107));
		imaginaryDB.add(new Course("WACK", 108));
		return imaginaryDB;
	}

	public ArrayList<Course> searchCat(String courseName) {
		ArrayList<Course> foundCourses = new ArrayList<Course>();
		for (Course c : courseList) {
			if (c.getCourseName().toLowerCase().strip().equals(courseName.toLowerCase().strip())) {
				foundCourses.add(c);
			}
		}
		if (foundCourses.size() == 0) {
			System.err.println("Couse " + courseName + " " + " does NOT exist!");
			return null;
		}

		return foundCourses;

	}

	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (c.getCourseNum() == courseNum
					&& c.getCourseName().toLowerCase().strip().equals(courseName.toLowerCase().strip())) {
				return c;
			}
		}
		System.err.println("Couse " + courseName + " " + courseNum + " does NOT exist!");
		return null;
	}

	public void createOffering(Course theCourse, int secNum, int secCap) {
		if (theCourse != null) {
			Offering theOffering = new Offering(secNum, secCap);
			theOffering.setTheCourse(theCourse);
			theCourse.addOffering(theOffering);
		}
	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	public void listAllCourses() {
		for (Iterator<Course> iterator = this.getCourseList().iterator(); iterator.hasNext();) {
			Course c = iterator.next();
			System.out.println(c + "\n");
		}
	}
}
