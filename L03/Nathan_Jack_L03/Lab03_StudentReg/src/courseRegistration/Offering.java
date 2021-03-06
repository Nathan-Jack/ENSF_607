package courseRegistration;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Lab 3 Exercise 2 Code Offering Class
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 13th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: Handles adding/removing registrations from students and
 *        students from registrations. limits regs by class cap size. Outputs
 *        min student reqs in text form.
 */
public class Offering {

	private int sectionNum;
	private int sectionCap;
	private Course theCourse;

	private ArrayList<Registration> studentList; // student list

	public Offering(int sectionNum, int sectionCap) {
		setSectionNum(sectionNum);
		setSectionCap(sectionCap);
		studentList = new ArrayList<Registration>();
	}

	public void addRegistration(Registration reg) {
		// check that student hasn't already registered for this course
		if (this.validateReg(reg) == true) {
			studentList.add(reg);
			if (this.getStudentList().size() < 8) {
				System.out.println("This course requires min 8 students to be offered in the next semester. Current #: "
						+ this.getStudentList().size());
			} else if (this.getStudentList().size() == 8) {
				System.out.println("Minimum Student req met!");

			}
			return;
		} else {
			return;
		}
	}

	private boolean validateReg(Registration reg) {
		if (reg.getTheOffering().getStudentList().size() < reg.getTheOffering().getSectionCap()) {
			return true;
		} else {
			System.out.println("This course is full, registration failed.");
			return false;
		}
	}

	public void removeReg(Registration reg) {
		if (reg != null) {
			int flag = 0;
			for (Iterator<Registration> iterator = this.getStudentList().iterator(); iterator.hasNext();) {
				Registration r = iterator.next();
				if (r.equals(reg)) {
					iterator.remove();
					flag = 1;
				}
			}
			if (flag == 0) {
				System.out.println("System could not remove course. returning to menu");
				return;
			}
		}
	}

	public int getSectionNum() {
		return sectionNum;
	}

	public void setSectionNum(int sectionNum) {
		this.sectionNum = sectionNum;
	}

	public int getSectionCap() {
		return sectionCap;
	}

	public void setSectionCap(int sectionCap) {
		this.sectionCap = sectionCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}

	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	public ArrayList<Registration> getStudentList() {
		return studentList;
	}

	public void setStudentList(ArrayList<Registration> studentList) {
		this.studentList = studentList;
	}

	@Override
	public String toString() {
		String st = "";
		st += String.format("+-----------------+---------+----------+-----------+%n");
		st += "Section Number: " + sectionNum + ", Section cap: " + sectionCap + "\n";
		st += "Students in this section are:\n\n";
		for (Registration r : this.getStudentList()) {
			st += r.getTheStudent().getStudentName() + ", Grade: " + r.getGrade();
			st += "\n\n";
		}
		return st;
	}

}
