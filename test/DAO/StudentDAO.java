package test.DAO;

import test.Main;
import test.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static test.DbObject.getConnection;

public class StudentDAO implements DAO<Student>{

    private ResultSet resultSet;
    private PreparedStatement getStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement getByCourseStmt;
    private PreparedStatement getByYearStmt;
    private PreparedStatement getByTeacherStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    public StudentDAO() {
        Connection connection = getConnection();
        String getQuery = "SELECT * FROM students WHERE ID = ?";
        String getByNameQuery = "SELECT * FROM students WHERE name = ?";
        String getByCourseQuery = "SELECT * FROM students WHERE courseID = ?";
        String getByYearQuery = "SELECT * FROM students WHERE courseID IN (SELECT id FROM courses WHERE year = ?)";
        String getByTeacherQuery = "SELECT * FROM students WHERE courseID IN (SELECT id FROM courses WHERE teacherID = ?)";
        String getAllQuery = "SELECT * FROM students";
        String createQuery = "INSERT INTO students (name, surname, courseID) VALUES (?,?,?)";
        String updateQuery = "UPDATE students SET name = ?, surname = ?, courseid = ? where ID = ?";
        String deleteQuery = "DELETE FROM students WHERE ID = ?";

        try {
            getStmt = connection.prepareStatement(getQuery);
            getByNameStmt = connection.prepareStatement(getByNameQuery);
            getByCourseStmt = connection.prepareStatement(getByCourseQuery);
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
            getByCourseStmt.close();
            getByYearStmt.close();
            getByTeacherStmt.close();
            getAllStmt.close();
            createStmt.close();
            updateStmt.close();
            deleteStmt.close();
        } catch(SQLException e){e.printStackTrace();}
    }

    public Student form(){
        while(true){
            try {
                BufferedReader reader = Main.getReader();
                System.out.println("Enter name");
                String name = reader.readLine();
                if (name.equals("")) throw new NumberFormatException();
                System.out.println("Enter surname");
                String surname = reader.readLine();
                if (surname.equals("")) throw new NumberFormatException();
                System.out.println("Enter course ID");
                int courseID = Integer.parseInt(reader.readLine());
                return new Student(name, surname, courseID);
            }catch(NumberFormatException e) {
                System.out.println("Wrong input. Try again?");
                try {
                    String input = Main.getReader().readLine();
                    if (input.length() == 0 || input.charAt(0) != 'y')
                    {
                        return null;
                    }
                }catch(IOException i){}
            }catch(IOException e){}
        }
    }

    public Student get(int ID) throws SQLException{

        getStmt.setInt(1, ID);
        resultSet = getStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            int courseID = resultSet.getInt("courseID");

            return new Student(id, name, surname, courseID);
        }
        else return null;
    }

    public Student getByName(String name) throws SQLException{

        getByNameStmt.setString(1, name);
        resultSet = getByNameStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String surname = resultSet.getString("surname");
            int courseID = resultSet.getInt("courseID");

            return new Student(id, name, surname, courseID);
        }
        else return null;
    }


    public List<Student> getByCourse(int courseID)throws SQLException{
        List<Student> list = new ArrayList<>();
        getByCourseStmt.setInt(1,courseID);
        resultSet = getByCourseStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("Surname");
            list.add(new Student(id,name,surname,courseID));
        }
        return list;
    }
    public List<Student> getByYear(int year) throws SQLException
    {
        List<Student> list = new ArrayList<>();
        getByYearStmt.setInt(1, year);
        resultSet = getByYearStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("Surname");
            int courseID = resultSet.getInt("CourseID");
            list.add(new Student(id,name,surname,courseID));
        }
        return list;
    }
    public List<Student> getByTeacher(int teacherID) throws SQLException
    {
        List<Student> list = new ArrayList<>();
        getByTeacherStmt.setInt(1, teacherID);
        resultSet = getByTeacherStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("Surname");
            int courseID = resultSet.getInt("CourseID");
            list.add(new Student(id,name,surname,courseID));
        }
        return list;
    }


    public List<Student> getAll() throws SQLException{
        resultSet = getAllStmt.executeQuery();
        List<Student> list = new ArrayList();
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            int courseID = resultSet.getInt("courseID");
            list.add(new Student(id, name, surname, courseID));
        }
        return list;
    }
    public void create(Student student) throws SQLException{
        createStmt.setString(1,student.getName());
        createStmt.setString(2,student.getSurname());
        createStmt.setInt(3, student.getCourseID());
        createStmt.executeUpdate();
    }
    public void update(int id, Student student) throws SQLException{
        updateStmt.setInt(4, id);
        updateStmt.setString(1,student.getName());
        updateStmt.setString(2,student.getSurname());
        updateStmt.setInt(3, student.getCourseID());
        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}