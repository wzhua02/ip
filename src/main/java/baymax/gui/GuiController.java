package baymax.gui;

import baymax.Baymax;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

public class GuiController {
    private static GuiController instance = null;
    private TextField userTextField;
    private VBox dialogContainer;
    private Baymax baymax;

    /** images for user and owen */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/hiro.png"));
    private Image baymaxImage = new Image(this.getClass().getResourceAsStream("/images/baymax.jpeg"));

    /**
     * get singleton instance of GUI controller
     *
     * @return singleton instance of GUI controller
     */
    public static GuiController getInstance() {
        if (instance == null) {
            instance = new GuiController();
        }
        return instance;
    }

    /**
     * set instance of chatbot for GUI controller
     *
     * @param b instance of chatbot
     */
    public void setBaymax(Baymax b) {
        this.baymax = b;
    }

    /**
     * set user text field for GUI controller
     *
     * @param userTextField text field for user input
     */
    public void setUserTextField(TextField userTextField) {
        this.userTextField = userTextField;
    }

    /**
     * set dialog container for GUI controller
     *
     * @param dialogContainer container for dialog
     */
    public void setDialogContainer(VBox dialogContainer) {
        this.dialogContainer = dialogContainer;
    }

    /**
     * add user dialog box to dialog container
     */
    public void addUserDialog() {
        String input = userTextField.getText();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage)
        );
        userTextField.clear();
    }

    /**
     * add owen dialog box to dialog container
     *
     * @param response response from processed command
     */
    public void addBaymaxDialog(String response) {
        dialogContainer.getChildren().addAll(
                DialogBox.getBaymaxDialog(response, baymaxImage)
        );
    }

    /**
     * call chatbot to evaluate user input
     */
    public void getResponse() {
        baymax.getResponse(userTextField.getText());
    }
}
