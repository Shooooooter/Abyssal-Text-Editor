package utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.PosixFilePermission;
import java.time.LocalDateTime;
import java.util.Set;

public class FileMetadata {

    private Path filePath;
    private String filename;
    private int totalLines;
    private final String author;
    private final LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
    private long fileSize;
    private boolean isModified; // False for first session
    private boolean isNewFile;  // True for first session
    private boolean isReadOnly;
    private String characterEncoding;

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public boolean isModified() {
        return isModified;
    }

    public void setModified(boolean modified) {
        isModified = modified;
    }

    public boolean isNewFile() {
        return isNewFile;
    }

    public void setNewFile(boolean newFile) {
        isNewFile = newFile;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public FileMetadata(Path filePath, String filename) {
        this.filePath = filePath;
        this.filename = filename;
        this.isModified = false;
        this.isNewFile = true;
        this.isReadOnly = false;
        this.characterEncoding = "UTF-8"; // Default to UTF-8

        // Set author and other attributes
        this.author = getUserName();
        this.creationDate = LocalDateTime.now();
        this.lastModifiedDate = this.creationDate;

        try {
            this.fileSize = Files.size(filePath);
        } catch (Exception e) {
            e.printStackTrace(); // Temp Debugger
        }
    }

    public void setIsReadOnly(boolean isReadOnly) {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            // For Unix Based Systems
            if (osName.contains("nix") || osName.contains("nux") || osName.contains("mac")) {
                Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(filePath);
                permissions.remove(PosixFilePermission.OWNER_WRITE);
                Files.setPosixFilePermissions(filePath, permissions);
            }
            // For Windows OS
            else if (osName.contains("win")) {
                DosFileAttributeView dosFileAttributeView = Files.getFileAttributeView(filePath, DosFileAttributeView.class);
                if (dosFileAttributeView != null) {
                    dosFileAttributeView.setReadOnly(isReadOnly);
                } else {
                    System.out.println("Unable to get DosFileAttributeView for setting read-only attribute.");
                }
            } else {
                // For unexpected/edge cases
                System.out.println("Unsupported operating system for setting read-only attribute");
            }

            this.isReadOnly = isReadOnly;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUserName() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            // Windows-specific code for getting the username
            return System.getenv("USERNAME");
        } else {
            // Unix-like systems
            return System.getProperty("user.name");
        }
    }
}
