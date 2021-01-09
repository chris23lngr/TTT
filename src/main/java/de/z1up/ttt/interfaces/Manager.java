package de.z1up.ttt.interfaces;

/**
 * A manager takes care of the individual functions
 * of a module. For example, the TimerManager only
 * deals with processes related to schedulers. Some
 * managers are extended by a wrapper that can be
 * used to perform operations in the database.
 *
 * @author chris23lngr
 * @since 1.0
 * @see de.z1up.ttt.interfaces.Wrapper
 */
public interface Manager {

    /**
     * The {@code load()} method is used to load data
     * or execute operations with a high priority. The
     * {@code init()} method is also called here. load()
     * should already be called in the constructor so
     * that operations can run smoothly.
     */
    void load();

    /**
     * The {@code init()} method initialises all
     * attributes of the manager.
     */
    void init();

}
