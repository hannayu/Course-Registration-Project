interface AdminInterface {
	void askMenu();
	
	//Course management functions	
	void createCourse();	
	void deleteCourse();
	void editCourse();
	void displayCourse();
	void registerStudent();
	
	//Reports functions (some are in User class)
	void viewAllCourses();
	void writeFullFile();
	void viewStudentsInCourse();
	void viewCoursesForStudent();
	void sortByNumStudents();
}
