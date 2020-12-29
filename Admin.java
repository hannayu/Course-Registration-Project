//Admin is a subclass of the User class and always has the username admin1 and password password1

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Admin extends User implements AdminInterface {
	
	public Admin(Scanner in) {
		super(in);
		username = "admin1";
		password = "password1";
		firstName = "Albus";
		lastName = "Dumbledore";
	}
	
	//Print the menu, then complete that action depending on the input
	public void askMenu() {
		System.out.print("\nChoose a menu to display. \n1. Course Management \n2. Reports \n0. Exit the program \nInput: ");
		int menu = -1;
		if (input.hasNextInt())
			menu = input.nextInt();
		if (menu == 1) {
			System.out.println("\nCourse Management \n1. Create a new course \n2. Delete a course \n3. Edit a course"
					+ "\n4. Display information for a given course \n5. Register a student \n6. Back to menu options \n0. Exit the program");
			System.out.print("Input: ");
			switch (input.nextInt()) {
				case 1: createCourse(); askMenu(); break;
				case 2: deleteCourse(); askMenu(); break;
				case 3: editCourse(); askMenu(); break;
				case 4: displayCourse(); askMenu(); break;
				case 5: registerStudent(); askMenu(); break;
				case 6: askMenu(); break;
				case 0: CourseRegistrationSystem.exit();
			}
		}
		else if (menu == 2) {
			System.out.println("\nReports\n1. View all courses \n2. View full courses \n3. Write full courses to a file"
					+ "\n4. View students for a given course \n5. View registered courses for a given student"
					+ "\n6. Sort courses based on the current number of students registered \n7. Back to menu options \n0. Exit the program");
			System.out.print("Input: ");
			switch (input.nextInt()) {
				case 1: viewAllCourses(); askMenu(); break;
				case 2: viewFullCourses(); askMenu(); break;
				case 3: writeFullFile(); askMenu(); break;
				case 4: viewStudentsInCourse(); askMenu(); break; //still need to check when multiple students are enrolled
				case 5: viewCoursesForStudent(); askMenu(); break;
				case 6: sortByNumStudents(); askMenu(); break;
				case 7: askMenu(); break;
				case 0: CourseRegistrationSystem.exit();
			}
		}
		else
			CourseRegistrationSystem.exit();
	}
	
	
	//Course management functions
	
	public void createCourse() {
		Course c = new Course();
		CourseRegistrationSystem.allCourses.add(c);
		
		System.out.println("\nCreate a course ");		
		//Prompt input information to create a course
		System.out.print("Enter the course name: ");
		input.nextLine();
		c.setName(input.nextLine()); //use nextLine() to account for spaces in the name
		System.out.print("Enter the course ID: ");
		c.setCourseId(input.next());
		System.out.print("Enter the maximum number of students: ");
		c.setMaxStudents(Integer.parseInt(input.next()));
		System.out.print("Enter the course instructor: ");
		input.nextLine();
		c.setInstructor(input.nextLine());
		System.out.print("Enter the course section: ");
		c.setSection(Integer.parseInt(input.next()));
		System.out.print("Enter the course location: ");
		input.nextLine();
		c.setLocation(input.nextLine());
		
		if (CourseRegistrationSystem.allCourses.contains(c))
			System.out.println("You've successfully created a course for " + c.getName());
	}
	
	public void deleteCourse() {
		System.out.print("\nDelete a course\nEnter the course ID: ");
		String id = input.next();
		
		//Have to use iterator for this
		//We look for the course, then delete it
		Iterator<Course> iterator = CourseRegistrationSystem.allCourses.iterator();
		while(iterator.hasNext()) {
			Course curr = iterator.next();
			if (curr.getCourseId().contentEquals(id)) {
				iterator.remove();
			}
		}
		
		System.out.println("You've deleted " + id + " from the list of existing courses.");
	}
	
	public void editCourse() {
		System.out.println("\nEdit a course ");
		
		//Find the course
		Course c  = findCourse();
		
		//Print menu for info to edit
		System.out.print("\nEditing " + c.getName());
		System.out.print("\nSelect what you'd like to edit."
				+ "\n1. Maximum number of students \n2. Course instructor"
				+ "\n3. Course section \n4. Course location");
		System.out.print("\nInput: ");
		
		//Editing each type of information
		switch (input.nextInt()) {
			//Edit the maximum number of registered students
			case 1: {
				int i = c.getMaxStudents();
				System.out.print("\nThe current maximum number of students is " + i
						+ ".\nPlease enter the new maximum number: ");
				int newMax = input.nextInt();
				c.setMaxStudents(newMax);
				if (c.getMaxStudents() == newMax)
					System.out.println("The maximum number of students for " + c.getName() + " Section " + c.getSection()
							+ " is now " + newMax + ".");
			} break;
			//Edit the instructor
			case 2: {
				String i = c.getInstructor();
				System.out.print("\nThe current instructor is " + i
						+ ".\nPlease enter the new instructor's name: ");
				
				//Get the line separator stuff right because next() will stop at a space
				input.nextLine();
				String newInstructor = input.nextLine();
				
				c.setInstructor(newInstructor);
				if (c.getInstructor().equals(newInstructor))
					System.out.println("The instructor for " + c.getName() + " Section " + c.getSection()
							+ " is now " + newInstructor + ".");
			} break;
			//Edit the section
			case 3:  {
				int i = c.getSection();
				System.out.print("\nThe current section number is " + i
						+ ".\nPlease enter the new section number: ");
				int newSection = input.nextInt();
				c.setSection(newSection);
				if (c.getSection() == newSection)
					System.out.println("The section for " + c.getName() + " Section " + i
							+ " is now " + newSection + ".");
			} break;
			//Edit the location
			case 4: {
				String i = c.getLocation();
				System.out.print("\nThe current location is " + i
						+ ".\nPlease enter the new location's address: ");
				
				//Get the line separator stuff right
				input.nextLine();
				String newLocation = input.nextLine();
				
				c.setLocation(newLocation);
				if (c.getLocation().equals(newLocation))
					System.out.println("The location for " + c.getName() + " Section " + c.getSection()
							+ " is now " + newLocation + ".");
			} break;
		}
	}	
	
	public void displayCourse() {
		System.out.println("\nDisplay a course");
		
		//Find the course
		Course c  = findCourse();
		
		//Print the course information
		System.out.println(c.courseInfo());
	}
	
	public void registerStudent() {
		Student s = new Student(input);
		CourseRegistrationSystem.allStudents.add(s);
		
		System.out.println("\nRegister a student ");		
		
		//Prompt input information to create a course
		System.out.print("Enter the student ID: ");
		s.setStudentId(input.nextInt());
		System.out.print("Enter the first name: ");
		s.setFirstName(input.next());
		System.out.print("Enter the last name: ");
		s.setLastName(input.next());
		
		if (CourseRegistrationSystem.allStudents.contains(s)) {
			System.out.println("You've successfully registered "
					+ s.getFirstName() + " " + s.getLastName());
			System.out.println("This is the log in information for " + s.getFirstName()
					+ " " + s.getLastName() + ".");
			System.out.println("username: " + s.username);
			System.out.println("password: " + s.password);
		}	
	}
	
	
	//Reports functions (some are in User class)
	
	//Overrides the method in the user class which displays a course's regular info
	//This view all displays every course's list of students and their student IDs
	public void viewAllCourses() {
		System.out.println("\nAll courses");
		
		for (Course c : CourseRegistrationSystem.allCourses)
			System.out.print(c.courseStudentInfo());
	}

	public void writeFullFile() {
		String fileName = "Full_Courses_List.txt";

		try{
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			boolean someFull = false;
			
			//Print all the courses in a reader friendly format
			bufferedWriter.write("List of full courses");
			bufferedWriter.newLine();
			bufferedWriter.newLine();
			for (Course c : CourseRegistrationSystem.allCourses) {	
				if (c.isFull()) {
					someFull = true;
					
					//Get the course information
					String info = c.courseStudentInfo();
					
					//Split the string with delimitor because the .write method
					//doesn't recognize I'm trying to make a new line
					String[] infoList = info.split("\n");
					for (String piece : infoList) {
						bufferedWriter.write(piece);
						bufferedWriter.newLine();
					}
					bufferedWriter.newLine();
				}
			}
			
			//Let the user know if there are no full courses
			if (!someFull)
				bufferedWriter.write("There are no full courses.");
			
			bufferedWriter.close();			
			System.out.println("\nFull courses have been written to Full_Courses_List.txt");
		}
		catch (IOException exk) {
			System.out.println( "Error writing file '" + fileName + "'");
			exk.printStackTrace();
		}
	}
	
	public void viewStudentsInCourse() {
		System.out.println("\nView students enrolled in a course");

		//Find the course
		Course c  = findCourse();
		
		System.out.println("\nStudents for " + c.getName() + " Section " + c.getSection());
		
		try {
			for (Student s : c.getStudentList())
				System.out.println(s.getFirstName() + " " + s.getLastName());
		} catch (RuntimeException e) {
			System.out.println("No students are enrolled for this course.");
		}
	}

	public void viewCoursesForStudent() {
		System.out.println("\nView the list of courses a student is enrolled in");
		
		Student s  = findStudent();
		
		System.out.println("\nCourses for " + s.getFirstName() + " " + s.getLastName());
		boolean someRegistered = false;

		for (Course c : CourseRegistrationSystem.allCourses) {
			if (c.hasStudent(s)) {
				System.out.println(c.courseInfo());
				someRegistered = true;
			}
		}
		
		//Let user know if student has not registered in any courses
		if (!someRegistered) {
			System.out.println(s.getFirstName() + " " + s.getLastName() + " isn't registered in any courses yet.");
		}
	}
	
	public void sortByNumStudents() {
		Collections.sort(CourseRegistrationSystem.allCourses);
		viewAllCourses();

	}
	
}
