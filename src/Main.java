import javafx.application.Application;
import presentacio.CtrlMnPrincipal;
import presentacio.CtrlPresentacio;
import presentacio.VistaPrincipal;

public class Main {

    public static void main(String[] args) {
        //Per utilitzar la GUI:
        Application.launch(VistaPrincipal.class, args);

        //Per utilitzar la CLI:
       /* CtrlMnPrincipal GH = new CtrlMnPrincipal();
        try {
            GH.runGeneradorHoraris();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
