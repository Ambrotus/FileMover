package sourcepkg;

/**
 *
 * @author Ambro
 */
import javafx.stage.DirectoryChooser;
import java.io.File;
import javafx.stage.Stage;

//references
//https://www.geeksforgeeks.org/javafx-directorychooser-class/
//http://tutorials.jenkov.com/javafx/directorychooser.html
//https://stackoverflow.com/questions/4362786/getting-the-default-root-directory-in-java
//https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html

public class SelectDir {

    public static String openDir(Stage stage) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        // "/" is root dir and works for every os, system properties do the same but actually get you to the working folder of the .jar(user.dir) and the users home folder(user.home) havent found a way to get to the my docs
        // root and user.home are kind of scary with this program as i move and delete files recursivly.
        directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));// "/" "user.home" https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
        File selectedDirectory = directoryChooser.showDialog(stage);
        return selectedDirectory.getAbsolutePath();
    }
}
