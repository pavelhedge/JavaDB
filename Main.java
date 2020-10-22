package Java.test;

import java.sql.*;

public class Main {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/test";
    private static final String user = "root";
    private static final String password = "";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String args[]){
        String query = "select name, surname from students where course IN (select id from courses where name = 'IT')";
        try {Class.forName("com.mysql.jdbc.Driver");}
        catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found");
        }

        try{
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            }
        } catch (SQLException sqlEx){
            sqlEx.printStackTrace();
        } finally{
            try{con.close();} catch(SQLException se){}
            try{stmt.close();} catch(SQLException se){}
            try{rs.close();} catch(SQLException se){}
        }
    }

}