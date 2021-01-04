package de.z1up.ttt.mysql.wrapper;

public interface Wrapper {

    void createTable();

    void insert(Object e);

    void update(Object e);

    void delete(Object e);

    Object get(Object e);

}
