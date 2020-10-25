package Java.test;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.List;


public class StudentDAOTest {

    static StudentDAO testDao = new StudentDAO();
    static Student testStudent = new Student("Testname", "TestSurname", 1);



    @Test(priority = 3)
    public void testGet() {
        try {
            Student checkStudent = testDao.get(testStudent.getID());
            Assert.assertEquals(testStudent.name, checkStudent.name);
            Assert.assertEquals(testStudent.surname, checkStudent.surname);
            Assert.assertEquals(testStudent.courseID, checkStudent.courseID);
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Student checkStudent = testDao.getByName(testStudent.name);
            testStudent.setID(checkStudent.getID());
            Assert.assertEquals(testStudent.name, checkStudent.name);
            Assert.assertEquals(testStudent.surname, checkStudent.surname);
            Assert.assertEquals(testStudent.courseID, checkStudent.courseID);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetByCourse() {
        try {
            boolean check = false;
            List<Student> list =  testDao.getByCourse(testStudent.courseID);
            for(Student st : list){
                if(st.name.equals(testStudent.name) && st.surname.equals(testStudent.surname) && st.courseID == testStudent.courseID){
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
            int year = new CourseDAO().get(testStudent.courseID).year;
            List<Student> list =  testDao.getByYear(year);
            for(Student st : list){
                if(st.name.equals(testStudent.name) && st.surname.equals(testStudent.surname) && st.courseID == testStudent.courseID){
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

            int teacherID = new CourseDAO().get(testStudent.courseID).teacherID;

            List<Student> list =  testDao.getByTeacher(teacherID);
            for(Student st : list){
                if(st.name.equals(testStudent.name) && st.surname.equals(testStudent.surname) && st.courseID == testStudent.courseID){
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
                if(st.name.equals(testStudent.name) && st.surname.equals(testStudent.surname) && st.courseID == testStudent.courseID){
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
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            testStudent.name = "updateName";
            testDao.update(testStudent.getID(), testStudent);
            Student checkStudent = testDao.get(testStudent.getID());
            Assert.assertEquals(testStudent.name, checkStudent.name);
            Assert.assertEquals(testStudent.surname, checkStudent.surname);
            Assert.assertEquals(testStudent.courseID, checkStudent.courseID);
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