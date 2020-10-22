package Java.test;

import java.util.ArrayList;
import java.util.List;

class CourseDAO implements DAO<Course>{
    public Course get(int ID){
        Course course = new Course();
        return course;
    }
    public List<Course> getAll(){
        List<Course> list = new ArrayList();
        return list;
    }
    public void save(Course course){

    }
    public void update(int ID, String[] params){

    }
    public void delete(int ID){

    }

}

public class Course extends DAOObject {
    int id;
    String name;
    String surname;
    int courseID;

    public Course(){}
    public Course(int id, String name, String surname, int courseID){
        super(id, name);
        this.surname = surname;
        this.courseID = courseID;
    }
}
