package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class VistaRestriccionsNegociables {
    Stage escenari;
    Scene escena;
    VBox layout;
    String plaEstudi;
    ArrayList<CheckBox> boxes = new ArrayList<CheckBox>();
    ComboBox<String> assignatura;
    ComboBox<String> grups;
    ComboBox<String> horaInici;
    ComboBox<String> horaFi;

    public VistaRestriccionsNegociables(String pe) {
        this.plaEstudi = pe;
        escenari = new Stage();
        layout = new VBox();
        layout.setAlignment(Pos.CENTER_LEFT);
        escena = new Scene(buildLayout());
        escenari.setScene(escena);
        escenari.setTitle("Menú restriccions negociables de " + plaEstudi);
        escenari.show();
    }

    private Parent buildLayout() {
        layout.getChildren().clear();
        layout.setSpacing(10);
        layout.setMinWidth(400);
        ArrayList<String> restriccions = VistaPrincipal.ctrl.getRestriccionsFlexibles(this.plaEstudi);
        for (String r : restriccions) {
            CheckBox box = new CheckBox(r);
            box.setLineSpacing(50);
            box.setTextAlignment(TextAlignment.RIGHT);
            boxes.add(box);
            layout.getChildren().add(box);
        }
        HBox comboLayout = new HBox();
        comboLayout.setAlignment(Pos.CENTER);
        comboLayout.setSpacing(10);
        assignatura = new ComboBox<String>();
        afegirAssignatures(assignatura);
        assignatura.setPromptText("Restricció negociable a afegir");
        assignatura.setOnAction(e -> actualitzaGrup());
        layout.getChildren().add(assignatura);
        grups = new ComboBox<String>();
        grups.setPromptText("Grup");
        horaInici = new ComboBox<String>();
        for (int i = 8; i <=20; i++) {
            horaInici.getItems().add(Integer.toString(i));
        }
        horaInici.setPromptText("Hora Inici");
        horaFi = new ComboBox<String>();
        for (int i = 8; i <=20; i++) {
            horaFi.getItems().add(Integer.toString(i));
        }
        horaFi.setPromptText("Hora Fi");
        comboLayout.getChildren().addAll(assignatura, grups,horaInici,horaFi);
        layout.getChildren().add(comboLayout);
        HBox botons = new HBox();
        botons.setSpacing(10);
        botons.setAlignment(Pos.CENTER);
        Button afegir = new Button("Afegir");
        afegir.setOnAction(e -> afegirResriccio());
        botons.getChildren().add(afegir);
        Button eliminar = new Button("Eliminar");
        eliminar.setOnAction(e -> eliminarRestriccio());
        botons.getChildren().add(eliminar);
        Button suavitzar = new Button("Suavitzar");
        suavitzar.setOnAction(e -> suavitzarRestriccio());
        botons.getChildren().add(suavitzar);
        Button sortir = new Button("Sortir");
        sortir.setOnAction(e -> sortir());
        botons.getChildren().add(sortir);
        layout.getChildren().add(botons);
        return layout;
    }

    private void actualitzaGrup() {
        grups.getItems().clear();
        String assig = assignatura.getSelectionModel().getSelectedItem().toString();
        ArrayList<String> grps = VistaPrincipal.ctrl.getGrupsAssignatura(assig, this.plaEstudi);
        ArrayList<String> totsGrups = (ArrayList<String>) grps.clone();
        for (String g : grps) {
            totsGrups.addAll(VistaPrincipal.ctrl.getSubgrupsGrup(g,assig, this.plaEstudi));
        }
        for (String g : totsGrups) {
            grups.getItems().add(g);
        }

    }

    private void afegirAssignatures(ComboBox<String> assignatura) {
        ArrayList<String> assigs = VistaPrincipal.ctrl.getAssignatures(this.plaEstudi);
        for (String a : assigs) {
            assignatura.getItems().add(a);
        }
    }

    private void suavitzarRestriccio() {
    }

    private void afegirResriccio() {
    }

    private void eliminarRestriccio() {
    }

    private void sortir() {
        escenari.close();
    }
}
