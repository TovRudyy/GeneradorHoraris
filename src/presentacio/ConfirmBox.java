package presentacio;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        //Create two buttons
        Button yesButton = new Button("Sí");
        Button noButton = new Button("No");

        //Clicking will set answer and close window
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        VBox layoutTop = new VBox(10);
        HBox layoutCenter = new HBox(10);

        //Add buttons
        layoutTop.getChildren().add(label);
        layoutTop.setAlignment(Pos.CENTER);
        layoutCenter.getChildren().addAll(yesButton, noButton);
        layoutCenter.setAlignment(Pos.CENTER);

        BorderPane panel = new BorderPane();
        panel.setTop(layoutTop);
        panel.setCenter(layoutCenter);

        Scene scene = new Scene(panel, 40, 60);
        window.setScene(scene);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }

}