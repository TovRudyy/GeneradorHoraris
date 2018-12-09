package presentacio;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;

public class MenuPrincipal {
    VBox layout;

    public MenuPrincipal() {
        layout = new VBox();
        buildLayout();
    }

    public Pane getLayout() {
        return this.layout;
    }

    private void buildLayout() {
        //Creacio Menus:
        MenuBar menu = new MenuBar();
        //Menu Aplicacio
        Menu menuAPP = new Menu();
        menuAPP.setText("Aplicació");
        //Submenu carregar Escenari
        MenuItem loadScenario = new MenuItem("Carregar Escenari");
        loadScenario.setOnAction(e -> loadScenario());
        menuAPP.getItems().add(loadScenario);
        //Menu Sortir
        MenuItem exit = new MenuItem("Sortir");
        exit.setOnAction(e -> VistaDialeg.terminate());
        menuAPP.getItems().add(new SeparatorMenuItem());
        menuAPP.getItems().addAll(exit);
        menu.getMenus().add(menuAPP);
        //Menu Aulari
        Menu menuAulari = new Menu("Aulari");
        menu.getMenus().add(menuAulari);
        //Submenu netejar Aulari
        MenuItem clearAulari = new MenuItem("Borrar Aulari");
        clearAulari.setOnAction(e -> clearAulariAction());
        menuAulari.getItems().add(clearAulari);
        //Submenu carregar fitxer Aules
        MenuItem loadAules = new MenuItem("Carregar fitxer d'aules");
        loadAules.setOnAction(e -> loadAules());
        menuAulari.getItems().add(loadAules);
        //Menu PlaEstudis
        Menu menuPE = new Menu("Plans Estudis");
        menu.getMenus().add(menuPE);
        //Submenu netejar plans estudis
        MenuItem clearPE = new MenuItem("Borrar Plans Estudis");
        clearPE.setOnAction(e -> clearPlansEstudis());
        menuPE.getItems().add(clearPE);
        //Submenu carregar fitxer pla estudis
        MenuItem loadPE = new MenuItem("Carregar fitxer pla estudis");
        loadPE.setOnAction(e -> loadPlaEstudis());
        menuPE.getItems().add(loadPE);
        //Addicio elements a topLayout
        layout.getChildren().add(menu);
    }

    private void loadScenario() {
        DirectoryChooser directChooser = new DirectoryChooser();
        directChooser.setTitle("Obrira fitxer pla estudis");
        directChooser.setInitialDirectory(new File("data/Escenaris"));
        Stage escenari = getFileChooserStage();
        File fitxer = directChooser.showDialog(escenari);
        if (fitxer != null) {
            VistaPrincipal.ctrl.borrarAulari();
            VistaPrincipal.ctrl.CarregarFitxerAules(fitxer.getAbsolutePath()+"/Aulari.json");
            VistaPrincipal.refrescaTaulaAulari();
            VistaPrincipal.ctrl.borrarPlansEstudis();
            VistaPrincipal.ctrl.CarregarFitxerPlaEstudis(fitxer.getAbsolutePath()+"/PlaEstudi.json");
            VistaPrincipal.refrescaArbrePlaEstudis();
        }
    }

    private void loadPlaEstudis() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Obrira fitxer pla estudis");
        fileChooser.setInitialDirectory(new File("data/PlaEstudis"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showOpenDialog(escenari);
        if (fitxer != null) {
            VistaPrincipal.ctrl.CarregarFitxerPlaEstudis(fitxer.getAbsolutePath());
            VistaPrincipal.refrescaArbrePlaEstudis();
        }
    }

    private void clearPlansEstudis() {
        VistaPrincipal.ctrl.borrarPlansEstudis();
        SeccioPlans.refrescaArbrePlansEstudis();
    }

    private void loadAules() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Obrir fitxer d'aules");
        fileChooser.setInitialDirectory(new File("data/Aules"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showOpenDialog(escenari);
        if (fitxer != null) {
            VistaPrincipal.ctrl.CarregarFitxerAules(fitxer.getAbsolutePath());
            VistaPrincipal.refrescaTaulaAulari();
        }
    }

    private Stage getFileChooserStage() {
        Stage escenari = new Stage();
        return escenari;
    }

    private void clearAulariAction() {
        VistaPrincipal.ctrl.borrarAulari();
        VistaPrincipal.refrescaTaulaAulari();
    }
}
