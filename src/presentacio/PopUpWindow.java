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

/**
 * Aquest objecte implementa una finestra informativa per a missatges d'error
 */
public class PopUpWindow {

    /**
     *
     * @param title titol de la finestra
     * @param message missatge a mostrar
     * @return retorna cert si l'usuari pitja el boto "d'acord"
     */
    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(500);
        Label label = new Label();
        label.setText(message);

        Button okbutton = new Button("D'acord");

        okbutton.setOnAction(e -> {
            window.close();
        });;

        VBox myLayout = new VBox(10);

        myLayout.getChildren().add(label);
        myLayout.setAlignment(Pos.CENTER);
        myLayout.getChildren().addAll(okbutton);
        myLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(myLayout, 200, 60);
        window.setScene(scene);
        window.showAndWait();

        return true;
    }
}
