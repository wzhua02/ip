package baymax.gui;

import baymax.Baymax;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controls the graphical user interface (GUI) interactions for the chatbot.
 */
public class Gui {
    private static Gui instance = null;
    private TextField userTextField;
    private VBox dialogContainer;
    private Baymax baymax;

    /** Images representing the user and Baymax. */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/hiro.png"));
    private Image baymaxImage = new Image(this.getClass().getResourceAsStream("/images/baymax.jpeg"));

    /**
     * Returns singleton instance of the GUI
     *
     * @return singleton instance of GUI
     */
    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    /**
     * Sets instance of chatbot for GUI
     *
     * @param b instance of chatbot
     */
    public void setBaymax(Baymax b) {
        this.baymax = b;
    }

    /**
     * Sets user text field for GUI
     *
     * @param userTextField text field for user input
     */
    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    /**
     * Sets dialog container for GUI
     *
     * @param dialogContainer container for dialog
     */
    public void setDialogContainer(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * Adds user dialog box to dialog container
     */
    public void addUserDialog() {
        String input = userTextField.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        userTextField.clear();
    }

    /**
     * Adds baymax dialog box to dialog container
     *
     * @param response response from processed command
     */
    public void addBaymaxDialog(String response, String dialogType) {
        dialogContainer.getChildren().addAll(
                DialogBox.getBaymaxDialog(response, baymaxImage, dialogType)
        );
    }

    /**
     * Calls chatbot to evaluate user input
     */
    public void getResponse() {
        baymax.getResponse(userTextField.getText());
    }
}
