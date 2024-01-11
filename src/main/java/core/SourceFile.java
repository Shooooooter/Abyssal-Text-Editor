// SourceFile class

package core;

import utils.FileMetadata;
import editor.UndoRedoManager;
import utils.Action;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.util.LinkedList;

public class SourceFile implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LinkedList<Line> lines;
    private FileMetadata metadata;
    private UndoRedoManager<SourceFile> undoRedoManager;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public SourceFile(FileMetadata metadata) {
        this.metadata = metadata;
        this.lines = new LinkedList<>();
        this.undoRedoManager = new UndoRedoManager<>();
        initUndoRedoListener();
    }

    private void initUndoRedoListener() {
        undoRedoManager.addPropertyChangeListener(evt -> {
            propertyChangeSupport.firePropertyChange("content", null, lines);
            propertyChangeSupport.firePropertyChange("undoRedoUpdate", null, undoRedoManager.getCurrentState());
        });
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public FileMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FileMetadata metadata) {
        this.metadata = metadata;
    }

    public void addLine(Line line) {
        lines.add(line);
        notifyContentChanged();
    }

    public void removeLine(int lineNumber) {
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber);
            notifyContentChanged();
        }
    }

    public void insertLine(int lineNumber, Line line) {
        if (lineNumber >= 0 && lineNumber <= lines.size()) {
            lines.add(lineNumber, line);
            notifyContentChanged();
        }
    }

    public void editLine(int lineNumber, String newContent) {
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            Line line = lines.get(lineNumber);
            line.getContent().setLength(0);
            line.getContent().append(newContent);
            notifyContentChanged();
        }
    }

    public void executeUndo() {
        undoRedoManager.undo();
    }

    public void executeRedo() {
        undoRedoManager.redo();
    }

    public void executeAction(Action<SourceFile> action) {
        undoRedoManager.execute(action);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    private void notifyContentChanged() {
        propertyChangeSupport.firePropertyChange("content", null, lines);
    }

    // Clean up resources when the SourceFile is no longer needed
    public void dispose() {
        undoRedoManager.removePropertyChangeListener(evt -> {
            propertyChangeSupport.firePropertyChange("undoRedoUpdate", null, undoRedoManager.getCurrentState());
        });
    }
}
