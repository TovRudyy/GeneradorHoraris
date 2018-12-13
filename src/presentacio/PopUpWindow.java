package presentacio;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpWindow {

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button okbutton = new Button("D'acord");

        //Clicking will set answer and close window
        okbutton.setOnAction(e -> {
            window.close();
        });;

        VBox myLayout = new VBox(10);

        //Add buttons
        myLayout.getChildren().add(label);
        myLayout.setAlignment(Pos.CENTER);
        myLayout.getChildren().addAll(okbutton);
        myLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(myLayout, 200, 60);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return true;
    }
}
