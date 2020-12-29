//Student is a subclass of User. It is serializable and can perform various course management functions.

import java.util.Scanner;

public class Student extends User implements java.io.Serializable, StudentInterface {
	public int studentId;
	public boolean firstTime;
	
	public Student(Scanner in) {
		super(in);
		
		//Ensure a unique username at creation
		username = "student" + (CourseRegistrationSystem.allStudents.size() + 1);
		password = "password" + (CourseRegistrationSystem.allStudents.size() + 1);
		
		//The first time a student logs in, firsTime will be true, so the student will reset his/her own log in info
		firstTime = true; 
	}
	
	public Student(Scanner in, boolean real) {
		super(in);
	}
	
	//Print the menu, then complete that action depending on the input
	public void askMenu() {
		System.out.println("\nCourse Management \n1. View all courses \n2. View all courses that are not full \n3. Register in a course"
				+ "\n4. Withdraw from a course \n5. View my registered courses \n0. Exit the program");
		System.out.print("Input: ");
		
		switch (input.nextInt()) {
			case 1: viewAllCourses(); askMenu(); break; //method defined in User class
			case 2: viewNotFullCourses(); askMenu(); break; //method defined in User class
			case 3: registerInCourse(); askMenu(); break;
			case 4: withdrawFromCourse(); askMenu(); break;
			case 5: viewRegisteredCourses(); askMenu(); break;
			case 0: CourseRegistrationSystem.exit(); break;
		}
	}
	
	//Menu functions
	
	public void registerInCourse() {
		System.out.println("\nRegister in a course");
		Course c = findCourse();
		
		//Don't register if the course is full
		if (c.isFull()) {
			System.out.println("Sorry, this course is full.");
			askMenu();
		}
		
		//Don't register if user is already registered in the course
		if (c.hasStudent(this)) {
			System.out.println("You are already registered in this course.");
			askMenu();
		}

		c.addStudent(this);
		if (c.getStudentList().contains(this));
			System.out.println("You've successfully registered in " + c.getName());
		
	}
	
	public void withdrawFromCourse() {
		System.out.println("\nWithdraw from a course");
		Course c = findCourse();
		
		//Check if the student already wasn't registered in the course
		if (!c.hasStudent(this)) {
			System.out.println("You are already not registered in this course.");
			askMenu();
		}
		
		c.deleteStudent(this);
		System.out.println("You've successfully withdrawn from " + c.getName());
	}
		
	public void viewRegisteredCourses() {
		System.out.println("\nRegistered Courses");
		boolean someRegistered = false;

		for (Course c : CourseRegistrationSystem.allCourses) {
			if (c.hasStudent(this)) {
				System.out.println(c.courseInfo());
				someRegistered = true;
			}
		}
		
		//Let user know if user has not registered in any courses
		if (!someRegistered) {
			System.out.println("You haven't registered in any courses yet.");
		}
	}
	
	
	//Getters and setters
	
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public int getStudentId() {
		return studentId;
	}

}
