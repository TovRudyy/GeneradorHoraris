package presentacio;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe que gestiona la modificació dels atributs d'un pla d'estudis
 */
public class ModifyWindow {

    private static int opcio;
    private  static TextField input;
    private  static ComboBox<String> input_horari;
    private  static Stage window;
    private  static String pe;
    private  static String assig;
    private  static String grup;

    /**
     *
     * @param titol titol de la finestra
     * @param opcio opcio que indica quin atribut es vol modificar exactament
     * @param pe identificador d'un pla d'estudis
     * @param assig identificador d'una assignatura
     */
    public ModifyWindow(String titol, int opcio, String pe, String assig) {
        this.opcio = opcio;
        this.pe = pe;
        this.assig = assig;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titol);
        window.setMinWidth(500);

        Scene escena = new Scene(dibuixaFinestra());
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     *
     * @param titol titol de la finestra
     * @param opcio opcio que indica quin atribut es vol modificar exactament
     * @param pe identificador d'un pla d'estudis
     * @param assig identficiador d'una assignatura
     * @param grup identificador d'un grup
     */
    public ModifyWindow(String titol, int opcio, String pe, String assig, String grup) {
        this.opcio = opcio;
        this.pe = pe;
        this.assig = assig;
        this.grup = grup;
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(titol);
        window.setMinWidth(500);

        Scene escena = new Scene(dibuixaFinestra());
        window.setScene(escena);
        window.showAndWait();
    }

    /**
     * Aqui es on es crea el layout respectiu de l'atribut a modificar
     * @return el layout de l'escena
     */
    private Parent dibuixaFinestra() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setAlignment(Pos.CENTER);
        layout.setVgap(10);
        Label nom;
        switch (opcio) {
            case 0:
                nom = new Label("Introdueix el nou nom: ");
                input = new TextField();
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 1:
                nom = new Label("Introdueix el nou nivell: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 2:
                nom = new Label("Introdueix nombre classes teoría: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 3:
                nom = new Label("Introdueix durada classes teoría: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 4:
                nom = new Label("Introdueix nonbre classes problemes: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 5:
                nom = new Label("Introdueix durada classes problemes: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 6:
                nom = new Label("Introdueix nombre classes laboratori: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 7:
                nom = new Label("Introdueix durada classes laboratori: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 8:
                nom = new Label("Introdueix capacitat del grup: ");
                input = new TextField();
                input.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
                layout.add(nom, 0, 0);
                layout.add(input, 1, 0);
                break;
            case 9:
                nom = new Label("Introdueix horari del grup: ");
                input_horari = new ComboBox<String>();
                input_horari.getItems().addAll("Matins", "Tardes");
                input_horari.setPromptText("Matins/Tardes");
                layout.add(nom, 0, 0);
                layout.add(input_horari, 1, 0);
                break;
            case 10:
                nom = new Label("Introdueix tipus del grup: ");
                input_horari = new ComboBox<String>(SeccioAules.getTipusAulesFX());
                input_horari.setPromptText("Tipus de grup");
                layout.add(nom, 0, 0);
                layout.add(input_horari, 1, 0);
                break;
        }
        //Boto aplicar
        Button apply = new Button("Aplicar");
        apply.setOnAction( e -> aplicar());
        layout.add(apply, 0,1);
        //Boto cancelar
        Button cancel = new Button("Cancelar");
        cancel.setOnAction(e -> {
            window.close();
        });
        layout.add(cancel, 1, 1);
        return layout;
    }

    /**
     * Aqui es defineixen les diferents accions a prendre pel boto d'aplicar modificacio en funcio de l'opcio
     */
    private static void aplicar() {
        switch (opcio) {
            case 0:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setNomAssignatura(pe, assig, newValue);
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 1:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setNivellAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 2:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setQtClassesTeoriaAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 3:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setDuradaClassesTeoriaAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 4:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setQtClassesProblemesAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 5:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setDuradaClassesProblemesAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 6:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setQtClassesLaboratoriAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 7:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setDuradaClassesLaboratoriAssignatura(pe, assig, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 8:
                if (!input.getText().equals("")) {
                    String newValue = input.getText();
                    VistaPrincipal.ctrl.setCapacitatGrupAssignatura(pe, assig, grup, Integer.parseUnsignedInt(newValue));
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 9:
                if (!input_horari.getValue().isEmpty()) {
                    String newValue =  input_horari.getValue().substring(0,1);
                    System.err.println("DEBUG: el nou horari es: " + newValue);
                    VistaPrincipal.ctrl.setHorariGrupAssignatura(pe, assig, grup, newValue);
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;
            case 10:
                if (!input_horari.getValue().isEmpty()) {
                    String newValue =  input_horari.getValue();
                    VistaPrincipal.ctrl.setTipusGrupAssignatura(pe, assig, grup, newValue);
                    SeccioPlans.refrescaArbrePlansEstudis();
                    window.close();
                }
                break;

        }
    }

    /**
     * Obliga a que el valor introduit nomes pugui ser un enter positiu
     * @param observable objecte al qual aplicar la comprovacio
     * @param oldValue valor antic
     * @param newValue valor nou
     */
    private void mustBeUnsignedInt(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.equals("")) {
            try {
                Integer.parseUnsignedInt(newValue);
            } catch (NumberFormatException e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        }
    }

}
