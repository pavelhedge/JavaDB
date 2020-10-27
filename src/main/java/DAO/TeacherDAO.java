package src.main.java.DAO;

import src.main.java.Teacher;
import src.main.java.DbObject;

import static src.main.java.Main.getReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class TeacherDAO implements DAO<Teacher>{

    private ResultSet resultSet;
    private PreparedStatement getStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public TeacherDAO() {
        Connection connection = DbObject.getConnection();
        String getQuery = "SELECT * FROM teachers WHERE ID = ?";
        String getByNameQuery = "SELECT * FROM teachers WHERE name = ?";
        String getAllQuery = "SELECT * FROM teachers";
        String createQuery = "INSERT INTO teachers (name, surname) VALUES (?,?)";
        String updateQuery = "UPDATE teachers SET name = ?, surname = ? where ID = ?";
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
        BufferedReader reader = getReader();
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
        createStmt.setString(1,teacher.getName());
        createStmt.setString(2,teacher.getSurname());
        createStmt.executeUpdate();
    }
    public void update(int id, Teacher teacher) throws SQLException{
        updateStmt.setInt(3, id);
        updateStmt.setString(1,teacher.getName());
        updateStmt.setString(2,teacher.getSurname());
        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}