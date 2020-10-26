package test.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import test.Course;
import test.DAO.CourseDAO;

import java.sql.SQLException;
import java.util.List;


public class CourseDAOTest {

    static CourseDAO testDao = new CourseDAO();
    static Course testCourse = new Course("TestName", 1, 1);



    @Test(priority = 3)
    public void testGet() {
        try {
            Course checkCourse = testDao.get(testCourse.getID());
            Assert.assertEquals(testCourse.getName(), checkCourse.getName());
            Assert.assertEquals(testCourse.getYear(), checkCourse.getYear());
            Assert.assertEquals(testCourse.getTeacherID(), checkCourse.getTeacherID());
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Course checkCourse = testDao.getByName(testCourse.getName());
            testCourse.setID(checkCourse.getID());
            Assert.assertEquals(testCourse.getName(), checkCourse.getName());
            Assert.assertEquals(testCourse.getYear(), checkCourse.getYear());
            Assert.assertEquals(testCourse.getTeacherID(), checkCourse.getTeacherID());
        }catch(SQLException e){e.printStackTrace();}
    }


    @Test(priority = 2)
    public void testGetByYear() {
        try {
            boolean check = false;
            List<Course> list =  testDao.getByYear(testCourse.getYear());
            for(Course course : list){
                if(course.getName().equals(testCourse.getName()) && course.getYear() == testCourse.getYear() &&
                        course.getTeacherID() == testCourse.getTeacherID()){
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
            List<Course> list =  testDao.getByTeacher(testCourse.getTeacherID());
            for(Course course : list){
                if(course.getName().equals(testCourse.getName()) && course.getYear()==testCourse.getYear() &&
                        course.getTeacherID() == testCourse.getTeacherID()){
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
            List<Course> list =  testDao.getAll();
            for(Course course : list){
                if(course.getName().equals(testCourse.getName()) && course.getYear() == testCourse.getYear() &&
                        course.getTeacherID() == testCourse.getTeacherID()){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 1)
    public void testCreate() {
        try {
            testDao.create(testCourse);
            Course checkCourse = testDao.getByName(testCourse.getName());
            Assert.assertEquals(testCourse.getName(), checkCourse.getName());
            Assert.assertEquals(testCourse.getTeacherID(), checkCourse.getTeacherID());
            Assert.assertEquals(testCourse.getYear(), checkCourse.getYear());
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            Course updateCourse = new Course(testCourse.getName(), 2, 2);
            testDao.update(testCourse.getID(), updateCourse);
            Course checkCourse = testDao.get(testCourse.getID());
            Assert.assertEquals(updateCourse.getName(), checkCourse.getName());
            Assert.assertEquals(updateCourse.getYear(), checkCourse.getYear());
            Assert.assertEquals(updateCourse.getTeacherID(), checkCourse.getTeacherID());
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 5)
    public void testDelete() {
        try {
            testDao.delete(testCourse.getID());
            Course checkCourse = testDao.get(testCourse.getID());
            Assert.assertNull(checkCourse);
        }catch(SQLException e){e.printStackTrace();}
    }
}