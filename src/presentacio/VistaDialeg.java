package presentacio;


public class VistaDialeg {
    /**
     * Mostra una finestra amb la confirmacio de voler tancar l'aplicacio
     */
    static public void terminate() {
        boolean answer = ConfirmBox.display("Gen. Horaris - Confirmació", "Segur que vols sortir?");
        if (answer) {
            System.exit(0);
        }
    }

}
