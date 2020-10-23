package Java.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Java.test.DbObject.getConnection;

class StudentDAO implements DAO<Student>{

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

    StudentDAO() {
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


    List<Student> getByCourse(int courseID)throws SQLException{
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
    List<Student> getByYear(int year) throws SQLException
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
    List<Student> getByTeacher(int teacherID) throws SQLException
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
        createStmt.setString(1,student.name);
        createStmt.setString(2,student.surname);
        createStmt.setInt(3, student.courseID);
        createStmt.executeUpdate();
    }
    public void update(int id, Student student) throws SQLException{
        updateStmt.setInt(4, id);
        updateStmt.setString(1,student.name);
        updateStmt.setString(2,student.surname);
        updateStmt.setInt(3, student.courseID);
        updateStmt.executeUpdate();
    }
    public void delete(int id) throws SQLException{
        deleteStmt.setInt(1,id);
        deleteStmt.executeUpdate();
    }

}

public class Student extends DbPerson {
    int courseID;

    public Student(int id, String name, String surname, int courseID) {
        super(id, name, surname);
        this.courseID = courseID;
    }

    public Student(String name, String surname, int courseID){
        super(name, surname);
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "" + this.name + " " + this.surname + ", ID: " + this.getID() + ", CourseID: " + this.courseID;
    }
}
