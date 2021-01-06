package de.z1up.ttt.interfaces;


public interface Wrapper {

    void createTable();

    void insert(Object e);

    void update(Object e);

    void delete(Object e);

    Object get(Object e);

}
