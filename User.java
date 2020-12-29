//User is a superclass of Admin and Student. It includes the abstract method askMenu()
//and includes other fields and variables that its subclasses can use.

import java.util.Scanner;

abstract class User implements java.io.Serializable {
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	static Scanner input; //must be static because Scanner does not implement Serializable,
						  //otherwise Student is not serializable
	protected User(Scanner in) {
		/*We pass the scanner so that we're just working with one scanner
		which originates in the CourseRegistrationSystem class*/
		input = in;
	}
	
	abstract void askMenu();
	
	public void viewAllCourses() {
		System.out.println("\nAll courses");
		
		for (Course c : CourseRegistrationSystem.allCourses) {
			System.out.println(c.courseInfo());
		}
	}
	
	public void viewFullCourses() {
		System.out.println("\nFull courses");
		boolean someFull = false;
		
		for (Course c : CourseRegistrationSystem.allCourses)	
			if (c.isFull()) {
				someFull = true;
				System.out.println(c.courseInfo());
			}
		
		//Let user know if there are no full courses
		if (!someFull)
			System.out.println("\nThere are no full courses.");
	}
	
	public void viewNotFullCourses() {
		System.out.println("\nCourses not full");
		int fullCount = CourseRegistrationSystem.allCourses.size();
		
		for (Course c : CourseRegistrationSystem.allCourses)	
			if (!c.isFull()) {
				System.out.println(c.courseInfo());
				fullCount--;
			}
		
		//Let user know if there are no full courses
		if (fullCount == CourseRegistrationSystem.allCourses.size())
			System.out.println("\nAll courses are full");
	}
	
	//Asks the user for the course ID and section number, then returns that Course object
	public Course findCourse() {
		//Prompt the user
		System.out.print("Enter the course ID: ");
		String id = input.next();
		System.out.print("Enter the course section number: ");
		int section = input.nextInt();
		
		//Return the course if found
		for (Course curr: CourseRegistrationSystem.allCourses)
			if (curr.getCourseId().equals(id) && curr.getSection() == section) {
				return curr;
			}
		
		//Return null if course is not found
		System.out.println("Course not found. Try again.");
		return findCourse(); //findCourse() returns the Student object here, and you'll have to return it again to get out of the method
	}
	
	//Asks the user for the student's first and last name, then returns that Student object
	public Student findStudent() {
		//Prompt the user
		System.out.print("Enter the first name: ");
		String first = input.next();
		System.out.print("Enter the last name: ");
		String last = input.next();
		
		//Return the course if found
		for (Student curr: CourseRegistrationSystem.allStudents)
			if (curr.getFirstName().equals(first) && curr.getLastName().equals(last)) {
				return curr;
			}
		
		//Return null if Student is not found
		System.out.println("Student not found. Try again.");
		return findStudent();
	}
	
	
	//Getters and setters

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLastName() {
		return lastName;
	}

	
}
