package persistencia;

import java.io.*;

/**
 * Classe per a serialitzar i deserialitzar objectes.
 * No es pot instanciar, les dues funcions que té són estàtiques
 */
final class Serialitzador {

    private Serialitzador() {
    }

    /**
     * @param object objecte que es vol serialitzar
     * @param path ruta del fitxer on s'escriurà el fitxer serialitzat (es crearà o sobreescriurà si cal)
     * @throws IOException el fitxer és un directori, no es pot crear o no es pot obrir
     */
    static void serialize(Object object, String path) throws IOException{
        if(!path.substring(path.lastIndexOf('.')+1).equals("ser")) path += ".ser";
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    /**
     * @param path ruta del fitxer que conte l'objecte serialitzat
     * @return objecte llegit deserialitzat
     * @throws IOException el fitxer és un directori, no es pot crear o no es pot obrir
     * @throws ClassNotFoundException la classe de lobjecte llegit és desconeguda
     */
    static Object deserialize(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object object = in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
