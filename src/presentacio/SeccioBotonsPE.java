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

import java.util.ArrayList;

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
        //Boto afegir correquisit
        Button addCorr = new Button("Afegir correquisit");
        addCorr.setOnAction(e -> afegirCorrequisit());
        layout.getChildren().add(addCorr);
        //Boto eliminar correquisit
        Button rmCorr = new Button("Eliminar correquisit");
        rmCorr.setOnAction(e -> eliminarCorrequisit());
        layout.getChildren().add(rmCorr);
        //Boto modificar atribut
        Button modify = new Button("Modificar atribut");
        modify.setOnAction(e -> modificarAtribut());
        layout.getChildren().add(modify);
    }

    private void afegirCorrequisit() {
        String pe = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
        String assig = arbre.getSelectionModel().getSelectedItem().getValue();
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                VistaAfegirCorrequisit vcor = new VistaAfegirCorrequisit(pe, assig);
                SeccioPlans.refrescaArbrePlansEstudis();
            }
        }
    }

    private void eliminarCorrequisit() {
        String corr = arbre.getSelectionModel().getSelectedItem().getValue();
        String pe;
        String assig;
        if(arbre.getSelectionModel().getSelectedItem().getParent().getValue().equals("Correquisits")) {
            pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
            assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
            if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
                if (VistaPrincipal.ctrl.existsAssignaturaPE(pe, assig)) {
                    ArrayList<String> c = new ArrayList<String>();
                    c.add(corr);
                    VistaPrincipal.ctrl.eliminarCorrequisit(pe, assig, c);
                    SeccioPlans.refrescaArbrePlansEstudis();
                }
            }
        }
    }

    private void modificarAtribut() {
        String camp = arbre.getSelectionModel().getSelectedItem().getValue();
        camp = camp.split(":")[0];
        System.err.println("DEBUG: es vol modificar el camp " + camp);
        String pe = null;
        String assig = null;
        String grup = null;
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
            case "Capacitat" :
                grup = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                //Comprovacio de si es tracta d'un grup o subgrup
                if (Integer.parseInt(grup)%10 == 0) {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
                }
                else {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                }
                mw = new ModifyWindow("Capacitat del grup", 8, pe, assig, grup);
                break;
            case "Horari" :
                grup = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                //Comprovacio de si es tracta d'un grup o subgrup
                if (Integer.parseInt(grup)%10 == 0) {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
                }
                else {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                }
                mw = new ModifyWindow("Horari del grup", 9, pe, assig, grup);
                break;
            case "Tipus aula" :
                grup = arbre.getSelectionModel().getSelectedItem().getParent().getValue();
                //Comprovacio de si es tracta d'un grup o subgrup
                if (Integer.parseInt(grup)%10 == 0) {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
                }
                else {
                    pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getParent().getValue();
                    assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
                }
                mw = new ModifyWindow("Tipus del grup", 10, pe, assig, grup);
                break;
        }
    }

    private void esborrarGrup() {
        String grup = arbre.getSelectionModel().getSelectedItem().getValue();
        String pe;
        String assig;
        //Comprovacio de si es tracta d'un grup o subgrup
        if (Integer.parseInt(grup)%10 == 0) {
            pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
            assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getValue();
        }
        else {
            pe = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getParent().getValue();
            assig = arbre.getSelectionModel().getSelectedItem().getParent().getParent().getParent().getValue();
        }
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
