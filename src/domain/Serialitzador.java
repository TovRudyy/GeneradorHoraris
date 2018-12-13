package domain;

import java.io.*;

public final class Serialitzador {

    private Serialitzador() {
    }

    public static <T> void guarda(T objecte, String path) throws IOException {
        serialize(objecte, path);
    }

    public static void guarda(String path, Object... objectes) throws IOException {
        serialize(objectes, path);
    }

    private static <T> void serialize(T object, String path) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    @SuppressWarnings("unchecked")
    public static <T> T carrega(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        T object = (T) in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
