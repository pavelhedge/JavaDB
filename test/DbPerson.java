package test;

public abstract class DbPerson extends DbObject {
    String surname;

    public String getSurname() {
        return surname;
    }

    public DbPerson(int id, String name, String surname){
        super(id, name);
        this.surname = surname;
    }
    public DbPerson(String name, String surname){
        super(name);
        this.surname = surname;
    }
}

