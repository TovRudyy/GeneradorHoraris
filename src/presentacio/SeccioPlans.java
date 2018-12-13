package presentacio;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;

public class SeccioPlans {
    VBox layout;
    static TreeItem<String> root;
    static TreeView<String> arbre;


    public SeccioPlans() {
        layout = new VBox();
        root = new TreeItem<String>();
        arbre = new TreeView<String>();
        buildLayout();
    }

    public static void refrescaArbrePlansEstudis() {
        afegeixPlans(root);
        arbre.setRoot(root);
        arbre.refresh();
    }

    private void buildLayout() {
        //Titol
        Label titol = new Label("Plans d'Estudis carregats");
        titol.setMaxWidth(Double.MAX_VALUE);
        titol.setAlignment(Pos.CENTER);
        layout.getChildren().add(titol);
        //Arbre de Plans
        afegeixPlans(root);
        root.setExpanded(true);
        arbre.setShowRoot(false);
        arbre.setMinSize(400,700);
        layout.getChildren().add(arbre);
        //Boto generar Horari
        Button genHorari = new Button("Generar Horari");
        genHorari.setOnAction(e -> generarHorari());
        layout.getChildren().add(genHorari);
        layout.setPadding(new Insets(10));
    }

    private void generarHorari() {
        String pe = arbre.getSelectionModel().getSelectedItem().getValue();
        System.err.println("DEBUG: es vol generar l'horari de "+ pe);
        if (VistaPrincipal.ctrl.existsPlaEstudi(pe)) {
            if (VistaPrincipal.ctrl.generaHorari(pe)) {
                ArrayList<ArrayList<Queue<String>>> horari = VistaPrincipal.ctrl.getHorariSencer(pe);

                VistaHorari horari_dibuixat = new VistaHorari(pe, horari);
            }
        }
    }

    private static void afegeixPlans(TreeItem<String> root) {
        root.getChildren().clear();
        System.err.println("DEBUG: estic en SeccioPlans.afegeixPlans");
        ArrayList<String> plans = VistaPrincipal.ctrl.getPlans();
        for (String p : plans) {
            addPla(root, p);
        }
        arbre.setRoot(root);
    }

    private static void addPla(TreeItem<String> root, String p) {
        TreeItem<String> item = new TreeItem<String>(p);
        root.getChildren().add(item);
        addInfoPla(item, p);
    }

    private static void addInfoPla(TreeItem<String> root, String p) {
        ArrayList<String> assig = VistaPrincipal.ctrl.getAssignatures(p);
        for (String a : assig) {
            addAssignatura(root, a, p);
        }
    }

    private static void addAssignatura(TreeItem<String> root, String assig, String pla) {
        TreeItem<String> item = new TreeItem<String>(assig);
        root.getChildren().add(item);
        addInfoAssignatura(item, assig, pla);
    }

