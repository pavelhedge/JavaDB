package test.DAO;

import test.DbObject;

import java.io.IOException;
import java.sql.*;
import java.util.List;

public interface DAO<T extends DbObject> {

    T form() throws IOException;
    T get(int ID) throws SQLException;
    T getByName(String name)throws SQLException;
    List<T> getAll() throws SQLException;
    void create(T t) throws SQLException;
    void update(int ID, T t) throws SQLException;
    void delete(int ID) throws SQLException;
}
