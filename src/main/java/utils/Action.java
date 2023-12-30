package utils;

public interface Action<T> {
    void execute();

    void undo();

    // Add a method to get the current state
    T getCurrentState();
}
