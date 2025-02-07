package baymax.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructor for a DialogBox object.
     * @param text Text to display within the dialog box
     * @param img The image of the author of the dialog box
     */
    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.getStyleClass().add("reply-label");
    }

    /**
     * Creates a user dialog box.
     *
     * @param text The text for the user's dialog.
     * @param img The image representing the user.
     * @return A {@code DialogBox} representing the user's message.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Changes the style of the dialog box based on the command type.
     *
     * @param commandType The type of command executed.
     */
    private void changeDialogStyle(String commandType) {
        switch(commandType) {
        case "todo", "deadline", "event" -> {
            dialog.getStyleClass().add("add-label");
        }
        case "mark", "unmark" -> {
            dialog.getStyleClass().add("marked-label");
        }
        case "delete" -> {
            dialog.getStyleClass().add("delete-label");
        }
        default -> {
            // Do nothing
        }
        }
    }

    /**
     * Creates a dialog box for Baymax with the appropriate styling.
     *
     * @param text The text for Baymax's dialog.
     * @param img The image representing Baymax.
     * @param dialogType The type of response.
     * @return A {@code DialogBox} representing Baymax's message.
     */
    public static DialogBox getBaymaxDialog(String text, Image img, String dialogType) {
        var db = new DialogBox(text, img);
        db.flip();
        db.changeDialogStyle(dialogType);
        return db;
    }

}

