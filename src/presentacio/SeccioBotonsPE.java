package presentacio;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
        //Boto eliminar grup
        Button rmGroup = new Button("Eliminar grup");
        rmGroup.setOnAction(e -> esborrarGrup());
        layout.getChildren().add(rmGroup);
        //Boto modificar atribut
        Button modify = new Button("Modificar atribut");
        modify.setOnAction(e -> modificarAtribut());
        layout.getChildren().add(modify);
    }

    private void modificarAtribut() {
        String camp = arbre.getSelectionModel().getSelectedItem().getValue();
        camp = camp.split(":")[0];
        System.err.println("DEBUG: es vol modificar el camp " + camp);
        String pe;
        String assig;
        ModifyWindow mw;
        switch (camp) {
            case "Nom" :
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Nom assignatrua", 0, pe, assig);
                break;
            case "Nivell" :
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Nivell assignatura", 1, pe, assig);
                break;
            case "n. classes teoría":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Nombre classes teoría", 2, pe, assig);
                break;
            case "Durada classes teoría":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Durada classes teoría", 3, pe, assig);
                break;
            case "n. classes problemes":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Nombre classes problemes", 4, pe, assig);
                break;
            case "Durada classes problemes":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Durada classes problemes", 5, pe, assig);
                break;
            case "n. classes laboratori":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Nombre classes laboratori", 6, pe, assig);
                break;
            case "Durada classes laboratori":
                pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
                assig = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                mw = new ModifyWindow("Durada classes laboratori", 7, pe, assig);
                break;
        }
    }

    private void esborrarGrup() {
        String pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
        String assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
        String grup = arbre.getSelectionModel().getSelectedItem().getValue();
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                if (VistaPrincipal.ctrl.existsGrupAssignatura(pe, assig, grup)) {
                    VistaPrincipal.ctrl.esborrarGrupAssignatura(pe, assig, grup);
                    SeccioPlans.refrescaArbrePlansEstudis();
                }
            }
        }
    }

    private void afegirGrup() {
        String pe = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
        String assig = arbre.getSelectionModel().getSelectedItem().getValue();
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                VistaAfegirGrup Vaf = new VistaAfegirGrup(pe, assig);
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