    private static void addInfoAssignatura(TreeItem<String> root, String assig, String pla) {
        //Nom
        String aux = VistaPrincipal.ctrl.getNomAssignatura(assig, pla);
        TreeItem<String> item = new TreeItem<String>("Nom: " + aux);
        root.getChildren().add(item);
        //Nivell
        aux = Integer.toString(VistaPrincipal.ctrl.getNivellAssignatura(assig, pla));
        item = new TreeItem<String>("Nivell: " + aux);
        root.getChildren().add(item);
        //Quantitat classes teoria
        aux = Integer.toString(VistaPrincipal.ctrl.getQtClassesTeoriaAssignatura(assig, pla));
        item = new TreeItem<String>("n. classes teoría: " + aux);
        root.getChildren().add(item);
        //Durada classes teoria
        aux = Integer.toString(VistaPrincipal.ctrl.getDuradaClassesTeoriaAssignatura(assig, pla));
        item = new TreeItem<String>("Durada classes teoría: " + aux);
        root.getChildren().add(item);
        //Quantitat classes problemes
        aux = Integer.toString(VistaPrincipal.ctrl.getQtClassesProblemesAssignatura(assig, pla));
        item = new TreeItem<String>("n. classes problemes: " + aux);
        root.getChildren().add(item);
        //Durada classes problemes
        aux = Integer.toString(VistaPrincipal.ctrl.getDuradaClassesProblemesAssignatura(assig, pla));
        item = new TreeItem<String>("Durada classes problemes: " + aux);
        root.getChildren().add(item);
        //Quantitat classes laboratori
        aux = Integer.toString(VistaPrincipal.ctrl.getQtClassesLaboratoriAssignatura(assig, pla));
        item = new TreeItem<String>("n. classes laboratori: " + aux);
        root.getChildren().add(item);
        //Durada classes laboratori
        aux = Integer.toString(VistaPrincipal.ctrl.getDuradaClassesLaboratoriAssignatura(assig, pla));
        item = new TreeItem<String>("Durada classes laboratori: " + aux);
        root.getChildren().add(item);
        //Correquisits
        TreeItem<String> corr = new TreeItem<String>("Correquisits");
        addCorrequisits(corr, assig, pla);
        root.getChildren().add(corr);
        //Grups
        item = new TreeItem<String>("Grups");
        root.getChildren().add(item);
        addGrupsAssignatura(item, assig, pla);
    }

    private static void addCorrequisits(TreeItem<String> root, String assig, String pla) {
        ArrayList<String> correquisits = VistaPrincipal.ctrl.getCorrequisitsAssignatura(assig, pla);
        for (String c : correquisits) {
            System.err.println("DEBUG: s'ha afegit el correquisit: "+ c + " a l'arbre");
            TreeItem<String> item = new TreeItem<String>(c);
            root.getChildren().add(item);
        }
    }

    private static void addGrupsAssignatura(TreeItem<String> root, String assig, String pla) {
        ArrayList<String> grups = VistaPrincipal.ctrl.getGrupsAssignatura(assig, pla);
        for (String g : grups) {
            TreeItem<String> item = new TreeItem<String>(g);
            root.getChildren().add(item);
            addInfoGrups(item, g, assig, pla);
        }
    }

    private static void addInfoGrups(TreeItem<String> root, String grup, String assig, String pla) {
        String aux;
        TreeItem<String> item;
        //Capacitat
        aux = Integer.toString(VistaPrincipal.ctrl.getCapacitatGrup(grup,assig,pla));
        item = new TreeItem<String>("Capacitat: " + aux);
        root.getChildren().add(item);
        //Horari
        aux = VistaPrincipal.ctrl.getHorariGrup(grup,assig,pla);
        item = new TreeItem<String>("Horari: " + aux);
        root.getChildren().add(item);
        //Tipus aula
        aux = VistaPrincipal.ctrl.getTipusAulaGrup(grup,assig,pla);
        item = new TreeItem<String>("Tipus aula: " + aux);
        root.getChildren().add(item);
        //Subgrups
        ArrayList<String> subgrups = VistaPrincipal.ctrl.getSubgrupsGrup(grup, assig, pla);
        for (String sg : subgrups) {
            TreeItem<String> subItem = new TreeItem<String>(sg);
            addInfoSubgrup(subItem, sg, assig, pla);
            root.getChildren().add(subItem);
        }
    }

    private static void addInfoSubgrup(TreeItem<String> root, String sg, String assig, String pla) {
        String aux;
        TreeItem<String> item;
        //Capacitat
        aux = Integer.toString(VistaPrincipal.ctrl.getCapacitatGrup(sg,assig,pla));
        item = new TreeItem<String>("Capacitat: " + aux);
        root.getChildren().add(item);
        //Horari
        aux = VistaPrincipal.ctrl.getHorariGrup(sg,assig,pla);
        item = new TreeItem<String>("Horari: " + aux);
        root.getChildren().add(item);
        //Tipus aula
        aux = VistaPrincipal.ctrl.getTipusAulaGrup(sg,assig,pla);
        item = new TreeItem<String>("Horari: " + aux);
        root.getChildren().add(item);
    }

    public Pane getLayout() {
        return this.layout;
    }

}
