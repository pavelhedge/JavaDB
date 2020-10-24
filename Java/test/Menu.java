package Java.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;


public class Menu {

    DAO menuDAO;
    StudentDAO sdao;
    TeacherDAO tdao;
    CourseDAO cdao;
    BufferedReader reader;
    Menu(){
        sdao = new StudentDAO();
        tdao = new TeacherDAO();
        cdao = new CourseDAO();
        reader = Main.getReader();

    }


    void getStudent() throws IOException, SQLException {
        System.out.println("get by: 1.id, 2.name, 3.courseID, 4.teacherID, 5.year");
        String param = reader.readLine();
        List<Student> stList;
        switch(param){
            case"id":
            case"1":
                System.out.println("Enter student's id");
                System.out.println(sdao.get(Integer.parseInt(reader.readLine())));
                break;
            case"name":
            case "2":
                System.out.println("Enter student's name");
                System.out.println(sdao.getByName((reader.readLine())));
                break;
            case"courseID":
            case "3":
                System.out.println("Enter courseID");
                stList = sdao.getByCourse(Integer.parseInt(reader.readLine()));
                for (Student st : stList) System.out.println(st);
                break;
            case "teacherID":
            case "4":
                System.out.println("Enter teacherID");
                stList = sdao.getByTeacher(Integer.parseInt(reader.readLine()));
                for (Student st : stList) System.out.println(st);
                break;
            case "year":
            case "5":
                System.out.println("Enter year");
                stList = sdao.getByYear(Integer.parseInt(reader.readLine()));
                for (Student st : stList) System.out.println(st);
                break;
        }
    }

    void getCourse() throws IOException, SQLException {
        System.out.println("get by: 1.id, 2.name, 3.teacherID, 4.year");
        String param = reader.readLine();
        List<Course> crList;
        switch(param){
            case"id":
            case "1":
                System.out.println("Enter id of course");
                System.out.println(cdao.get(Integer.parseInt(reader.readLine())));
                break;
            case"name":
            case "2":
                System.out.println("Enter name of course");
                System.out.println(cdao.getByName((reader.readLine())));
                break;
            case "teacherID":
            case "3":
                System.out.println("Enter that course teacher's ID");
                crList = cdao.getByTeacher(Integer.parseInt(reader.readLine()));
                for (Course cr : crList) System.out.println(cr);
                break;
            case "year":
            case "4":
                System.out.println("Enter year of a course");
                crList = cdao.getByYear(Integer.parseInt(reader.readLine()));
                for (Course cr : crList) System.out.println(cr);
                break;
        }
    }

    void getTeacher() throws IOException, SQLException {
        System.out.println("get by: 1.id, 2.name");
        String param = reader.readLine();
        switch(param){
            case"id":
            case "1":
                System.out.println("Enter teacher's ID");
                System.out.println(tdao.get(Integer.parseInt(reader.readLine())));
                break;
            case"name":
            case "2":
                System.out.println("Enter teacher's name");
                System.out.println(tdao.getByName((reader.readLine())));
                break;
        }
    }



    void menu(){
        boolean exit = false;
        while(true){
            if (exit) break;
            try {
                boolean cont = false;

                System.out.println("Choose instance: 1.student, 2.course, 3.teacher or type exit");
                String input = reader.readLine();
                switch (input) {
                    case "student":
                    case "1":
                        menuDAO = sdao;
                        break;
                    case "course":
                    case "2":
                        menuDAO = cdao;
                        break;
                    case "teacher":
                    case "3":
                        menuDAO = tdao;
                        break;
                    case "exit":
                        exit = true;
                        break;
                    default:
                        cont = true;
                }
                if (cont) continue;
                if (exit) break;
                System.out.println("Choose action: 1.get, 2.create, 3.update, 4.delete");
                String input2 = reader.readLine();
                int id;
                switch (input2) {
                    case "get":
                    case "1":
                        System.out.println("get");
                        switch(input){
                            case "student":
                            case "1":
                                getStudent();
                                break;
                            case "course":
                            case "2":
                                getCourse();
                                break;
                            case "teacher":
                            case "3":
                                getTeacher();
                                break;
                        }
                        break;
                    case "create":
                    case "2":
                        try{
                            menuDAO.create(menuDAO.form());
                        }catch (NullPointerException e){}
                        break;
                    case "update":
                    case "3":
                        System.out.println("Enter teacher's ID");
                        id = Integer.parseInt(reader.readLine());
                        try{
                            menuDAO.update(id, menuDAO.form());
                        }catch (NullPointerException e){}
                        break;
                    case "delete":
                    case "4":
                        System.out.println("Enter teacher's ID");
                        id = Integer.parseInt(reader.readLine());
                        menuDAO.delete(id);
                        break;
                    default:
                        cont = true;
                }
            }catch(IOException e){
                e.printStackTrace();
                exit = true;
            }
            catch (SQLException sqlException){
                System.out.println("Something wrong with SQL:");
                sqlException.printStackTrace();
            }


        }
        System.out.println("Good bye!");
    }

}



