package src.main.java;

public class Teacher extends DbPerson {

    public Teacher(int id, String name, String surname) {
        super(id, name, surname);
    }

    public Teacher(String name, String surname){
        super(name, surname);
    }

    @Override
    public String toString() {
        return "" + this.name + " " + this.surname + ", ID: " + this.getID();
    }
}
