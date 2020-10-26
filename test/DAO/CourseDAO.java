package test.DAO;

import test.Course;
import test.Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static test.DbObject.getConnection;

public class CourseDAO implements DAO<Course> {

    private ResultSet resultSet;
    private PreparedStatement getStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement getByYearStmt;
    private PreparedStatement getByTeacherStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public CourseDAO() {
        Connection connection = getConnection();
        String getQuery = "SELECT * FROM courses WHERE ID = ?";
        String getByNameQuery = "SELECT * FROM courses WHERE name = ?";
        String getByYearQuery = "SELECT * FROM courses WHERE year = ?";
        String getByTeacherQuery = "SELECT * FROM courses WHERE teacherID = ?";
        String getAllQuery = "SELECT * FROM courses";
        String createQuery = "INSERT INTO courses (name, teacherID, year) VALUES (?,?,?)";
        String updateQuery = "UPDATE courses SET name = ?, teacherID = ?, year = ? where ID = ?";
        String deleteQuery = "DELETE FROM courses WHERE ID = ?";

        try {
            getStmt = connection.prepareStatement(getQuery);
            getByNameStmt = connection.prepareStatement(getByNameQuery);
            getByYearStmt = connection.prepareStatement(getByYearQuery);
            getByTeacherStmt = connection.prepareStatement(getByTeacherQuery);
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
            getByYearStmt.close();
            getByTeacherStmt.close();
            getAllStmt.close();
            createStmt.close();
            updateStmt.close();
            deleteStmt.close();
        } catch(SQLException e){e.printStackTrace();}
    }

    public Course form() {

        while (true) {
            BufferedReader reader = Main.getReader();
            try {
                System.out.println("Enter name");
                String name =reader.readLine();
                if (name.equals("")) throw new NumberFormatException();
                System.out.println("Enter teacher ID");
                int teacherID = Integer.parseInt(reader.readLine());
                System.out.println("Enter year");
                int year = Integer.parseInt(reader.readLine());
                reader.close();
                return new Course(name, teacherID, year);
            } catch (NumberFormatException e) {
                System.out.println("Wrong input. Try again?");
                try {
                    String input =reader.readLine();
                    if (input.length() == 0 || input.charAt(0) != 'y') {
                        reader.close();
                        return null;
                    }
                } catch (IOException i) {
                }
            } catch (IOException e) {
            }
        }
    }

    public Course get(int ID) throws SQLException{

        getStmt.setInt(1, ID);
        resultSet = getStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int teacherID = resultSet.getInt("teacherID");
            int year = resultSet.getInt("year");

            return new Course(id, name, teacherID, year);
        }
        else return null;
    }

    public Course getByName(String name) throws SQLException{

        getByNameStmt.setString(1, name);
        resultSet = getByNameStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int teacherID = resultSet.getInt("teacherID");
            int year = resultSet.getInt("year");

            return new Course(id, name, teacherID, year);
        }
        else return null;
    }

    public List<Course> getByYear(int year) throws SQLException
    {
        List<Course> list = new ArrayList<>();
        getByYearStmt.setInt(1, year);
        resultSet = getByYearStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int teacherID = resultSet.getInt("TeacherID");
            list.add(new Course(id,name,teacherID,year));
        }
        return list;
    }
    public List<Course> getByTeacher(int teacherID) throws SQLException
    {
        List<Course> list = new ArrayList<>();
        getByTeacherStmt.setInt(1, teacherID);
        resultSet = getByTeacherStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int year = resultSet.getInt("year");
            list.add(new Course(id,name,teacherID, year));
        }
        return list;
    }


    public List<Course> getAll() throws SQLException{
        resultSet = getAllStmt.executeQuery();
        List<Course> list = new ArrayList();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int teacherID = resultSet.getInt("teacherID");
            int year = resultSet.getInt("year");
            list.add(new Course(id, name, teacherID, year));
        }
        return list;
    }
    public void create(Course course) throws SQLException{
        createStmt.setString(1,course.getName());
        createStmt.setInt(2, course.getTeacherID());
        createStmt.setInt(3, course.getYear());
        createStmt.executeUpdate();
    }
    public void update(int id, Course course) throws SQLException{
        updateStmt.setInt(4, id);
        updateStmt.setString(1,course.getName());
        updateStmt.setInt(2, course.getTeacherID());
        createStmt.setInt(3, course.getYear());

        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}

