package presentacio;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class VistaHorari {
    Stage escenari;
    Scene escena;
    VBox layout;
    GridPane matriuLayout;
    String plaEstudi;
    ArrayList<ArrayList<Queue<String>>> horari;

    public VistaHorari(String pla, ArrayList<ArrayList<Queue<String>>> horari) {
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
        dibuixaHorari();
        layout.getChildren().add(matriuLayout);
        return layout;
    }

    private void dibuixaHorari() {
        dibuixaDies(1,1);
        dibuixaHores(0,2);
        afegeixHorariSencer(1,2);
    }

    private void afegeixHorariSencer(int x, int y) {
        int dia = x;

        for (int j=0; j < 12; ++j) {
            ArrayList<Queue<String>> dies = horari.get(j);
            for (int i=0; i < 5; ++i) {
                Queue<String> sessions = dies.get(i);

                ListView<String> classes = new ListView();
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

    private void afegeix_dades() {
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
        matriuLayout.setPadding(new Insets(10, 10, 10, 10));
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
