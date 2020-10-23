package Java.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Java.test.DbObject.getConnection;

public interface DAO<T extends DbObject> {

    T get(int ID) throws SQLException;
    T getByName(String name)throws SQLException;
    List<T> getAll() throws SQLException;
    void create(T t) throws SQLException;
    void update(int ID, T t) throws SQLException;
    void delete(int ID) throws SQLException;
}

class DbObject{

    static Connection connection;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String user = "root";
    private static final String password = "";

    static Connection getConnection(){
        if (connection == null){
            try {
                connection = DriverManager.getConnection(url, user, password);
            }catch (SQLException e){
                System.out.println("Can't establish SQL connection");
                e.printStackTrace();
            }
        }
        return connection;
    }

    private int id = 0;
    public String name;

    public DbObject(){}
    public DbObject(String name){
        this.name = name;
    }
    DbObject(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getID(){
        return this.id;
    }
}

abstract class DbPerson extends DbObject{
    public String surname;
    public DbPerson(int id, String name, String surname){
        super(id, name);
        this.surname = surname;
    }
    public DbPerson(String name, String surname){
        super(name);
        this.surname = surname;
    }
}
