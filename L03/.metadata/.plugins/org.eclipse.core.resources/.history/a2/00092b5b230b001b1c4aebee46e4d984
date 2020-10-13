package courseRegistration;
//This is my "FrontEnd!"
public class CourseRegApp {
	
	public static void main (String [] args) {
		
		CourseCat cat = new CourseCat ();  //This loads the courses from our "DB"
		Student st1 = new Student ("Sara", 1);
		Student st2 = new Student ("Joe", 2);
		
		Course myCourse = cat.searchCat("ENGG", 233);
		if (myCourse != null) {
			cat.createOffering(myCourse, 1, 200);
			cat.createOffering(myCourse, 2, 150);
		}
		
		st1.registerForCourse(cat, "ENGG", 233, 1);
		
		System.out.println(myCourse.getOfferingList().get(0).getStudentList().get(0).getTheStudent().getStudentName());
	}

}
