package de.z1up.ttt.interfaces;

/**
 * A wrapper takes care of all the processes that need to be handled
 * via the mysql connection. For example, the wrapper can be used to
 * insert new objects or delete old ones.
 *
 * @author chris23lngr
 * @since 1.0
 */
public interface Wrapper {

    /**
     * This method creates a new table in the database. The important
     * attributes are always declared in the class header.
     */
    void createTable();

    /**
     * Creates a new object in the database. The appropriate object
     * for the respective wrapper is specified as a parameter. The
     * data to be entered into the database is then read from this
     * object.
     *
     * @param e The object which needs to be inserted.
     */
    void insert(Object e);

    /**
     * Updates an object in the database.
     *
     * @param e the identifier by which the mysql connection
     *          recognises which object needs to be changed.
     */
    void update(Object e);

    /**
     * Deletes an object from the database by its identifier.
     *
     * @param e The identifier for the Object which needs to be
     *          deleted.
     */
    void delete(Object e);

    /**
     * Returns an object from the database of the respective wrapper.
     *
     * @param e The identifier to select the right Object.
     * @return The selected Object from the database.
     */
    Object get(Object e);

}
