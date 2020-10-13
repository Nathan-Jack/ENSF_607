package courseRegistration;

import java.util.ArrayList;

public class Student {
	
	private String studentName;
	private int studentId;
	private ArrayList <Registration> regList;    //list of course sections
	
	public Student  (String studentName, int studentId) {
		setStudentName (studentName);
		setStudentId (studentId);
		setRegList(new ArrayList <Registration>());
	}
	public void registerForCourse (CourseCat cat, String courseName, int courseNum, int section) {
		Course myCourse = cat.searchCat(courseName, courseNum);
		//Now the student needs to make sure the section exists. and if it does, register!
		//A student registers by:
		//Creating a registration object
		//and calling the 
		Offering theOffering = myCourse.getOfferingList().get(section - 1);//must fix this!!
		Registration reg = new Registration ();
		reg.register(this, theOffering);
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public ArrayList <Registration> getRegList() {
		return regList;
	}

	public void setRegList(ArrayList <Registration> regList) {
		this.regList = regList;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		regList.add(registration);
		
	}

}
