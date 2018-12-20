package presentacio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aquesta classe representa la finestra principal de l'aplicacio i comunica la GUI amb el controlador presentacio
 */
public class VistaPrincipal extends Application {
    //Constants
    private static final int width = 300;
    private static final int height = 300;
    //Variables dels controladors
    public static CtrlPresentacio ctrl;

    //Variables de la GUI
    Stage escenari;
    Scene escena;
    LayoutPrincipal layout;

    /**
     * Arranca l'aplicacio, inicialitza totes les estructures de dades i mostra la GUI
     * @param primaryStage
     * @throws Exception
     */
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
       // escenari.setHeight(height);
       // escenari.setWidth(width);
        //Visualitzar finestra grafica
        escenari.show();
    }

    /**
     * Actualiza les dades mostrades en la SeccioAules
     */
    public static void refrescaTaulaAulari() {
        SeccioAules.refrescaTaula();
    }

    /**
     * Actualitza les dades mostrades en la SeccioPlans
     */
    public static void refrescaArbrePlaEstudis() {
        SeccioPlans.refrescaArbrePlansEstudis();
    }

}
