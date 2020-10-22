package Java.test;

import java.util.List;

public interface DAO<T extends DAOObject> {
    <T> T get(int ID);
    List<T> getAll();
    void save(T t);
    void update(int ID, String[] params);
    void delete(int ID);
}

abstract class DAOObject{
    public int id;
    public String name;

    public DAOObject(){};
    public DAOObject(int id, String name){
        this.id = id;
        this.name = name;
    }
}

abstract class DAOPerson extends DAOObject{
    public String surname;
    public DAOPerson(int id, String name, String surname){
        super(id, name);
        this.surname = surname;
    }
}
