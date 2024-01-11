import editor.UndoRedoManager;
import org.junit.jupiter.api.Test;
import utils.Action;
import core.Line;
import core.SourceFile;
import utils.AddLineAction;
import utils.FileMetadata;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UndoRedoManagerTest {

    @Test
    void testUndoRedo() {
        UndoRedoManager<SourceFile> undoRedoManager = new UndoRedoManager<>();

        Path p1 = Paths.get("your-file-path");

        FileMetadata md = new FileMetadata(p1, "example.txt");

        SourceFile sourceFile = new SourceFile(md);

        Line newLine = new Line("This is a new line.");

        Action<SourceFile> addLineAction = new AddLineAction(sourceFile, newLine);

        undoRedoManager.execute(addLineAction);

        SourceFile currentState = undoRedoManager.getCurrentState();
        assertEquals(1, currentState.getLines().size());

        undoRedoManager.undo();
        currentState = undoRedoManager.getCurrentState();
        assertEquals(0, currentState.getLines().size());

        undoRedoManager.redo();
        currentState = undoRedoManager.getCurrentState();
        assertEquals(1, currentState.getLines().size());
    }
}
