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
import javafx.stage.Stage;

/**
 * Aquesta classe implementa la finestra per afegir assignatures
 */
public class VistaAfegirAssig {
    Stage escenari;
    Scene escena;
    GridPane layout;
    String plaEstudi;
    TextField inputID;
    TextField inputNom;
    TextField inputNivell;
    TextField inputNclassT;
    TextField inputDurT;
    TextField inputNclassP;
    TextField inputDurP;
    TextField inputNclassL;
    TextField inputDurL;


    public VistaAfegirAssig(String pe) {
        this.plaEstudi = pe;
        escenari = new Stage();
        layout = new GridPane();
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.setVgap(5);
        layout.setHgap(5);
        layout.setAlignment(Pos.CENTER);
        escena = new Scene(buildLayout());
        escenari.setScene(escena);
        escenari.setTitle("Afegir assignatura a " + pe);
        escenari.show();
    }

    /**
     *
     * @return retorna el layout de VistaAfegirAssig
     */
    private Parent buildLayout() {
        int row = 0;
        //ID assignatura
        Label id_assig = new Label("ID assignatura:");
        inputID = new TextField();
        layout.add(id_assig, 0,row);
        layout.add(inputID, 1,row);
        row++;

        //Nom assignatura
        Label nom = new Label("Nom assignatura:");
        inputNom = new TextField();
        layout.add(nom, 0,row);
        layout.add(inputNom, 1,row);
        row++;

        //Nivell
        Label nivell = new Label("Nivell assignatura:");
        inputNivell = new TextField();
        inputNivell.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(nivell, 0,row);
        layout.add(inputNivell, 1,row);
        row++;

        //Nombre classes teoria
        Label numClassesT = new Label("Nombre classes teoría:");
        inputNclassT = new TextField();
        inputNclassT.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(numClassesT, 0, row);
        layout.add(inputNclassT, 1, row);
        row++;

        //Duracio classes teoria
        Label durClassesT = new Label("Duració classes teoría:");
        inputDurT = new TextField();
        inputDurT.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(durClassesT, 0, row);
        layout.add(inputDurT,1, row);
        row++;

        //Nombre classes problemes
        Label numClassesP = new Label("Nombre classes problemes:");
        inputNclassP = new TextField();
        inputNclassP.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(numClassesP, 0, row);
        layout.add(inputNclassP, 1, row);
        row++;

        //Duració classes problemes
        Label durClassesP = new Label("Duració classes problemes:");
        inputDurP = new TextField();
        inputDurP.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(durClassesP, 0 , row);
        layout.add(inputDurP, 1, row);
        row++;

        //Nombre classes laboratori
        Label numClassesL = new Label("Nombre classes laboratori:");
        inputNclassL = new TextField();
        inputNclassL.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(numClassesL, 0, row);
        layout.add(inputNclassL, 1 , row);
        row++;

        //Duració classes laboratori
        Label durClassesL = new Label("Duració classes laboratori:");
        inputDurL = new TextField();
        inputDurL.textProperty().addListener((observable, oldValue, newValue) -> mustBeUnsignedInt(observable, oldValue, newValue));
        layout.add(durClassesL, 0 , row);
        layout.add(inputDurL, 1, row);
        row++;

        //Boto crear
        Button create = new Button("Crear");
        create.setOnAction(e -> crearAssignatura());
        layout.add(create, 0, row);


        //Boto cancelar
        Button cancel = new Button("Cancelar");
        cancel.setOnAction(e -> exitVista());
        layout.add(cancel, 1, row);
        row++;
        return layout;
    }

    /**
     * Impelemta l'accio a dur a terme quan es pitja el boto de cancelar
     */
    private void exitVista() {
        escenari.close();
    }

    /**
     * Implementa la funcio a dur a terme quan es pitja el boto d'afegir assignatura
     */
    private void crearAssignatura() {

        //Agafem tots els atributs
        String id = inputID.getText();
        if (VistaPrincipal.ctrl.existsAssignaturaPE(plaEstudi, id)) {
            PopUpWindow.display("ERROR", "L'assignatura " + id +" ja existeix!");
            return;
        }
        String nom = inputNom.getText();
        int nivell = Integer.parseInt(inputNivell.getText());
        int n_T = Integer.parseInt(inputNclassL.getText());
        int dur_T = Integer.parseInt(inputDurT.getText());
        int n_P = Integer.parseInt(inputNclassP.getText());
        int dur_P = Integer.parseInt(inputDurP.getText());
        int n_L = Integer.parseInt(inputNclassL.getText());
        int dur_L = Integer.parseInt(inputDurL.getText());

        VistaPrincipal.ctrl.afegirAssignatura(plaEstudi, id, nom, nivell, n_T, dur_T, n_P, dur_P, n_L, dur_L);

        VistaPrincipal.refrescaArbrePlaEstudis(); //aqui hauriem de recarregar les assignatures
        escenari.close();

    }

    /**
     * Assegura que el valor introduit unicament pugui ser un enter positiu
     * @param observable
     * @param oldValue
     * @param newValue
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
