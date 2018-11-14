import presentacio.ControladorPresentacioMenuPrincipal;

public class Main {
    public static void main(String[] args) {
        ControladorPresentacioMenuPrincipal GH = new ControladorPresentacioMenuPrincipal();
        try {
            GH.runGeneradorHoraris();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
