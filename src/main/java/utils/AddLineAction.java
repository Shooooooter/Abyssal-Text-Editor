package utils;

import utils.Action;
import core.Line;
import core.SourceFile;
import editor.UndoRedoManager;

public class AddLineAction implements Action<SourceFile> {
    private SourceFile sourceFile;
    private Line line;

    public AddLineAction(SourceFile sourceFile, Line line) {
        this.sourceFile = sourceFile;
        this.line = line;
    }

    @Override
    public void execute() {
        sourceFile.getLines().add(line);
    }

    @Override
    public void undo() {
        sourceFile.getLines().remove(line);
    }

    @Override
    public SourceFile getCurrentState() {
        return sourceFile;
    }
}
