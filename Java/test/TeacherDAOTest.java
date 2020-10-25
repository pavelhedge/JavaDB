package Java.test;
import org.testng.Assert;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.List;


public class TeacherDAOTest {

    static TeacherDAO testDao = new TeacherDAO();
    static Teacher testTeacher = new Teacher("Testname", "TestSurname");



    @Test(priority = 3)
    public void testGet() {
        try {
            Teacher checkTeacher = testDao.get(testTeacher.getID());
            Assert.assertEquals(testTeacher.name, checkTeacher.name);
            Assert.assertEquals(testTeacher.surname, checkTeacher.surname);
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Teacher checkTeacher = testDao.getByName(testTeacher.name);
            testTeacher.setID(checkTeacher.getID());
            Assert.assertEquals(testTeacher.name, checkTeacher.name);
            Assert.assertEquals(testTeacher.surname, checkTeacher.surname);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetAll() {
        try {
            boolean check = false;
            List<Teacher> list =  testDao.getAll();
            for(Teacher st : list){
                if(st.name.equals(testTeacher.name) && st.surname.equals(testTeacher.surname)){
                    check = true;
                }
            }
            Assert.assertTrue(check);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 1)
    public void testCreate() {
        try {
            testDao.create(testTeacher);
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            testTeacher.name = "updateName";
            testDao.update(testTeacher.getID(), testTeacher);
            Teacher checkTeacher = testDao.get(testTeacher.getID());
            Assert.assertEquals(testTeacher.name, checkTeacher.name);
            Assert.assertEquals(testTeacher.surname, checkTeacher.surname);
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 5)
    public void testDelete() {
        try {
            testDao.delete(testTeacher.getID());
            Teacher checkTeacher = testDao.get(testTeacher.getID());
            Assert.assertNull(checkTeacher);
        }catch(SQLException e){e.printStackTrace();}
    }
}