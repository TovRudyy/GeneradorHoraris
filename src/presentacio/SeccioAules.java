package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SeccioAules {
    VBox layout;
    TableView<AulaFX> taula;


    public SeccioAules() {
        layout = new VBox();
        taula = new TableView();
        buildLayout();
    }

    private void buildLayout() {
        //Titol
        Label titol = new Label("Aules Carregades");
        titol.setMaxWidth(Double.MAX_VALUE);
        titol.setAlignment(Pos.CENTER);
        layout.getChildren().add(titol);
        //Taula
        TableColumn id = new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<AulaFX, String>("id"));
        TableColumn cap = new TableColumn("Capacitat");
        cap.setCellValueFactory(new PropertyValueFactory<AulaFX, Integer>("cap"));
        TableColumn tipus = new TableColumn("Tipus");
        tipus.setCellValueFactory(new PropertyValueFactory<AulaFX, String>("tipus"));
        taula.setItems(VistaPrincipal.ctrl.getDataAula());
        taula.getColumns().addAll(id,cap,tipus);
        //Opcions de la Taula
        taula.setMinSize(400,700);
        taula.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setMaxWidth(1f * Integer.MAX_VALUE*30);
        cap.setMaxWidth(1f * Integer.MAX_VALUE*25);
        tipus.setMaxWidth(1f * Integer.MAX_VALUE*45);
        //Layout
        layout.getChildren().add(taula);
        layout.setPadding(new Insets(10));
    }

    public Pane getLayout() {
        return this.layout;
    }

}
