//A Course has course information and a list of the students registered to that course

import java.util.ArrayList;

public class Course implements java.io.Serializable, Comparable<Course>{

	private String name;
	private String courseId;
	private int maxStudents;
	private ArrayList<Student> studentList = new ArrayList<>();
	private String instructor;
	private int section;
	private String location;
	
	public Course() {}
	
	//Compares to Courses by the number of students enrolled in each
    public int compareTo(Course other) {
        return ((Integer)studentList.size()).compareTo((Integer)other.studentList.size());
    }
	
    public String courseInfo() {
		return "\n" + name + " Section " +  section
				+ "\nCourse ID: " + courseId
				+ "\nMaximum number of students: " + maxStudents
				+ "\nCurrent number of students: " + studentList.size()
				+ "\nInstructor: " + instructor
				+ "\nLocation: " + location;
    }
    
    //Returns the student information for the course
	public String courseStudentInfo() {
		
		//List of student names
		ArrayList<String> namesList = new ArrayList<>();
		for (Student s : studentList) {
			String name = s.getFirstName() + " " + s.getLastName();
			namesList.add(name);
		}
		
		String nameStudent = "\nEnrolled students: " + namesList;
		
		//List of student IDs
		ArrayList<Integer> idList = null;
		//try catch in case no students exist yet
		try {
			for (Student student : studentList)
				idList.add(student.getStudentId());
		} catch (RuntimeException e) {}
		String ids = "\nEnrolled students' IDs: " + idList;
		
		String numberRegistered = "\nNumber of students registered: " + studentList.size();
		String maxRegistered = "\nMaximum number of students allowed: " + maxStudents;
	
		return "\n" + name + " Section " +  section + nameStudent + ids
				+ numberRegistered + maxRegistered + "\n";
	}
	
	void addStudent(Student s) {
		studentList.add(s);
	}
	
	void deleteStudent(Student s) {
		//Must first find the student object in the course by matching the username
		Student match = null;
		for (Student curr : studentList) {
			if (curr.getUsername().equals(s.getUsername()))
				match = curr;
		}
		
		int index = studentList.indexOf(match);
		studentList.remove(index);
	}
	
	//Even if the user is a student, the user is a different object from the corresponding student on the studentList.
	//That's why we compare the usernames instead of using studentList.contains(Student)
	boolean hasStudent(Student s) {
		for (Student curr : studentList) {
			if (curr.getUsername().equals(s.getUsername()))
				return true;
		}
		return false;
	}
	
	boolean isFull() {
		return studentList.size() == maxStudents;
	}
	
	//Getters and setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	

	public int getMaxStudents() {
		return maxStudents;
	}
	
	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
	

	public ArrayList<Student> getStudentList() {
		return studentList;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
