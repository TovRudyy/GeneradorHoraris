package presentacio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VistaGeneral extends Application {
    //Constants
    private static final int width = 500;
    private static final int height = 200;
    //Variables dels controladors
    private CtrlMnPrincipal ctrl;

    //Variables de la GUI
    Stage escenari = new Stage();
    Planol layout;
    Scene escena;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // S'inicialitza el controlador principal de la capa de presentació
        ctrl = new CtrlMnPrincipal();
        escenari = new Stage();
        escena = new Scene();
        layout = new Planol;

        init_all(primaryStage);
        escenari.show();

    }

    private void init_escenari(Stage primaryStage) {
        escenari = primaryStage;
        primaryStage.setTitle("Generador d'Horaris");
        escenari.setOnCloseRequest(e -> {
            e.consume();
            terminate();
        });

        primaryStage.setScene(escena);
    }

    private void init_all(Stage primaryStage) {
        init_layout();
        init_escena();
        init_escenari(primaryStage);
    }


    private void init_layout() {
        toplayout.getChildren().addAll(topMenu);
        layout.setTop(toplayout);


        Label testA = new Label("TEST A");
        Label testB = new Label("TEST B");
        leftSide.getChildren().add(testA);
        rightSide.getChildren().add(testB);
        centlayout.setLeft(leftSide);
        centlayout.setRight(rightSide);
        layout.setCenter(centlayout);
    }

    private void init_escena() {
        escena.setRoot(layout.layout);
        escenari.setHeight(height);
        escenari.setWidth(width);
    }

}
