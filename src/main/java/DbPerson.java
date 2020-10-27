package src.main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DbPerson extends DbObject {
    String surname;

    public String getSurname() {
        return surname;
    }

    public DbPerson(int id, String name, String surname){
        super(id, name);
        this.surname = surname;
    }
    public DbPerson(String name, String surname){
        super(name);
        this.surname = surname;
    }

    public static class CourseDAO implements DAO<Course> {

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

    public static interface DAO<T extends DbObject> {

        T form() throws IOException;
        T get(int ID) throws SQLException;
        T getByName(String name)throws SQLException;
        List<T> getAll() throws SQLException;
        void create(T t) throws SQLException;
        void update(int ID, T t) throws SQLException;
        void delete(int ID) throws SQLException;
    }

    public static class StudentDAO implements DAO<Student> {

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

    public static class TeacherDAO implements DAO<Teacher> {

        private ResultSet resultSet;
        private PreparedStatement getStmt;
        private PreparedStatement getByNameStmt;
        private PreparedStatement getAllStmt;
        private PreparedStatement createStmt;
        private PreparedStatement updateStmt;
        private PreparedStatement deleteStmt;

        public TeacherDAO() {
            Connection connection = getConnection();
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
}

