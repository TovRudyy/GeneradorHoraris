package presentacio;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Queue;
import java.util.*;

public class VistaHorari {
    Stage escenari;
    Scene escena;
    VBox layout;
    GridPane matriuLayout;
    String plaEstudi;
    private static LinkedList<LinkedList<Queue<String>>> horari;

    private static final int posicio_hores[] = {0,0,8,9,10,11,12,13,14,15,16,17,18,19,20};
    private static final String posicio_dia[] = {"caca","caca","DILLUNS","DIMARTS","DIMECRES","DIJOUS","DIVENDRES"};
    private static boolean isOK = false;
    private static int oldPositionX, oldPositionY;
    private static int toDelete = -1;
    private static Dragboard db;

    public VistaHorari(String pla, LinkedList<LinkedList<Queue<String>>> horari) {
        this.plaEstudi = pla;
        this.horari = horari;
        escenari = new Stage();
        escena = new Scene(buildHorari());
        escenari.setTitle("Horari de " + pla);
        escenari.setScene(escena);
        escenari.show();
    }

    private Parent buildHorari() {
        //Inicialitza el layout
        configureLayout();
        //Inicialitzo el GridPane matriulayout
        configureGridPane();
        //Pla d'Estudis de l'horari
        Label plaHorari = new Label(plaEstudi);
        plaHorari.setAlignment(Pos.CENTER);
        layout.getChildren().add(plaHorari);
        //Dibuixo l'horari en sí
        dibuixaHorari(horari);
        layout.getChildren().add(matriuLayout);
        afegeixBotons();
        return layout;
    }


    private void dibuixaHorari(LinkedList<LinkedList<Queue<String>>> horari) {
        dibuixaDies(2,1);
        dibuixaHores(1,2);
        afegeixHorariSencer(2,2, horari);
    }

    private void afegeixBotons() {
        HBox buttonLayout = new HBox();
        Button saveHorari = new Button("Guardar");
        saveHorari.setOnAction(e -> guardarHorariSencer());
        Button exportarPNG = new Button("Exportar PNG");
        exportarPNG.setOnAction(e -> exportarPNG());
        Button exportarTXT = new Button("Exportar TXT");
        exportarTXT.setOnAction(e -> exportarTXT());
        Button exit = new Button("Sortir");
        exit.setOnAction(e -> escenari.close());
        buttonLayout.getChildren().addAll(saveHorari, exportarPNG, exportarTXT, exit);
        layout.getChildren().add(buttonLayout);
    }

    private void exportarTXT() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Horari");
        fileChooser.setInitialDirectory(new File("data/Horaris"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showSaveDialog(escenari);
        if (fitxer != null) {
            try {
                VistaPrincipal.ctrl.exportarHorariTXT(fitxer.getAbsolutePath(), this.plaEstudi);

            } catch (Exception e) {
                // TODO: handle exception here
                PopUpWindow.display("Export Error", "Error al exportar l'horari a TXT");
            }
        }
    }

    public void exportarPNG() {
        WritableImage image = matriuLayout.snapshot(new SnapshotParameters(), null);
        // TODO: probably use a file chooser here
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Horari");
        fileChooser.setInitialDirectory(new File("data/"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showSaveDialog(escenari);
        if (fitxer != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fitxer);
            } catch (IOException e) {
                // TODO: handle exception here
                PopUpWindow.display("Export Error", "Error al exportar l'horari a PNG");
            }
        }
    }

    private Stage getFileChooserStage() {
        Stage escenari = new Stage();
        return escenari;
    }

    private void guardarHorariSencer() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Horari");
        fileChooser.setInitialDirectory(new File("data/Horaris"));
        Stage escenari = getFileChooserStage();
        File fitxer = fileChooser.showSaveDialog(escenari);
        if (fitxer != null) {
            try {
                VistaPrincipal.ctrl.guardarHorariSencer(plaEstudi, fitxer.getAbsolutePath());
            } catch (Exception e) {
                // TODO: handle exception here
                PopUpWindow.display("Save Error", "Error al guardar l'horari");
            }
        }
    }

