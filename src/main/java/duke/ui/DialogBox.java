package duke.ui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;

/**
 * The DialogBox class represents a graphical dialog box that can display text and an image.
 *
 * @author selwyn
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a new DialogBox with the specified text and image.
     *
     * @param text The text to display in the dialog box.
     * @param img  The image to display in the dialog box.
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
        dialog.setMinHeight(Region.USE_PREF_SIZE);
        displayPicture.setImage(img);
        displayPicture.setClip(new Circle(50, 50, 50));
        HBox.setMargin(displayPicture, new Insets(0, 10, 0, 10));
    }

    /**
     * Flips the dialog box to align its contents to the top left.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
        dialog.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-padding: 10px; "
                + "-fx-border-insets: 5px; -fx-font: 15px Tahoma;");
    }

    /**
     * Creates and returns a new user dialog box with the specified text and image.
     *
     * @param text The text to display in the user dialog box.
     * @param img  The image to display in the user dialog box.
     * @return A new DialogBox representing the user's dialog.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates and returns a new Duke dialog box with the specified text and image.
     * Additionally, it flips the dialog box to align its contents to the top left.
     *
     * @param text The text to display in the Duke dialog box.
     * @param img  The image to display in the Duke dialog box.
     * @return A new DialogBox representing Duke's dialog.
     */
    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
