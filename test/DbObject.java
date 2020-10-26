package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbObject {

    static Connection connection;

    private static final String url = "jdbc:mysql://127.0.0.1:5778/test";
    private static final String user = "root";
    private static final String password = "12345";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                System.out.println("Can't establish SQL connection");
                e.printStackTrace();
            }
        }
        return connection;
    }

    int ID = 0;
    String name;

    public String getName() { return name; }

    public int getID() {
        return this.ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public DbObject() {
    }

    public DbObject(String name) {
        this.name = name;
    }

    DbObject(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }


}
