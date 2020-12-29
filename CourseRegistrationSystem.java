//This is the class where main is called. It's where information is serialized and deserialized, and where users are created.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class CourseRegistrationSystem {
	
	public static ArrayList<Course> allCourses = new ArrayList<Course>();
	public static ArrayList<Student> allStudents = new ArrayList<Student>();
	public static User user;
	public static Scanner input;
	
	public static void main(String args []) {		
		
		try {
			input = new Scanner(System.in);
			deserialize();
			welcome();	
			user.askMenu();
		}
		//make sure info is serialized and updated no matter what errors happen
		catch(Exception e) {
			System.out.println("\nAn error has occurred.");
			e.printStackTrace();
			exit();
		}

	}
	
	public static void deserialize() {	
		try{
		    FileInputStream fisCourse = new FileInputStream("Course.ser");
		    FileInputStream fisStudent = new FileInputStream("Student.ser");
		   
		    ObjectInputStream oisCourse = new ObjectInputStream(fisCourse);
		    ObjectInputStream oisStudent = new ObjectInputStream(fisStudent);
		    
		    allCourses = (ArrayList<Course>) oisCourse.readObject();
		    	
		    //Try in case no students have been created yet
		    try{
		    	allStudents = (ArrayList<Student>) oisStudent.readObject();
		    }
			catch(IOException ioe) {}
		     
		    oisCourse.close();
		    oisStudent.close();
		    fisCourse.close();
		    fisStudent.close();
		}
		catch(FileNotFoundException ex) {
			//In the first run, .ser files will not exist so program will
			//come here and read from the .csv file
			readCsv();
		}
		catch(ClassNotFoundException cnfe) {
			System.out.println("Class type does not match");
			cnfe.printStackTrace();
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void serialize() {
		try {
			//Write data to files
			FileOutputStream fosCourse = new FileOutputStream("Course.ser");
			FileOutputStream fosStudent = new FileOutputStream("Student.ser");
							
			//Writes objects to a stream (A sequence of data)
			ObjectOutputStream oosCourse = new ObjectOutputStream(fosCourse);
			ObjectOutputStream oosStudent = new ObjectOutputStream(fosStudent);
			
			//Writes the specific object to the OOS
			oosCourse.writeObject(allCourses);
			oosStudent.writeObject(allStudents);
							
			//Close both streams
			oosCourse.close();
			oosStudent.close();
			fosCourse.close();
			fosStudent.close();
		} 
		catch (IOException ioe) {
			ioe.printStackTrace();
		}		
	}
	
	public static void readCsv() {
		String fileName = "MyUniversityCourses.csv";
		String line = null;	
		
		try{
			FileReader fileReader = new FileReader(fileName);			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
		
			bufferedReader.readLine(); //Skip the first line because it's a heading
			
			while((line = bufferedReader.readLine()) != null) {
				line = line.replace("0,NULL", "");
				//Split each line into tokens
				StringTokenizer strTokens = new StringTokenizer(line, ",");

				while (strTokens.hasMoreTokens()){		
					//Use token information to create courses
					Course a = new Course();
					a.setName(strTokens.nextToken());
					a.setCourseId(strTokens.nextToken());
					a.setMaxStudents(Integer.parseInt(strTokens.nextToken()));
					a.setInstructor(strTokens.nextToken());
					a.setSection(Integer.parseInt(strTokens.nextToken()));
					a.setLocation(strTokens.nextToken());
					allCourses.add(a);
				}
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + fileName + "'");
			ex.printStackTrace();
		}
		catch (IOException ex) {
			System.out.println( "Error reading file '" + fileName + "'");
			ex.printStackTrace();
		}
	}
	
	public static void welcome() {
		System.out.print("Welcome to the Course Registration System."
				+ "\n\nPlease select one of the following actions by entering the number."
				+ "\n1. Log in as admin \n2. Log in as student \n0. Exit the program"
				+ "\nInput: ");
		
		int nextAction = input.nextInt();
		
		switch (nextAction) {
			case 1: login(new Admin(input)); break;
			case 2: if (allStudents.size() == 0) {
						System.out.println("There are no students registered yet. "
								+ "An admin must first register a student to log in as a student.\n");
						welcome();
					}
					else {
						login(new Student(input, false)); //Create a dummy student so you can log in
					}
					break;
			case 0: exit(); break;
		}
	}
	
	public static void login(Admin admin) {
		System.out.println("\nPlease enter your log in information.");
		System.out.print("username: ");
		String username = input.next();
		System.out.print("password: ");
		String password = input.next();
		
		if (username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
			System.out.println("You have logged in.");
			user = admin;
		}
		else {
			System.out.println("Username or password is incorrect. Try again.");
			login(admin);
		}
	}
	
	public static void login(Student student) {
		System.out.println("\nPlease enter your log in information.");
		System.out.print("username: ");
		String username = input.next();
		System.out.print("password: ");
		String password = input.next();
		
		//Match username to Student object, then set user as the real Student
		boolean match = false;
		for (Student s : allStudents) {
			if (s.getUsername().equals(username)) {
				user = s;
				match = true;
			}
		}
			
		//If no match is found, repeat log in
		if (!match) {
			System.out.println("This username does not exist. Try again.");
			login(student);
		}
		
		if (((Student)user).getPassword().equals(password)) {
			System.out.println("You have logged in.");
			
			//If it's a student's first time logging in, student resets his/her own log in info
			if (((Student)user).firstTime) {
				System.out.println("\nChange the log in information to something of your own");
				((Student)user).firstTime = false;
				changeLoginInfo();
			}
		}
	
		//If password is incorrect, repeat log in
		else {
			System.out.println("Username or password is incorrect. Try again.");
			login(student);
		}
	}
	
	public static void changeLoginInfo() {
		System.out.print("Create a new username (no spaces): ");
		String newUsername = input.next();
		
		//Check to make sure all usernames are unique
		if (user instanceof Student) {
			for (Student curr : allStudents) {
				if (curr.getUsername().equals(newUsername)) {
					System.out.println("This username already exists. Please enter a different one.");
					changeLoginInfo();
					return; //Need to break out of the method, otherwise it'll continue from here after I call the method again
				}
			}
		}

		System.out.print("Create a new password (no spaces): ");
		String newPassword = input.next();
		
		user.setPassword(newPassword);
		user.setUsername(newUsername);
		
		System.out.println("You have changed your username and password.");
	}
	
	public static void exit() {
		serialize();
		System.out.println("\nYou've exited the program.");
		System.exit(0);
	}
}
