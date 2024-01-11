// UndoRedoManager class

package editor;

import utils.Action;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.Stack;

public class UndoRedoManager<T> {
    private Stack<Action<T>> undoStack;
    private Stack<Action<T>> redoStack;
    private T currentState;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public UndoRedoManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void execute(Action<T> action) {
        action.execute();
        currentState = action.getCurrentState();
        undoStack.push(action);
        redoStack.clear();

        // Notify listeners about the change
        propertyChangeSupport.firePropertyChange("undoRedoUpdate", null, currentState);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Action<T> action = undoStack.pop();
            action.undo();
            redoStack.push(action);
            currentState = action.getCurrentState();
            propertyChangeSupport.firePropertyChange("undoRedoUpdate", null, currentState);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Action<T> action = redoStack.pop();
            action.execute();
            undoStack.push(action);
            currentState = action.getCurrentState();
            propertyChangeSupport.firePropertyChange("undoRedoUpdate", null, currentState);
        }
    }

    public T getCurrentState() {
        return currentState;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }
}
