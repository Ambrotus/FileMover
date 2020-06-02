package sourcepkg;



import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * @param stage
     * @throws java.lang.Exception
     */
    
    @Override
    public void start(Stage stage) throws Exception {
        //I decided to  seperate most of the code and functions into different classes to keep the main nice and clean.
        
        //i need to pass stage into the new gui object so i can open a directory finder
        Gui gui = new Gui(stage);
        stage.setResizable(true);
        stage.setTitle("File Mover");
        
        // all the setup for the scenes are done in the gui class, and buildScene() function that i created will return a scene which will be set here.
        stage.setScene(gui.buildScene());
        stage.show();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        launch(args);
    }

}