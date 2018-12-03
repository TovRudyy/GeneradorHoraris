package presentacio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SeccioPlans {
    VBox layout;
    TableView taula;


    public SeccioPlans() {
        layout = new VBox();
        buildLayout();
    }

    private void buildLayout() {
        //Titol
        Label titol = new Label("Plans d'Estudis carregats");
        titol.setMaxWidth(Double.MAX_VALUE);
        titol.setAlignment(Pos.CENTER);
        layout.getChildren().add(titol);
        //Llista de Plans
        ListView<String> plans = new ListView<String>();
        ObservableList<String> data = FXCollections.observableArrayList(VistaPrincipal.ctrl.getPlans());
        plans.setItems(data);
        layout.getChildren().add(plans);
        layout.setPadding(new Insets(10));
    }

    public Pane getLayout() {
        return this.layout;
    }

}
