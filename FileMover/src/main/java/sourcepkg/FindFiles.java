package sourcepkg;

/**
 *
 * @author ---
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//I followed a guide for directory parsing here:
//https://mkyong.com/java/search-directories-recursively-for-file-in-java/

public class FindFiles {

    private String fileExtension;
    private List<String> results = new ArrayList<String>();

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtenstion(String fileExtensionToSearch) {
        fileExtension = fileExtensionToSearch;
    }

    public List<String> getResults() {
        return results;
    }

    public void searchDirectory(File directory, String fileExtensionToSearch) {
        setFileExtenstion(fileExtensionToSearch);
        if (directory.isDirectory()) {
            search(directory);
        } else {
            Gui.outputLog.appendText("\n" + directory.getAbsoluteFile() + " is not a directory!");
        }
    }

    private void search(File file) {

        if (file.isDirectory()) {
            Gui.outputLog.appendText("\nSearching directory ... " + file.getAbsoluteFile());
            if (file.canRead()) {
                for (File fileFound : file.listFiles()) {
                    if (fileFound.isDirectory()) {
                        search(fileFound);
                    } else {
                        if (fileFound.toString().toLowerCase().contains(fileExtension)) {
                            results.add(fileFound.getAbsoluteFile().toString());
                        }
                    }
                }
            } else Gui.outputLog.appendText("\n" + file.getAbsoluteFile() + "Permission Denied");
        }
    }
}
