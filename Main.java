package Java.test;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {


    public static Connection con;
    static Statement stmt;
    static PreparedStatement pstmt;
    static ResultSet rs;

    private static final String url = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String user = "root";
    private static final String password = "";

    public static void main(String args[]){
        String query = "select name, surname from students where courseID IN (select ID from courses where name = 'IT')";
        String preQuery = "select surname from students where name = ?";

        StudentDAO sdao = new StudentDAO();
        TeacherDAO tdao = new TeacherDAO();
        CourseDAO cdao = new CourseDAO();

        /*Scanner scan = new Scanner(System.in);
        while(true){
            String input = scan.nextLine();
            if (input.equals("exit")) break;
            else if (input.equals("get")){
                System.out.println("Choose instance: student, course, teacher");
                input = scan.nextLine();
                if (input.equals("student")){

                }
            }else if (input.equals("add")){

            }else if (input.equals("delete"){

            }
        }*/

        /*try{
            *//*System.out.println("Get check: " + stdao.get(1));
            System.out.println("GetAll check:");
            List<Student> students = stdao.getAll();
            for (Student st : students) System.out.println(st);
            System.out.println();
            System.out.println("create check");
            stdao.create(new Student ("Jenny", "Mittens", 2));
            System.out.println("New student created: " + stdao.getByName("Jenny"));
            System.out.println();
            System.out.println("Update check: Jenny to Kitty");
            stdao.update(9, new Student("Kitty", "Mittens", 3));
            System.out.println(stdao.get(9));
            System.out.println();*//*
            System.out.println("Delete check");
            //stdao.delete(9);
            Student st = stdao.get(9);
            if (st == null) System.out.println("Not found");
            else System.out.println(st);

        } catch (SQLException e){e.printStackTrace();}*/

    }

}