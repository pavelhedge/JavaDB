package src.main.java;

public class Course extends DbObject {
    int teacherID;
    int year;

    public int getTeacherID() { return teacherID; }

    public int getYear() { return year; }

    public Course(int id, String name, int teacherID, int year) {
        super(id, name);
        this.teacherID = teacherID;
        this.year = year;
    }

    public Course(String name, int teacherID, int year){
        super(name);
        this.teacherID = teacherID;
        this.year = year;
    }

    @Override
    public String toString() {
        return "" + this.name + "," +this.year+" year. Teacher: " + this.teacherID + ", ID: " + this.getID();
    }
}