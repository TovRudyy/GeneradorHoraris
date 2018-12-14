package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

public class SeccioBotonsPE {
    TreeView<String> arbre;
    VBox layout;

    public SeccioBotonsPE(TreeView<String> arbrePE) {
        this.arbre = arbrePE;
        layout = new VBox();
        layout.setPadding(new Insets(5, 5, 5, 5));
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        buildLayout();
    }

    private void buildLayout() {
        //Boto afegir assignatura
        Button addAssig = new Button("Afegir assig.");
        addAssig.setOnAction(e -> afegirAssignatura());
        layout.getChildren().add(addAssig);
        //Boto eliminar assignatura
        Button rmAssig = new Button("Eliminar assig.");
        rmAssig.setOnAction(e -> esborrarAssignatura());
        layout.getChildren().add(rmAssig);
        //Boto afegir grup
        Button addGroup = new Button("Afegir grup");
        addGroup.setOnAction(e -> afegirGrup());
        layout.getChildren().add(addGroup);
    }

    private void afegirGrup() {
        String pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
        String assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
        String grup = arbre.getSelectionModel().getSelectedItem().getValue();
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                if (!VistaPrincipal.ctrl.existsGrupAssignatura(pe, assig, grup)) {
                    VistaAfegirGrup Vaf = new VistaAfegirGrup(pe, assig);
                }
            }
        }
    }

    private void esborrarAssignatura() {
        String pe = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
        String assig = arbre.getSelectionModel().getSelectedItem().getValue();
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                VistaPrincipal.ctrl.esborrarAssignaturaPE(pe, assig);
                SeccioPlans.refrescaArbrePlansEstudis();
            }
        }
    }

    private void afegirAssignatura() {
        String pe = arbre.getSelectionModel().getSelectedItem().getValue();
        System.err.println("DEBUG: es vol afegir una assignatura a  "+ pe);
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            VistaAfegirAssig Vaf = new VistaAfegirAssig(pe);
        }
    }

    public Node getLayout() {
        return layout;
    }
}
