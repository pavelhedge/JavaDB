package Java.test;

import java.util.ArrayList;
import java.util.List;

class TeacherDAO implements DAO<Teacher>{
    public Teacher get(int ID){
        Teacher teacher = new Teacher(1,"J", "J");
        return teacher;
    }
    public List<Teacher> getAll(){
        List<Teacher> list = new ArrayList();
        return list;
    }
    public void save(Teacher teacher){

    }
    public void update(int ID, String[] params){

    }
    public void delete(int ID){

    }

}

public class Teacher extends DAOPerson {
    Teacher(int id, String name, String surname){
        super(id,name,surname);
    }


}
