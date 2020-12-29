import java.util.ArrayList;

interface StudentInterface {
	ArrayList<Course> courses = new ArrayList<>();
	int studentId = 0;
	int studentCount = 0;
	boolean firstTime = true;
	
	//Print the menu and receive input
	void askMenu();
	
	//Menu functions
	void registerInCourse();
	void withdrawFromCourse();
	void viewRegisteredCourses();
	
	//Getters and setters
	void setStudentId(int studentId);
	int getStudentId();

}

