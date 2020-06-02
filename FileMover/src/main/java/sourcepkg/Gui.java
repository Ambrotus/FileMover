package sourcepkg;

/**
 *
 * @author Ambro
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class Gui {
    //--variables
    String sourceDirectoryPath;
    String outputDirectoryPath;

    //--textFields
    private final TextField customExtensionBox = new TextField();

    //--textArea
    static final TextArea outputLog = new TextArea();

    //--checkBoxes //http://tutorials.jenkov.com/javafx/checkbox.html
    private final ArrayList<CheckBox> extBoxes = new ArrayList<>();

    //--buttons
    private final Button openInitialFolder = new Button("Open Source Folder");
    private final Button selectDestinationFolder = new Button("Select Destination Folder");
    private final Button searchForFiles = new Button("Search And Move Files");

    //--labels
    
    //--tooltips http://tutorials.jenkov.com/javafx/tooltip.html
    Tooltip extensionTooltipChkBx;
    Tooltip extensionTooltipCust;
    Tooltip sourceBtnToolTip;
    Tooltip destinationBtnToolTip;
    Tooltip searchBtnToolTip;

    //--panes and scene
    VBox rootPane;
    SplitPane splitpane;
    GridPane extensionPane;
    GridPane buttonPane;
    Scene scene;

    //--Stage variable for opening directory
    Stage openStage;

    //--constructors
    Gui(Stage stage) {
        rootPane = new VBox();
        splitpane = new SplitPane();
        extensionPane = new GridPane();
        buttonPane = new GridPane();
        scene = new Scene(rootPane, 650, 220);
        openStage = stage;
        sourceDirectoryPath = "";
        outputDirectoryPath = "";
        extBoxes.add(new CheckBox(".png"));
        extBoxes.add(new CheckBox(".jpg"));
        extBoxes.add(new CheckBox(".jpeg"));
        extBoxes.add(new CheckBox(".gif"));
        extBoxes.add(new CheckBox(".tif"));
        extBoxes.add(new CheckBox(".tga"));
        extBoxes.add(new CheckBox(".bmp"));
        extBoxes.add(new CheckBox(".raw"));
        extensionTooltipChkBx = new Tooltip("Selects Extension to search for. \nYou can select as many as you want, including a custom one.");
        extensionTooltipCust = new Tooltip("Type a custom extension to search for. \nMatch the format above, eg.  .avi, .mov");
        sourceBtnToolTip = new Tooltip("Open the folder where your files that you want to move are located. \n After your move you will need to redo the process as they will be reset");
        destinationBtnToolTip = new Tooltip("Open the folder where you want to move the files to. \n After your move you will need to redo the process as they will be reset");
        searchBtnToolTip = new Tooltip("Searches for files in the target folder, and moves them to destination folder. \nRemoves folder selections once completed.");

        Gui.outputLog.appendText("-Output Log-\n");

    }

    //--functions
    void buildExtensionPane() {
        //This function builds the extension pane. Here i used a checkbox arraylist to remove any unnessisary typing i would have had to do to access the 8 different text boxes each time.
        //the tooltips here show when hovering the mouse over the item they are assigned to in the program

        extensionPane.add(new Label("Select The Extensions To Be Moved:"), 0, 0, 4, 1);
        for (int row = 1; row < 3; row++) {
            for (int i = 0; i < 4; i++) {
                extensionPane.add(extBoxes.get(row == 2 ? i + 4 : i), i, row);
                extBoxes.get(row == 2 ? i + 4 : i).setTooltip(extensionTooltipChkBx);
            }
        }

        customExtensionBox.setPromptText("Enter a custom extension");
        extensionPane.add(customExtensionBox, 0, 4, 4, 1);
        customExtensionBox.setTooltip(extensionTooltipCust);

        //extensionPane Padding and preferences
        extensionPane.setHgap(5);
        extensionPane.setVgap(5);
        extensionPane.setPadding(new Insets(10, 10, 0, 10));

        //testing
        //extensionPane.setGridLinesVisible(true);
    }

    void buildButtonPane() {

        buttonPane.add(openInitialFolder, 0, 2);
        buttonPane.add(selectDestinationFolder, 0, 3);
        buttonPane.add(searchForFiles, 0, 4);

        //buttonPane Padding and preferences
        buttonPane.setVgap(5);
        buttonPane.setPadding(new Insets(0, 10, 10, 10));
        openInitialFolder.setPrefWidth(192);
        selectDestinationFolder.setPrefWidth(192);
        searchForFiles.setPrefWidth(192);
        searchForFiles.setDisable(true);
        openInitialFolder.setTooltip(sourceBtnToolTip);
        selectDestinationFolder.setTooltip(destinationBtnToolTip);
        searchForFiles.setTooltip(searchBtnToolTip);

        //buttonEvents
        openInitialFolder.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getDirectory("source");
            }
        }));

        selectDestinationFolder.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getDirectory("destination");
            }
        }));

        searchForFiles.setOnMouseClicked((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                boolean selection = false;
                Gui.outputLog.appendText("\n");
                try {
                    for (int i = 0; i < 8; i++) {
                        if (extBoxes.get(i).isSelected()) {
                            selection = true;
                        }
                    }
                    if (!customExtensionBox.getText().isEmpty()) {
                        selection = true;
                    }

                    if (!selection) {
                        Alert warning = new Alert(Alert.AlertType.WARNING);
                        warning.setTitle("File Mover: Error");
                        warning.setHeaderText("MISSING EXTENSION");
                        warning.setContentText("Please select extension(s)\n"
                                + "A valid custom extension consists of a period followed by extension type\n"
                                + "This does not actually check for valid extensions so be careful");
                        warning.show();

                    } else {
                        for (int i = 0; i < 8; i++) {
                            if (extBoxes.get(i).isSelected()) {
                                filemover(extBoxes.get(i).getText());
                            }
                        }
                        if (!customExtensionBox.getText().isEmpty()) {
                            filemover(customExtensionBox.getText());
                        }
                    }

                } catch (Exception e) {

                }

                sourceDirectoryPath = "";
                outputDirectoryPath = "";
                searchForFiles.setDisable(true);
                openInitialFolder.requestFocus();
                Gui.outputLog.appendText("\n------Task Ended------");
            }
        }));

    }

    void getDirectory(String directoryType) {
        //This should get the directories no matter the os, and set the variables above to the path obtained.
        try {
            if (directoryType.equals("source")) {
                sourceDirectoryPath = SelectDir.openDir(openStage);
                Gui.outputLog.appendText("\nSource Directory: " + sourceDirectoryPath);
            } else {
                outputDirectoryPath = SelectDir.openDir(openStage);
                Gui.outputLog.appendText("\nOutput Directory:" + outputDirectoryPath);
            }
            if (!outputDirectoryPath.isBlank() && !sourceDirectoryPath.isBlank()) {
                searchForFiles.setDisable(false);
            }

        } catch (Exception e) {
            //this catches when we search for a directory and then hit cancel. We try to read both outputDirPath and sourceDirPath, and it fails due to null.
            searchForFiles.setDisable(true);
            if (directoryType.equals("source")) {
                sourceDirectoryPath = "";
            } else {
                outputDirectoryPath = "";
            }
        }

        //this section just controls the focus of each button. Doing it here rather than in the button event.
        if (sourceDirectoryPath.isBlank()) {
            openInitialFolder.requestFocus();
        } else if (sourceDirectoryPath.equals(System.getProperty("user.home")) || sourceDirectoryPath.equals(System.getProperty("user.home").substring(0, System.getProperty("user.home").indexOf(System.getProperty("file.separator")) + 1))) {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("File Mover: Error");
            warning.setHeaderText("Accessed Critical Folder");
            warning.setContentText("Please avoid choosing folders that can cause other programs \nor system critical files to have their files moved/destroyed.");
            warning.showAndWait();
            getDirectory(directoryType);
        } else if (!outputDirectoryPath.isBlank() && !sourceDirectoryPath.isBlank()) {
            searchForFiles.requestFocus();
        } else {
            selectDestinationFolder.requestFocus();
        }
    }

    void filemover(String fileExtension) {

        FindFiles fileSearch = new FindFiles();

        fileSearch.searchDirectory(new File(sourceDirectoryPath), fileExtension);//".png"checkBox1.getText()

        int fileCount = fileSearch.getResults().size();
        if (fileCount == 0) {
            Gui.outputLog.appendText("\n" + "No results found!");
        } else {
            Gui.outputLog.appendText("\n" + "Found " + fileCount + " results!\n");
            for (String file : fileSearch.getResults()) {
                //this line will output the found files,it grabs the file name from the path, and cuts it apart using the / or \ delimiter
                Gui.outputLog.appendText("\n" + file.substring(file.lastIndexOf(System.getProperty("file.separator")) + 1)); //"Found: " + 
                try {
                    MoveFile.move(file, sourceDirectoryPath, outputDirectoryPath);
                } catch (IOException e) {
                    Gui.outputLog.appendText("\n" + "move failed file already exists with same name at the destination");
                }
            }
            Gui.outputLog.appendText("\n");
        }
    }

    void buildSplitPane() {

        VBox container = new VBox();
        container.getChildren().addAll(extensionPane, buttonPane);
        container.setMinWidth(215);

        //These are set up to stop the splitpane divider from moving unless intended by user.
        splitpane.setDividerPositions(0.2, 0.8);
        SplitPane.setResizableWithParent(container, false);

        splitpane.getItems().add(container);
        outputLog.setEditable(false);
        outputLog.setMouseTransparent(false);
        //outputLog.setMinWidth(300);
        splitpane.getItems().add(outputLog);

    }

    //--scene building function
    // the final function to put everything together. Called in app.java
    Scene buildScene() {
        rootPane.setAlignment(Pos.CENTER);
        buildExtensionPane();
        buildButtonPane();
        buildSplitPane();
        rootPane.getChildren().addAll(splitpane);

        scene.setRoot(rootPane);
        return scene;
    }
}
