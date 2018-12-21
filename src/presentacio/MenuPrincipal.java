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

import java.io.File;

/**
 * Aquesta classe implementa el menu superior de l'aplicacio
 */
public class MenuPrincipal {
    VBox layout;

    public MenuPrincipal() {
        layout = new VBox();
        buildLayout();
    }

    /**
     *
     * @return el layout del menu principal
     */
    public Pane getLayout() {
        return this.layout;
    }

    /**
     * construeix el layout del menu principal
     */
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
        //Submenu carregar Horari
        MenuItem loadHorari = new MenuItem("Carregar Horari");
        loadHorari.setOnAction(e -> loadHorari());
        menuAPP.getItems().add(loadHorari);
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
        //Submenu guardar horari
        MenuItem saveAules = new MenuItem("Guardar Aules");
        saveAules.setOnAction(e -> guardarAules());
        menuAulari.getItems().add(saveAules);
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
        //Submenu guardar fiter Pla estudis
        MenuItem savePE = new MenuItem("Guardar Pla Estudis");
        savePE.setOnAction(e -> guardarPE());
        menuPE.getItems().add(savePE);
        //Addicio elements a topLayout
        layout.getChildren().add(menu);
    }

    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de guardar pla estudis
     */
    private void guardarPE() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Pla Estudis");
        fileChooser.setInitialDirectory(new File("data/PlaEstudis"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showSaveDialog(escenari);
        if (fitxer != null) {
            try {
                VistaPrincipal.ctrl.guardarPlaEstudis(fitxer.getAbsolutePath());
            } catch (Exception e) {
                // TODO: handle exception here
                PopUpWindow.display("Save Error", "Error al guardar l'aulari");
            }
        }
    }


    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de guardar aulari
     */
    private void guardarAules() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Aulari");
        fileChooser.setInitialDirectory(new File("data/Aules"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Serialized files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showSaveDialog(escenari);
        if (fitxer != null) {
            try {
                VistaPrincipal.ctrl.guardarAulari(fitxer.getAbsolutePath());
            } catch (Exception e) {
                // TODO: handle exception here
                PopUpWindow.display("Save Error", "Error al guardar l'aulari");
            }
        }
    }


    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de carregar Horari
     */
    private void loadHorari() {
        System.err.println("DEBUG: vols carregar un Horari");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Obrira fitxer Horari");
        fileChooser.setInitialDirectory(new File("data/Horaris"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showOpenDialog(escenari);
        if (fitxer != null) {
            VistaPrincipal.ctrl.carregaFitxerHorari(fitxer.getAbsolutePath());
            VistaPrincipal.refrescaTaulaAulari();
            VistaPrincipal.refrescaArbrePlaEstudis();
        }
    }

    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de carregar escena (aulari + pla d'estudis)
     */
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


    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de carregar pla d'estudis
     */
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


    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de borrar pla d'estudis de memoria
     */
    private void clearPlansEstudis() {
        VistaPrincipal.ctrl.borrarPlansEstudis();
        SeccioPlans.refrescaArbrePlansEstudis();
    }

    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de carregar un aulari
     */
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


    /**
     *
     * @return un objecte Stage funcional
     */
    private Stage getFileChooserStage() {
        Stage escenari = new Stage();
        return escenari;
    }

    /**
     * implementa les accions a dur a terme a l'hora de pitjar el menu de borrar l'aulari de memoria
     */
    private void clearAulariAction() {
        VistaPrincipal.ctrl.borrarAulari();
        VistaPrincipal.refrescaTaulaAulari();
    }
}
