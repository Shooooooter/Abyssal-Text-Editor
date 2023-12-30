package core;

import java.util.Objects;

public class Line {
    private StringBuilder content;
    private int lineNumber;

    public Line() {
        this.content = new StringBuilder();
    }

    public Line(String initialContent) {
        this.content = new StringBuilder(initialContent);
    }

    public Line(Line other) {
        this.content = new StringBuilder(other.getContent());
        this.lineNumber = other.getLineNumber();
    }

    public StringBuilder getContent() {
        return content;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void append(String text) {
        content.append(text);
    }

    public void insert(int index, String text) {
        content.insert(index, text);
    }

    public void delete(int start, int end) {
        content.delete(start, end);
    }

    @Override
    public String toString() {
        return "Line " + lineNumber + ": " + content.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Line other = (Line) obj;
        return lineNumber == other.lineNumber && content.toString().equals(other.content.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineNumber, content.toString());
    }
}
