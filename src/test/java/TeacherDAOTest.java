package src.test.java;

import org.testng.Assert;
import org.testng.annotations.*;
import src.main.java.DbPerson;
import src.main.java.Teacher;

import java.sql.SQLException;
import java.util.List;


public class TeacherDAOTest {

    static DbPerson.TeacherDAO testDao = new DbPerson.TeacherDAO();
    static Teacher testTeacher = new Teacher("TestName", "TestSurname");



    @Test(priority = 3)
    public void testGet() {
        try {
            Teacher checkTeacher = testDao.get(testTeacher.getID());
            Assert.assertEquals(testTeacher.getName(), checkTeacher.getName());
            Assert.assertEquals(testTeacher.getSurname(), checkTeacher.getSurname());
        }catch(SQLException e){e.printStackTrace();}

    }

    @Test(priority = 2)
    public void testGetByName() {
        try {
            Teacher checkTeacher = testDao.getByName(testTeacher.getName());
            testTeacher.setID(checkTeacher.getID());
            Assert.assertEquals(testTeacher.getName(), checkTeacher.getName());
            Assert.assertEquals(testTeacher.getSurname(), checkTeacher.getSurname());
        }catch(SQLException e){e.printStackTrace();}
    }

    @Test(priority = 2)
    public void testGetAll() {
        try {
            boolean check = false;
            List<Teacher> list =  testDao.getAll();
            for(Teacher teacher : list){
                if(teacher.getName().equals(testTeacher.getName()) &&
                        teacher.getSurname().equals(testTeacher.getSurname())){
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
            Teacher checkTeacher = testDao.getByName(testTeacher.getName());
            Assert.assertEquals(testTeacher.getName(), checkTeacher.getName());
            Assert.assertEquals(testTeacher.getSurname(), checkTeacher.getSurname());
        }catch (SQLException e){e.printStackTrace();}
    }

    @Test(priority = 4)
    public void testUpdate() {
        try {
            Teacher updateTeacher = new Teacher(testTeacher.getName(), "updateSurname");
            testDao.update(testTeacher.getID(), updateTeacher);
            Teacher checkTeacher = testDao.get(testTeacher.getID());
            Assert.assertEquals(updateTeacher.getName(), checkTeacher.getName());
            Assert.assertEquals(updateTeacher.getSurname(), checkTeacher.getSurname());
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