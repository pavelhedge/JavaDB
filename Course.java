package Java.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Java.test.DbObject.getConnection;

class CourseDAO implements DAO<Course>{

    private ResultSet resultSet;
    private PreparedStatement getStmt;
    private PreparedStatement getByNameStmt;
    private PreparedStatement getByYearStmt;
    private PreparedStatement getByTeacherStmt;
    private PreparedStatement getAllStmt;
    private PreparedStatement createStmt;
    private PreparedStatement updateStmt;
    private PreparedStatement deleteStmt;

    CourseDAO() {
        Connection connection = getConnection();
        String getQuery = "SELECT * FROM courses WHERE ID = ?";
        String getByNameQuery = "SELECT * FROM courses WHERE name = ?";
        String getByYearQuery = "SELECT * FROM courses WHERE year = ";
        String getByTeacherQuery = "SELECT * FROM courses WHERE teacherID = ?";
        String getAllQuery = "SELECT * FROM courses";
        String createQuery = "INSERT INTO courses (name, surname, teacherID) VALUES (?,?,?)";
        String updateQuery = "UPDATE courses SET name = ?, surname = ?, teacherID = ? where ID = ?";
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

    public Course get(int ID) throws SQLException{

        getStmt.setInt(1, ID);
        resultSet = getStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int teacherID = resultSet.getInt("teacherID");

            return new Course(id, name, teacherID);
        }
        else return null;
    }

    public Course getByName(String name) throws SQLException{

        getByNameStmt.setString(1, name);
        resultSet = getByNameStmt.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int teacherID = resultSet.getInt("teacherID");

            return new Course(id, name, teacherID);
        }
        else return null;
    }

    List<Course> getByYear(int year) throws SQLException
    {
        List<Course> list = new ArrayList<>();
        getByYearStmt.setInt(1, year);
        resultSet = getByYearStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("Surname");
            int teacherID = resultSet.getInt("TeacherID");
            list.add(new Course(id,name,teacherID));
        }
        return list;
    }
    List<Course> getByTeacher(int teacherID) throws SQLException
    {
        List<Course> list = new ArrayList<>();
        getByTeacherStmt.setInt(1, teacherID);
        resultSet = getByTeacherStmt.executeQuery();
        while (resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("Surname");
            list.add(new Course(id,name,teacherID));
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
            list.add(new Course(id, name, teacherID));
        }
        return list;
    }
    public void create(Course course) throws SQLException{
        createStmt.setString(1,course.name);
        createStmt.setInt(2, course.teacherID);
        createStmt.executeUpdate();
    }
    public void update(int id, Course course) throws SQLException{
        updateStmt.setInt(3, id);
        updateStmt.setString(1,course.name);
        updateStmt.setInt(2, course.teacherID);
        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}


public class Course extends DbObject {
    int teacherID;

    public Course(int id, String name, int teacherID) {
        super(id, name);
    }

    public Course(String name, int teacherID){
        super(name);
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return "" + this.name + ", teacher is: " + this.teacherID + ", ID: " + this.getID();
    }
}
