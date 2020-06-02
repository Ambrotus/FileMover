package sourcepkg;

/**
 *
 * @author Ambro
 */

import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;

//I followed a guide for file moving here
//https://www.geeksforgeeks.org/moving-file-one-directory-another-using-java/
//I also researched OS specific paths, same link as commented a few more lines below.
//https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html

public class MoveFile {

    //"file.separator" 	Character that separates components of a file path. This is "/" on UNIX and "\" on Windows. this could be added to remove the whole "os if" statements  https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
    public static void move(String file, String initialDir, String parentOutDir) throws IOException {
        String outDir;
        String outputPath = parentOutDir + file.substring(initialDir.length());
        outDir = parentOutDir + file.substring(initialDir.length(), file.lastIndexOf(System.getProperty("file.separator"))); //equivlent of file.lastIndexOf('/')) or \\ but works for any os

        File directory = new File(outDir);
        if (!directory.exists()) {
            //using mkdirs to copy file tree of folders to keep users organized files as they originally had them
            directory.mkdirs();
        }

        Path temp = Files.move(Paths.get(file), Paths.get(outputPath));
        if (temp != null) {
            Gui.outputLog.appendText(" moved successfully");
        } else {
            Gui.outputLog.appendText(" failed to move");
        }
    }
}
