package courseRegistration;

//This is my "FrontEnd!"
/**
 * Lab 3 Exercise 2 Code Registration Class
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 13th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: Handles interactions between student and offering. Limits
 *        students from applying for courses theyve already enrolled in.
 *        Overrides equals method to ensure students and their courses are being
 *        compared accurately.
 */
public class Registration {

	private Student theStudent;
	private Offering theOffering;
	private char grade;

	public void register(Student theStudent, Offering theOffering) {

		setTheStudent(theStudent);
		setTheOffering(theOffering);

		for (Registration r : theStudent.getRegList()) {
			if (r != null && r.getTheOffering().getTheCourse().equals(this.getTheOffering().getTheCourse())) {
				System.out.println(this.theStudent.getStudentName() + "  already registered in this course.");
			}
		}
		addRegistration();
	}

	private void addRegistration() {

		theStudent.addRegistration(this);
		theOffering.addRegistration(this);

	}

	public Student getTheStudent() {
		return theStudent;
	}

	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	public Offering getTheOffering() {
		return theOffering;
	}

	public void setTheOffering(Offering theOffering) {
		this.theOffering = theOffering;
	}

	public char getGrade() {
		return grade;
	}

	public void setGrade(char grade) {
		this.grade = grade;
	}

	@Override
	/**
	 * Registration objects are to be compared to other registrations using Student
	 * IDs.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Registration) {
			Registration r = (Registration) obj;
			if (r != null && this.getTheStudent().getStudentId() == r.getTheStudent().getStudentId()
					&& this.getTheOffering().getSectionNum() == r.getTheOffering().getSectionNum()
					&& this.getTheOffering().getTheCourse().equals(r.getTheOffering().getTheCourse())) {
				return true;
			}
		}
		return false;
	}

}
