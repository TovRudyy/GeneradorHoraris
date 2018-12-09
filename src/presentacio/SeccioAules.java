package presentacio;

import domain.Aula;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class SeccioAules {
    VBox layout;
    static TableView<AulaFX> taula;
    private static ObservableList<String> tipusAulesFX = FXCollections.observableArrayList();
    TextField idInput, capInput;
    ComboBox<String> tipusInput = new ComboBox<>(tipusAulesFX);

    public SeccioAules() {
        layout = new VBox();
        taula = new TableView();
        taula.setEditable(true);
        taula.setPlaceholder(new Label("No s'han carregat aules"));
        tipusAulesFX.addAll("TEORIA","PROBLEMES","LAB_FISICA","LAB_ELECTRONICA","LAB_INFORMATICA");
        buildLayout();
    }

    public static void refrescaTaula() {
        taula.getItems().clear();
        taula.setItems(VistaPrincipal.ctrl.getDataAula());
    }

    private void buildLayout() {
        //Titol
        Label titol = new Label("Aules Carregades");
        titol.setMaxWidth(Double.MAX_VALUE);
        titol.setAlignment(Pos.CENTER);
        layout.getChildren().add(titol);
        //Taula
        taula = buildTaula();
        //Botons afegir/eliminar
        HBox botons = addBotons();
        //Layout
        layout.getChildren().addAll(taula,botons);
        layout.setPadding(new Insets(10));
    }

    private HBox addBotons() {
        idInput = new TextField();
        idInput.setPromptText("Identificador");
        capInput = new TextField();
        capInput.setPromptText("Capacitat");
        Button addButton = new Button("Afegir");
        addButton.setOnAction(e -> afegirFila());
        Button deleteButton = new Button("Eliminar");
        deleteButton.setOnAction(e -> eliminarFila());
        HBox layout = new HBox();
        tipusInput.setPromptText("Tipus d'aula");
        layout.getChildren().addAll(idInput,capInput,tipusInput,addButton,deleteButton);
        return layout;
    }

    private void eliminarFila() {
        ObservableList<AulaFX> totesAules, aulaSeleccionada;
        totesAules = taula.getItems();
        aulaSeleccionada = taula.getSelectionModel().getSelectedItems();
        aulaSeleccionada.forEach(aula -> {
            VistaPrincipal.ctrl.removeAula(aula.getId());
            totesAules.remove(aula);
        });
    }

    private void afegirFila() {
        String id = idInput.getText();
        if (id.isEmpty()) {
            System.err.println("DEBUG: Error, no es pot afegir una aula sense identificador!");
            return;
        }
        int capacitat;
        try {
            capacitat = Integer.parseInt(capInput.getText());
        }
        catch (Exception e) {
            System.err.println("DEBUG: Error, s'ha volgut afegir una aula amb una capacitat inacceptable");
            return;
        };
        if (capacitat < 0 || capacitat > 999) {
            System.err.println("DEBUG: Error, s'ha volgut afegir una aula amb una capacitat inacceptable");
            return;
        }
        if (tipusInput.getSelectionModel().isEmpty()) {
            System.err.println("DEBUG: Error, no es pot afegir una aula sense escollir-ne el tipus!");
            return;
        }
        String tipus = tipusInput.getValue();
        if (VistaPrincipal.ctrl.afegirAula(id, capacitat, tipus)) {
            AulaFX nova = new AulaFX(id, capacitat, tipus);
            taula.getItems().add(nova);
            idInput.clear();
            capInput.clear();
            tipusInput.getSelectionModel().clearSelection();
        }
        else {
            System.err.println("DEBUG: ERROR, no s'ha pogut afegir l'aula");
        }

    }

    private TableView<AulaFX> buildTaula() {
        //Columna Identificador
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<AulaFX, String>("id"));
        id.setCellValueFactory(new PropertyValueFactory<AulaFX, String>("id"));
        id.setCellFactory(TextFieldTableCell.forTableColumn());
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //Obtinc l'objecte AulaFX que es vol editar
                AulaFX a = (AulaFX) event.getTableView().getSelectionModel().getSelectedItem();
                System.err.println("DEBUG: vols cambiar el id de l'aula " + a.getId() + " pel nou id " + event.getNewValue());
                //Comprovo si el nou id està repetit
                if (!VistaPrincipal.ctrl.setIdentificadorAula(a.getId(), (String) event.getNewValue())) {
                    a.setId((String) event.getOldValue());
                    //Hack perquè es refresquin els valors de les caselles de la columna
                    id.setVisible(false);
                    id.setVisible(true);
                }
                //Si estem aqui significa que s'ha actualitzat correctament el id de l'aula
                else {
                    a.setId((String)event.getNewValue());
                }
            }
        });
        //Columna Capacitat
        TableColumn cap = new TableColumn("Capacitat");
        cap.setCellValueFactory(new PropertyValueFactory<AulaFX, Integer>("cap"));
        cap.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        //Defineixo les accions a prendre quan es modifica la casella
        cap.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                //Obtinc l'objecte AulaFX que es vol editar
                AulaFX a = (AulaFX) event.getTableView().getSelectionModel().getSelectedItem();
                System.err.println("DEBUG: vols afegir el valor " + event.getNewValue() + " a la capacitat de l'aula " + a.getId());
                //Comprovo si l'enter introduït és acceptable
                int nou = (int) event.getNewValue();
                if (nou < 0 || nou > 999) {
                    a.setCap((int) event.getOldValue());
                    //Hack perquè es refresquin els valors de les caselles de la columna
                    cap.setVisible(false);
                    cap.setVisible(true);
                }
                else {
                    a.setCap(nou);
                    VistaPrincipal.ctrl.setCapacitatAula(a.getId(), nou);
                }
            }
        });
        //Columna Tipus
        TableColumn tipus = new TableColumn("Tipus");
        tipus.setCellValueFactory(new PropertyValueFactory<AulaFX, String>("tipus"));
        tipus.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), tipusAulesFX));
        tipus.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                AulaFX a = (AulaFX) event.getTableView().getSelectionModel().getSelectedItem();
                String tipus = (String) event.getNewValue();
                VistaPrincipal.ctrl.setTipusAula(a.getId(), tipus);

            }
        });
        refrescaTaula();
        taula.getColumns().addAll(id,cap,tipus);
        //Opcions de la Taula
        taula.setMinSize(400,700);
        taula.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setMaxWidth(1f * Integer.MAX_VALUE*30);
        cap.setMaxWidth(1f * Integer.MAX_VALUE*25);
        tipus.setMaxWidth(1f * Integer.MAX_VALUE*45);
        taula.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        return taula;
    }


    public Pane getLayout() {
        return this.layout;
    }

}
