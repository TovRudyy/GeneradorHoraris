package presentacio;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
 * Finestra de confirmació de decisions
 */
public class ConfirmBox {
    static boolean answer;

    /**
     *
     * @param title titol de la finestra
     * @param message missatge a mostrar
     * @return cert si l'usuari ha pitjat "Si" fals si ha pijat "No"
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label();
        label.setText(message);

        Button yesButton = new Button("Sí");
        Button noButton = new Button("No");

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

        return answer;
    }

}