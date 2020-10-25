package Java.test;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.List;


public class CourseDAOTest {

    static CourseDAO testDao = new CourseDAO();
    static Course testCourse = new Course("testName", 1, 1);



    @Test(priority = 3)
    public void testGet() {
        try {
            Course checkCourse = testDao.get(testCourse.getID());
            Assert.assertEquals(testCourse.name, checkCourse.name);
            Assert.assertEquals(testCourse.year, checkCourse.year);
            Assert.assertEquals(testCourse.teacherID, checkCourse.teacherID);
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Course checkCourse = testDao.getByName(testCourse.name);
            testCourse.setID(checkCourse.getID());
            Assert.assertEquals(testCourse.name, checkCourse.name);
            Assert.assertEquals(testCourse.year, checkCourse.year);
            Assert.assertEquals(testCourse.teacherID, checkCourse.teacherID);
        }catch(SQLException e){e.printStackTrace();}
    }


    @Test(priority = 2)
    public void testGetByYear() {
        try {
            boolean check = false;
            List<Course> list =  testDao.getByYear(testCourse.year);
            for(Course st : list){
                if(st.name.equals(testCourse.name) && st.year == testCourse.year && st.teacherID == testCourse.teacherID){
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
            List<Course> list =  testDao.getByTeacher(testCourse.teacherID);
            for(Course st : list){
                if(st.name.equals(testCourse.name) && st.year==testCourse.year && st.teacherID == testCourse.teacherID){
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
            for(Course st : list){
                if(st.name.equals(testCourse.name) && st.year == testCourse.year && st.teacherID == testCourse.teacherID){
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
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            testCourse.name = "updateName";
            testDao.update(testCourse.getID(), testCourse);
            Course checkCourse = testDao.get(testCourse.getID());
            Assert.assertEquals(testCourse.name, checkCourse.name);
            Assert.assertEquals(testCourse.year, checkCourse.year);
            Assert.assertEquals(testCourse.teacherID, checkCourse.teacherID);
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