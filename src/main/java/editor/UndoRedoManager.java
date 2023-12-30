package editor;

import utils.Action;
import java.util.Stack;

public class UndoRedoManager<T> {
    private Stack<Action<T>> undoStack;
    private Stack<Action<T>> redoStack;
    private T currentState;

    public UndoRedoManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void execute(Action<T> action) {
        action.execute();
        currentState = action.getCurrentState();
        undoStack.push(action);
        redoStack.clear();
    }


    public void undo() {
        if (!undoStack.isEmpty()) {
            Action<T> action = undoStack.pop();
            action.undo();
            redoStack.push(action);
            currentState = action.getCurrentState();
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Action<T> action = redoStack.pop();
            action.execute();
            undoStack.push(action);
            currentState = action.getCurrentState();
        }
    }

    public T getCurrentState() {
        return currentState;
    }

}
