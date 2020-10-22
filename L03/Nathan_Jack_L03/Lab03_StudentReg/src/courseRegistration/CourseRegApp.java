package courseRegistration;



import java.util.ArrayList;
import java.util.Scanner;

//This is my "FrontEnd!"
/**
 * Lab 3 Exercise 2 Code Front end implementation
 * 
 * @author Nathan Jack
 * @version 1.0
 * @since Oct 13th, 2020
 * 
 *        Sources: Code requirements from assignment
 * 
 *        Description: FrontEnd user interface. Handles all interactions with
 *        user and back end. Provides debug methods to create and initialize students for testing.
 */
public class CourseRegApp {

	public CourseRegApp() {

	}

	public void Menu(CourseCat cat, Student st1) {
		Scanner scan = new Scanner(System.in);

		while (true) {

			this.printMenuChoices(); // reprint menu options every loop.

			Course myCourse = null;
			ArrayList<String> input = new ArrayList<String>(3); // Name, Number, Section
			String choice = null;

			while (choice == null) { // get input till input is valid
				choice = this.sanitizeInput(scan.nextLine(), "menu");
			}

			switch (choice) {
			case "1": { // Search course catalogue
				input = this.propmtNameNumSec(scan, 0);

				ArrayList<Course> c = null;
				if (Integer.parseInt(input.get(1)) == -1) { // if empty string entered for course number, sanitize sets it to
															// -1
					while (c != null) {
						c = cat.searchCat(input.get(0)); // search by name
						System.out.println(c);
					}
					cat.searchCat(input.get(0));
					break;
				} else {// else print course to String based on name and num
					System.out.println(cat.searchCat(input.get(0), Integer.parseInt(input.get(1))));
					break;
				}

			}
			case "2": { // add course to student
				System.out.println("Please enter the name and number of course to be registered in >> \n");

				input = this.propmtNameNumSec(scan, 1);

				if (cat.searchCat(input.get(0), Integer.parseInt(input.get(1))) == null) {
					break;
				} else {
					// Cat, Name, Number, Section
					st1.registerForCourse(cat, input.get(0), Integer.parseInt(input.get(1)),
							Integer.parseInt(input.get(2)));
				}
				break;

			}
			case "3": { // remove course from student

				st1.listAllCourses();
				System.out.println("See above.");

				input = this.propmtNameNumSec(scan, 0);
				st1.removeReg(cat, input.get(0), Integer.parseInt(input.get(1)));

				break;

			}
			case "4": { // View all courses in catalogue
				cat.listAllCourses();
				break;
			}
			case "5": { // View all courses taken by student
				st1.listAllCourses();
				break;

			}
			case "6": { // quit
				System.out.println("Quitting");
				scan.close();
				return;
			}

			}

		}

	}

	/**
	 * Prints menu options list for user.
	 */
	private void printMenuChoices() {

		System.out.println("\nWelcome to Course Registration. Please select one of the following options: ");
		System.out.format("+-----------------+---------+----------+-----------+%n");
		System.out.println("1. Search Course Catalogue");
		System.out.println("2. Add course to student courses");
		System.out.println("3. Remove course from student courses");
		System.out.println("4. View all courses in catalogue");
		System.out.println("5. View all courses taken by student");
		System.out.println("6. Quit");
		System.out.println("\nInput Selection >>");
	}

	/**
	 * Prompts user for input and returns array of values.
	 * 
	 * @param scan scanner required for user input from keyboard
	 * @param i    0 or 1. indicates if section number input is required
	 * @return Array of strings of user input in order Name,Number,Section
	 */
	private ArrayList<String> propmtNameNumSec(Scanner scan, int i) {

		String cName = null, cNum = null, cSec = null;
		ArrayList<String> res = new ArrayList<String>();

		System.out.println("Enter Course Name >> ");
		while (cName == null) {
			cName = scan.nextLine();
		}
		System.out.println("Enter Course number >> ");
		while (cNum == null) {
			cNum = this.sanitizeInput(scan.nextLine(), ""); // ensures input is positive or a string.
		}
		if (i == 1) {
			System.out.println("Enter Section number >> ");
			while (cSec == null) {
				cSec = this.sanitizeInput(scan.nextLine(), ""); // ensures input is positive or a string.
			}
		}
		res.add(cName);
		res.add(cNum);
		res.add(cSec);

		return res;

	}

	private String sanitizeInput(String s, String menu) {

		String errMsg = s + " Was an invalid input. Please try again >>";
		if (s.isEmpty()) {
			s = "-1";
			return s;
		}
		try {
			int r = Integer.valueOf(s);
			if (r < 0) { // no negative values allowed
				System.out.println(errMsg);
				return null;
			}
			if (menu.compareTo("menu") == 0) {
				if (r > 0 && r < 7) {
					return s;
				} else {
					System.out.println(errMsg);
					return null;
				}
			} else {
				return s;
			}

		} catch (NumberFormatException e) {
			System.out.println(errMsg);
			return null;
		}
	}

	public static ArrayList<Student> debugCreateStudents() {

		ArrayList<Student> stList = new ArrayList<Student>();

		stList.add(new Student("Sara", 1));
		stList.add(new Student("Joe", 2));
		stList.add(new Student("DAVE", 5));
		
		for (int i = 0; i < 10; i++) {
			Student temp = new Student("Legion", i);
			stList.add(temp);
		}
		return stList;
	}

	public static void main(String[] args) {

		CourseRegApp app = new CourseRegApp();
		
		ArrayList<Student> stList= debugCreateStudents();
		
		CourseCat cat = new CourseCat(); // This loads the courses from our "DB"

		// initializing course with default sections for testing
		for (Course myCourse : cat.getCourseList()) {
			if (myCourse != null) {
				cat.createOffering(myCourse, 1, 200);
				cat.createOffering(myCourse, 2, 150);
				cat.createOffering(myCourse, 3, 8);
				cat.createOffering(myCourse, 4, 1);
			}

		}
		
		stList.get(0).registerForCourse(cat, "ENGG", 233, 1); // initial student registration for base requirement outputs Sare
		stList.get(0).registerForCourse(cat, "PHYS", 259, 2);

		stList.get(1).registerForCourse(cat, "ENGG", 233, 3); // Joe
		stList.get(1).registerForCourse(cat, "ENSF", 607, 4);

		for (int i = 1; i < 9; i++) { // testing max allowable courses per student
			stList.get(3).registerForCourse(cat, "WACK", Integer.parseInt("10" + i), 1); //DAVE
		}

		for (Student st : stList) { // testing min/max studentcap outputs for everyone.
			st.registerForCourse(cat, "WACK", 102, 3);
			st.registerForCourse(cat, "ENSF", 607, 1);
		}

		// testing with sara as current user. This would ideally be where the sign in page sends the user info into the front end.
		// NOTE: success/failure messages not blocked during initialization for debug and demonstration.
		app.Menu(cat, stList.get(0));
	}

}