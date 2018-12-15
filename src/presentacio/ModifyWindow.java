package presentacio;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifyWindow {

    private static int opcio;
    private  static TextField input;
    private  static Stage window;
    private  static String pe;
    private  static String assig;

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
        }
    }

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
