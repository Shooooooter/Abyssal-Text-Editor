package core;

import utils.FileMetadata;

import java.util.LinkedList;

public class SourceFile {
    private LinkedList<Line> lines;
    private FileMetadata metadata;

    public SourceFile(FileMetadata metadata) {
        this.metadata = metadata;
        this.lines = new LinkedList<>();
    }

    public LinkedList<Line> getLines() {
        return lines;
    }

    public void setLines(LinkedList<Line> lines) {
        this.lines = lines;
    }

    public FileMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(FileMetadata metadata) {
        this.metadata = metadata;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public void removeLine(int lineNumber) {
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            lines.remove(lineNumber);
        }
    }

    public void insertLine(int lineNumber, Line line) {
        if (lineNumber >= 0 && lineNumber <= lines.size()) {
            lines.add(lineNumber, line);
        }
    }

    public void editLine(int lineNumber, String newContent) {
        if (lineNumber >= 0 && lineNumber < lines.size()) {
            Line line = lines.get(lineNumber);
            line.getContent().setLength(0); // Clear existing content
            line.getContent().append(newContent);
        }
    }
}