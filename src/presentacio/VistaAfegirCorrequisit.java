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

    private void cancelar() {
        escenari.close();
    }

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
