package presentacio;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class VistaAfegirGrup {
    String plaEstudi;
    String assignatura;
    Stage escenari;
    Scene escena;
    GridPane layout;
    TextField inputID;
    TextField inputCap;
    ComboBox<String> horari;
    ComboBox<String> tipus;

    public VistaAfegirGrup(String pe, String assig) {
        this.plaEstudi = pe;
        this.assignatura = assig;
        escenari = new Stage();
        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setAlignment(Pos.CENTER);
        escena = new Scene(buildLayout());
        escenari.setScene(escena);
        escenari.setTitle("Afegir grup a " + assig);
        escenari.show();
    }

    private Parent buildLayout() {
        int row = 0;
        //ID grup
        Label id_grup = new Label("ID grup:");
        inputID = new TextField();
        inputID.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(id_grup, 0, row);
        layout.add(inputID, 1, row);
        row++;
        //Capacitat gruo
        Label cap_grup = new Label("Capacitat grup:");
        inputCap = new TextField();
        inputCap.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(cap_grup, 0, row);
        layout.add(inputCap, 1, row);
        row++;
        //Tipus d'horari
        Label tipus_h = new Label("Tipus d'horari:");
        horari = new ComboBox<String>();
        horari.getItems().addAll("Matins", "Tardes");
        horari.setPromptText("Matins/Tardes");
        layout.add(tipus_h, 0, row);
        layout.add(horari, 1, row);
        row++;
        //Tipus de grup
        Label tipus_classe = new Label("Tipus de grup:");
        tipus = new ComboBox<>(SeccioAules.getTipusAulesFX());
        tipus.setPromptText("Tipus de grup");
        layout.add(tipus_classe, 0, row);
        layout.add(tipus, 1, row);
        row++;
        //Boto crear
        Button create = new Button("Crear");
        create.setOnAction(e -> afegirGrup());
        layout.add(create, 0, row);
        //Boto cancelar
        Button cancel = new Button("Cancelar");
        cancel.setOnAction(e -> cancelar());
        layout.add(cancel, 1, row);
        row++;
        return layout;
    }

    private void cancelar() {
        escenari.close();
    }

    private void afegirGrup() {
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
