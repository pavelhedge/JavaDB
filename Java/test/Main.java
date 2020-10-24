package Java.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {


    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static BufferedReader getReader(){
        return reader;
    }

    public static void main(String[] args){


        BufferedReader reader = getReader();
        System.out.println("Entering menu");
        Menu menu = new Menu();
        menu.menu();


    }

}