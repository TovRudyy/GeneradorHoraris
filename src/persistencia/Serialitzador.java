package persistencia;

import java.io.*;

public final class Serialitzador {

    private Serialitzador() {
    }

    public static void guarda(Object objecte, String path) throws IOException {
        serialize(objecte, path);
    }

    public static void guarda(String path, Object... objectes) throws IOException {
        serialize(objectes, path);
    }

    private static void serialize(Object object, String path) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    public static Object carrega(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object object = in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
