package presentacio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VistaPrincipal extends Application {
    //Constants
    private static final int width = 500;
    private static final int height = 500;
    //Variables dels controladors
    public static CtrlPresentacio ctrl;

    //Variables de la GUI
    Stage escenari;
    Scene escena;
    LayoutPrincipal layout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ControladorPresentacio
        ctrl = new CtrlPresentacio();

        layout = new LayoutPrincipal();
        escena = new Scene(layout.getLayout());
        escenari = new Stage();
        //Element Stage
        escenari = primaryStage;
        escenari.setTitle("Generador d'Horaris");
        escenari.setScene(escena);
//        escenari.setHeight(height);
//        escenari.setWidth(width);
        //Visualitzar finestra grafica
        escenari.show();
    }

}