    private void afegeixHorariSencer(int x, int y, LinkedList<LinkedList<Queue<String>>> horari) {
        int dia = x;

        for (int j=0; j < 12; ++j) {
            LinkedList<Queue<String>> dies = horari.get(j);
            for (int i=0; i < 5; ++i) {
                Queue<String> sessions = dies.get(i);

                ListView<String> classes = new ListView();
                classes.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        db = classes.startDragAndDrop(TransferMode.MOVE);
                        String item = classes.getSelectionModel().getSelectedItem();
                        System.err.println("DEBUG: s'ha detectat un intent d'arrossegar " + item);
                        //S'obte l'index de l'element seleccionat del ListView
                        toDelete = classes.getSelectionModel().getSelectedIndex();
                        //S'obte la posicio de la ListView en la matriu
                        oldPositionX = GridPane.getColumnIndex(classes);
                        oldPositionY= GridPane.getRowIndex(classes);
                        System.err.println("DEBUG: intent d'arrossegament en X=" + oldPositionX + " Y=" + oldPositionY);
                        ClipboardContent content = new ClipboardContent();
                        content.putString(item);
                        db.setContent(content);
                        event.consume();
                    }
                });
                classes.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });
                classes.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        int newPositionX = GridPane.getColumnIndex(classes);
                        int newPositionY= GridPane.getRowIndex(classes);
                        Dragboard db = event.getDragboard();
                        String entrada[] = db.getString().split("  ");
                        String assig = entrada[0];
                        String grup = entrada[1];
                        String aula = entrada[2];
                        String oldDia = getDiaPosition(oldPositionX);
                        int oldHora = getHoraPosition(oldPositionY);
                        String newDia = getDiaPosition(newPositionX);
                        int newHora = getHoraPosition(newPositionY);
                        if (VistaPrincipal.ctrl.modificarHorari(plaEstudi,assig,grup,oldDia,oldHora,newDia,newHora,aula)) {
                            dibuixaHorari(VistaPrincipal.ctrl.getHorariSencer(plaEstudi));
                        }
                        else {
                            //PopUpWindow.display("ERROR", "El canvi infringeix les restriccions!");
                        }
                    }
                });
                String classe;
                while ((classe = sessions.poll()) != null) {
                    classes.getItems().add(classe);
                }
                matriuLayout.add(classes, dia, y);  //hem canviat la y per dia
                dia++;
            }
            y++;
            dia = x;
        }

    }

    private int getHoraPosition(int oldPositionY) {
        return posicio_hores[oldPositionY];
    }

    private String getDiaPosition(int oldPositionX) {
        return posicio_dia[oldPositionX];
    }

    private void dibuixaHores(int x, int y) {
        ArrayList<String> hores = VistaPrincipal.ctrl.getHoresHorari();
        for (String hora : hores) {
            Label h = new Label(hora);
            h.setAlignment(Pos.CENTER);
            matriuLayout.add(h, x, y);
            y++;
        }
    }

    private void dibuixaDies(int x, int y) {
        ArrayList<String> dies = VistaPrincipal.ctrl.getDiesSetmana();
        for (String dia : dies) {
            Label d = new Label(dia);
            d.setAlignment(Pos.CENTER);
            matriuLayout.add(d, x, y);
            x++;
        }
    }

    private void configureLayout() {
        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10,10,10,10));
    }

    private void configureGridPane() {
        matriuLayout = new GridPane();
        matriuLayout.setMinSize(400, 200);
        matriuLayout.setPadding(new Insets(5, 5, 5, 5));
        matriuLayout.setVgap(5);
        matriuLayout.setHgap(5);
        matriuLayout.setAlignment(Pos.CENTER);

        ColumnConstraints columns = new ColumnConstraints();
        columns.setMinWidth(100);
        columns.setHalignment(HPos.CENTER);
        matriuLayout.getColumnConstraints().add(columns);

        RowConstraints rows = new RowConstraints();
        rows.setValignment(VPos.CENTER);
        matriuLayout.getRowConstraints().add(rows);
    }

}
