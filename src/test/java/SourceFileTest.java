import core.Line;
import core.SourceFile;
import org.junit.jupiter.api.Test;
import utils.FileMetadata;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SourceFileTest {

    @Test
    void testAddLine() {
        SourceFile sourceFile = new SourceFile(new FileMetadata(null, "testFile"));
        Line line = new Line("Test line content");

        sourceFile.addLine(line);

        assertEquals(1, sourceFile.getLines().size());
        assertEquals(line, sourceFile.getLines().get(0));
    }

    @Test
    void testRemoveLine() {
        SourceFile sourceFile = new SourceFile(new FileMetadata(null, "testFile"));
        Line line = new Line("Test line content");
        sourceFile.addLine(line);

        sourceFile.removeLine(0);

        assertEquals(0, sourceFile.getLines().size());
    }

    // Add more tests for other methods
}
