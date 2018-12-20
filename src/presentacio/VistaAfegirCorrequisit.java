package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Aquesta classe implementa la finestra per afegir correquisits
 */
public class VistaAfegirCorrequisit {
    Stage escenari;
    Scene escena;
    GridPane layout;
    String plaEstudi;
    String assig;
    TextField inputCorr;


    public VistaAfegirCorrequisit(String pe, String assig) {
        this.plaEstudi = pe;
        this.assig = assig;
        escenari = new Stage();
        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setAlignment(Pos.CENTER);
        escena = new Scene(buildLayout());
        escenari.setScene(escena);
        escenari.setTitle("Afegir assignatura correquisit a " + assig);
        escenari.show();
    }

    /**
     *
     * @return retorna el layout de VistaAfegirCorrequisit
     */
    private Parent buildLayout() {
        Label nom = new Label("Introdueix nom de l'assignatura: ");
        layout.add(nom,0,0);
        inputCorr = new TextField();
        layout.add(inputCorr, 1, 0);
        Button afegir = new Button("Afegir");
        afegir.setOnAction(e -> afegirCorrequisit());
        layout.add(afegir, 0, 1);
        Button cancelar = new Button("Cancelar");
        cancelar.setOnAction(e -> cancelar());
        layout.add(cancelar, 1, 1);
        return layout;
    }

    /**
     * Implementa l'accio a dur a terme quan es pitja el boto de cancelar
     */
    private void cancelar() {
        escenari.close();
    }

    /**
     * Implementa la funcio a dur a terme quan es pitja el boto d'afegir correquisit
     */
    private void afegirCorrequisit() {
        String assig_aux = inputCorr.getText(); //suposem que hi poden haver diverses paraules
        String assig[] = assig_aux.split(" ");

        ArrayList<String> c = new ArrayList<>();
       for (String a : assig) {
           if (VistaPrincipal.ctrl.existsAssignaturaPE(plaEstudi, a)) {
               System.err.println("DEBUG: vols afegir el correquisit " + a + " a " + this.assig);
               if (!a.equals(this.assig)) {
                   c.add(a);
               } else
                   PopUpWindow.display("ERROR", "No es pot afegir de correquisit d'una assignatura a ella mateixa!");
           }
           else
               PopUpWindow.display("ERROR", "No existeix l'assignatura: "+assig);
       }
        VistaPrincipal.ctrl.afegirCorrequisit(plaEstudi, this.assig, c);
        SeccioPlans.refrescaArbrePlansEstudis();
        escenari.close();

    }
}
