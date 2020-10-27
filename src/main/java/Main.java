package src.main.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
Проект содержит три базы данных - студентов, курсы, преподавателей.
Каждая запись в любой из таблиц имеет свой уникальный для этой таблицы номер id.
Каждый студент имеет имя, фамилию и записан на курс.
Каждый курс имеет название, год обучения и закреплен за преподавателем.
Каждый преподаватель имеет имя и фамилию.

DAO позволяет создавать, получать, изменять и удалять записи в таблице.
Получать записи можно по одной по ID или названию(имени).
Так же для студентов можно получать записи списком по году обучения, названию курса и ID преподавателя.
Для курсов можно получать записи списком по году обучения и имени преподавателя.
*/
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