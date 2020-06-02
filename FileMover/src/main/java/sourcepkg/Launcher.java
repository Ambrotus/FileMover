package sourcepkg;

/**
 * The launcher class was made because the executable jar doesnt like to start the program with a class that inherits/extends application
 * seems redundant but it makes it work
 * @author Ambro
 */

// ---maven commands to run and compile in netbeans---
//  compile command -   compile package
//  run command     -   clean javafx:run
// throw these under project properties/build>actions/run project and build project or use in the console.

public class Launcher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       App.main(args);
    }
    
}
