package courseRegistration;

public class Registration {
	
	private Student theStudent;
	private Offering theOffering;
	private char grade;
	
	public void register (Student theStudent, Offering theOffering) {
		setTheStudent (theStudent);
		setTheOffering (theOffering);
		addRegistration ();
	}

	private void addRegistration() {
		// TODO Auto-generated method stub
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

}
