package baymax.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    /**
     * Binds the GUI to the text field and dialog container.
     */
    @FXML
    public void initialize() {
        Gui gui = Gui.getInstance();
        gui.setDialogContainer(dialogContainer);
        gui.setUserTextField(userInput);
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Handles user input by passing it to the chatbot for processing.
     * Creates two dialog boxes: one echoing the user's input and another containing the chatbot's reply.
     * Clears the user input after processing.
     *
     */
    @FXML
    private void handleUserInput() {
        Gui gui = Gui.getInstance();
        gui.getResponse();
    }
}
