package de.z1up.ttt.mysql;

public class DBPlayerWrapper {

    private SQL sql;

    public DBPlayerWrapper(SQL sql) {
        this.sql = sql;
        createTable();
    }

    void createTable() {

    }

}
