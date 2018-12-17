package presentacio;

public class VistaDialeg {

    static public void terminate() {
        boolean answer = ConfirmBox.display("Gen. Horaris - Confirmació", "Segur que vols sortir?");
        if (answer) {
            System.exit(0);
        }
    }

}
