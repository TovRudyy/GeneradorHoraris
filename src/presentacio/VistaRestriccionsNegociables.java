package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Aquesta classe implementa una finestra on poder modificar les restriccions negociables de cada pla d'estudis
 * @author Oleksandr Rudyy
 */
public class VistaRestriccionsNegociables {
    Stage escenari;
    Scene escena;
    VBox layout;
    String plaEstudi;
    ArrayList<CheckBox> boxesTotals = new ArrayList<CheckBox>();
    ArrayList<CheckBox> boxesActives = new ArrayList<CheckBox>();
    ComboBox<String> assignatura;
    ComboBox<String> grups;
    ComboBox<String> horaInici;
    ComboBox<String> horaFi;
    ComboBox<String> dia;

    public VistaRestriccionsNegociables(String pe) {
        this.plaEstudi = pe;
        escenari = new Stage();
        layout = new VBox();
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setPadding(new Insets(10,10,10,10));
        layout.setSpacing(20);
        escena = new Scene(buildLayout());
        escenari.setScene(escena);
        escenari.setTitle("Menú restriccions negociables de " + plaEstudi);
        escenari.show();
    }

    /**
     *
     * @return retorna el layout que ha de projectar l'objecte Scene d'aquesta finestra
     */
    private Parent buildLayout() {

        layout.getChildren().clear();
        layout.setPadding(new Insets(50,50,50,50));
        layout.setMinWidth(400);

        HBox restriccions= buildRestriccions();
        layout.getChildren().add(restriccions);

        HBox comboLayout = getComboLayut();
        layout.getChildren().add(comboLayout);

        HBox butonsLayout = getButtonsLayoyt();
        layout.getChildren().add(butonsLayout);
        return layout;
    }

    /**
     *
     * @return retorna un layout amb les restriccions existents i actives
     */
    private HBox buildRestriccions() {
        HBox ret = new HBox();
        ret.setPadding(new Insets(10,10,10,10));
        ret.setSpacing(50);
        ret.getChildren().add(buildRestriccionsTotals());
        ret.getChildren().add(buildRestriccionsActives());
        return ret;
    }

    /**
     *
     * @return retorna un layout amb les restriccions actives
     */
    private Node buildRestriccionsActives() {
        ArrayList<String> restriccions = VistaPrincipal.ctrl.getRestriccionsFlexiblesActives(this.plaEstudi);
        Label nomRestTot = new Label("Restriccions actives");
        VBox aux = new VBox();
        aux.setSpacing(10);
        aux.getChildren().add(nomRestTot);
        for (String r : restriccions) {
            System.err.println("DEBUG: afegint restriccio flexible activa: " + r);
            CheckBox box = new CheckBox(r);
            box.setLineSpacing(50);
            box.setTextAlignment(TextAlignment.RIGHT);
            boxesActives.add(box);
            aux.getChildren().add(box);
        }
        return aux;
    }

    /**
     *
     * @return retorna el layout amb els botons respectius per modificar les restriccions
     */
    private HBox getButtonsLayoyt() {
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
        Button activar = new Button("Activar");
        activar.setOnAction(e -> activarRestriccio());
        botons.getChildren().add(activar);
        Button reiniciar = new Button("Reiniciar");
        reiniciar.setOnAction(e -> reiniciar());
        botons.getChildren().add(reiniciar);
        Button sortir = new Button("Sortir");
        sortir.setOnAction(e -> sortir());
        botons.getChildren().add(sortir);
        return botons;
    }

    /**
     * Reinicia les restriccions
     */
    private void reiniciar() {
        VistaPrincipal.ctrl.reiniciarRestriccions(this.plaEstudi);
        escena.setRoot(buildLayout());
    }

    /**
     * Activa una restriccio existent
     */
    private void activarRestriccio() {
        for (CheckBox c : this.boxesTotals) {
            if (c.isSelected()) {
                VistaPrincipal.ctrl.activarRestriccio(plaEstudi, c.getText());
            }
        }
        escena.setRoot(buildLayout());
    }

    /**
     *
     * @return retorna un layout amb els camps per afegir restriccions
     */
    private HBox getComboLayut() {
        HBox comboLayout = new HBox();
        comboLayout.setAlignment(Pos.CENTER);
        comboLayout.setSpacing(10);
        assignatura = new ComboBox<String>();
        afegirAssignatures(assignatura);
        assignatura.setPromptText("Restricció negociable a afegir");
        assignatura.setOnAction(e -> actualitzaGrup());
        grups = new ComboBox<String>();
        grups.setPromptText("Grup");
        dia = new ComboBox<String>();
        dia.getItems().addAll("DILLUNS","DIMARTS","DIMECRES","DIJOUS","DIVENDRES");
        dia.setPromptText("Dia");
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

        comboLayout.getChildren().addAll(assignatura, grups, dia, horaInici, horaFi);
        return comboLayout;
    }

    /**
     *
     * @return retorna un layout amb les restriccions existents
     */
    private VBox buildRestriccionsTotals() {
        ArrayList<String> restriccions = VistaPrincipal.ctrl.getRestriccionsFlexibles(this.plaEstudi);
        Label nomRestTot = new Label("Restriccions existents");
        VBox aux = new VBox();
        aux.setSpacing(10);
        aux.getChildren().add(nomRestTot);
        for (String r : restriccions) {
            System.err.println("DEBUG: afegint restriccio flexible: " + r);
            CheckBox box = new CheckBox(r);
            box.setLineSpacing(50);
            box.setTextAlignment(TextAlignment.RIGHT);
            boxesTotals.add(box);
            aux.getChildren().add(box);
        }
        return aux;
    }

    /**
     * Cada vegada que es selecciona una assignatura actualitza el combobox dels grups d'aquesta assignatura
     */
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

    /**
     * Afegeix una assignatura al combobox d'assignatura
     * @param assignatura identifciador d'una assignatura
     */
    private void afegirAssignatures(ComboBox<String> assignatura) {
        ArrayList<String> assigs = VistaPrincipal.ctrl.getAssignatures(this.plaEstudi);
        for (String a : assigs) {
            assignatura.getItems().add(a);
        }
    }

    /**
     * Implementa les accions a dur a terme quan es pitja el boto de suavitzar restriccions
     */
    private void suavitzarRestriccio() {
        for (CheckBox c : this.boxesActives) {
            if (c.isSelected()) {
                VistaPrincipal.ctrl.suavitzarRestriccio(plaEstudi, c.getText());
            }
        }
        escena.setRoot(buildLayout());
    }

    /**
     * Implementa les accions a dur a terme quan es pitja el boto d'afegir restriccions
     */
    private void afegirResriccio() {
    }

    /**
     * Implementa les accions a dur a terme quan es pitja el boto d'eliminar restriccions
     */
    private void eliminarRestriccio() {
        for (CheckBox c : this.boxesTotals) {
            if (c.isSelected()) {
                VistaPrincipal.ctrl.eliminarRestriccio(plaEstudi, c.getText());
            }
        }
        for (CheckBox c : this.boxesActives) {
            if (c.isSelected()) {
                VistaPrincipal.ctrl.eliminarRestriccio(plaEstudi, c.getText());
            }
        }
        escena.setRoot(buildLayout());
    }

    /**
     * Implementa les accions a dur a terme quan es pitja el boto de sortir
     */
    private void sortir() {
        escenari.close();
    }
}
