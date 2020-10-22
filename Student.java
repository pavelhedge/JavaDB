package Java.test;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

class StudentDAO implements DAO<Student>{
    public Student get(int ID){
        PreparedStatement getStudent = con.prepareStatement
        Student student = new Student(1,"Jean", "Jackson", 1);
        return student;
    }
    public List<Student> getAll(){
        List<Student> list = new ArrayList();
        return list;
    }
    public void save(Student student){

    }
    public void update(int ID, String[] params){

    }
    public void delete(int ID){

    }

}

public class Student extends DAOPerson {
    int courseID;

    public Student(int id, String name, String surname, int courseID){
        super(id, name, surname);
        this.courseID = courseID;
    }
}
