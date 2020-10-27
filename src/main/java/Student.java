package src.main.java;

public class Student extends DbPerson {


    int courseID;

    public int getCourseID() { return courseID; }

    public Student(int id, String name, String surname, int courseID) {
        super(id, name, surname);
        this.courseID = courseID;
    }

    public Student(String name, String surname, int courseID){
        super(name, surname);
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "" + this.name + " " + this.surname + ", ID: " + this.getID() + ", CourseID: " + this.courseID;
    }
}
