package test.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import test.DAO.CourseDAO;
import test.DAO.StudentDAO;
import test.Student;

import java.sql.SQLException;
import java.util.List;


public class StudentDAOTest {

    static StudentDAO testDao = new StudentDAO();
    static Student testStudent = new Student("TestName", "TestSurname", 1);



    @Test(priority = 3)
    public void testGet() {
        try {
            Student checkStudent = testDao.get(testStudent.getID());
            Assert.assertEquals(testStudent.getName(), checkStudent.getName());
            Assert.assertEquals(testStudent.getSurname(), checkStudent.getSurname());
            Assert.assertEquals(testStudent.getCourseID(), checkStudent.getCourseID());
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Student checkStudent = testDao.getByName(testStudent.getName());
            testStudent.setID(checkStudent.getID());
            Assert.assertEquals(testStudent.getName(), checkStudent.getName());
            Assert.assertEquals(testStudent.getSurname(), checkStudent.getSurname());
            Assert.assertEquals(testStudent.getCourseID(), checkStudent.getCourseID());
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetByCourse() {
        try {
            boolean check = false;
            List<Student> list =  testDao.getByCourse(testStudent.getCourseID());
            for(Student st : list){
                if(st.getName().equals(testStudent.getName()) && st.getName().equals(testStudent.getName()) &&
                        st.getCourseID() == testStudent.getCourseID()){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetByYear() {
        try {
            boolean check = false;
            int year = new CourseDAO().get(testStudent.getCourseID()).getYear();
            List<Student> list =  testDao.getByYear(year);
            for(Student st : list){
                if(st.getName().equals(testStudent.getName()) && st.getSurname().equals(testStudent.getSurname()) &&
                        st.getCourseID() == testStudent.getCourseID()){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetByTeacher() {
        try {
            boolean check = false;

            int teacherID = new CourseDAO().get(testStudent.getCourseID()).getTeacherID();

            List<Student> list =  testDao.getByTeacher(teacherID);
            for(Student st : list){
                if(st.getName().equals(testStudent.getName()) && st.getSurname().equals(testStudent.getSurname()) &&
                        st.getCourseID() == testStudent.getCourseID()){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetAll() {
        try {
            boolean check = false;
            List<Student> list =  testDao.getAll();
            for(Student st : list){
                if(st.getName().equals(testStudent.getName()) && st.getSurname().equals(testStudent.getSurname()) &&
                        st.getCourseID() == testStudent.getCourseID()){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 1)
    public void testCreate() {
        try {
            testDao.create(testStudent);
            Student checkStudent = testDao.getByName(testStudent.getName());
            Assert.assertEquals(testStudent.getName(), checkStudent.getName());
            Assert.assertEquals(testStudent.getSurname(), checkStudent.getSurname());
            Assert.assertEquals(testStudent.getCourseID(), checkStudent.getCourseID());
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            Student updateStudent = new Student (testStudent.getName(), "updateSurname", testStudent.getCourseID());
            testDao.update(testStudent.getID(), updateStudent);
            Student checkStudent = testDao.get(testStudent.getID());
            Assert.assertEquals(updateStudent.getName(), checkStudent.getName());
            Assert.assertEquals(updateStudent.getSurname(), checkStudent.getSurname());
            Assert.assertEquals(updateStudent.getCourseID(), checkStudent.getCourseID());
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 5)
    public void testDelete() {
        try {
            testDao.delete(testStudent.getID());
            Student checkStudent = testDao.get(testStudent.getID());
            Assert.assertNull(checkStudent);
        }catch(SQLException e){e.printStackTrace();}
    }
}