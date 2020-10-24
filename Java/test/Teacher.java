package Java.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Java.test.DbObject.getConnection;

class TeacherDAO implements DAO<Teacher>{

    private ResultSet resultSet;
    private PreparedStatement getStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    TeacherDAO() {
        Connection connection = getConnection();
        String getQuery = "SELECT * FROM teachers WHERE ID = ?";
        String getByNameQuery = "SELECT * FROM teachers WHERE name = ?";
        String getAllQuery = "SELECT * FROM teachers";
        String createQuery = "INSERT INTO teachers (name, surname, courseID) VALUES (?,?,?)";
        String updateQuery = "UPDATE teachers SET name = ?, surname = ?, courseid = ? where ID = ?";
        String deleteQuery = "DELETE FROM teachers WHERE ID = ?";

        try {
            getStmt = connection.prepareStatement(getQuery);
            getByNameStmt = connection.prepareStatement(getByNameQuery);
            getAllStmt = connection.prepareStatement(getAllQuery);
            createStmt = connection.prepareStatement(createQuery);
            updateStmt = connection.prepareStatement(updateQuery);
            deleteStmt = connection.prepareStatement(deleteQuery);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void finalize(){
        try{
            getStmt.close();
            getByNameStmt.close();
            getAllStmt.close();
            createStmt.close();
            updateStmt.close();
            deleteStmt.close();
        } catch(SQLException e){e.printStackTrace();}
    }

    public Teacher form() {
        BufferedReader reader = Main.getReader();
        while (true) {
            try {
                System.out.println("Enter name");
                String name = reader.readLine();
                if (name.equals("")) throw new NumberFormatException();
                System.out.println("Enter surname");
                String surname = reader.readLine();
                if (surname.equals("")) throw new NumberFormatException();
                reader.close();
                return new Teacher(name, surname);
            } catch (IOException e) {
            } catch (NumberFormatException e) {
                System.out.println("Wrong input. Try again?");
                try {
                    String input = reader.readLine();
                    if (input.length() == 0 || input.charAt(0) != 'y') {
                        reader.close();
                        return null;
                    }
                } catch (IOException i) {
                }
            }

        }
    }

    public Teacher get(int ID) throws SQLException{

        getStmt.setInt(1, ID);
        resultSet = getStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");

            return new Teacher(id, name, surname);
        }
        else return null;
    }

    public Teacher getByName(String name) throws SQLException{

        getByNameStmt.setString(1, name);
        resultSet = getByNameStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String surname = resultSet.getString("surname");

            return new Teacher(id, name, surname);
        }
        else return null;
    }

    public List<Teacher> getAll() throws SQLException{
        resultSet = getAllStmt.executeQuery();
        List<Teacher> list = new ArrayList<>();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            list.add(new Teacher(id, name, surname));
        }
        return list;
    }
    public void create(Teacher teacher) throws SQLException{
        createStmt.setString(1,teacher.name);
        createStmt.setString(2,teacher.surname);
        createStmt.executeUpdate();
    }
    public void update(int id, Teacher teacher) throws SQLException{
        updateStmt.setInt(3, id);
        updateStmt.setString(1,teacher.name);
        updateStmt.setString(2,teacher.surname);
        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}


public class Teacher extends DbPerson {

    public Teacher(int id, String name, String surname) {
        super(id, name, surname);
    }

    public Teacher(String name, String surname){
        super(name, surname);
    }

    @Override
    public String toString() {
        return "" + this.name + " " + this.surname + ", ID: " + this.getID();
    }
}
