package domain;

import java.io.*;

public final class Serialitzador {

    private Serialitzador() {
    }

    private static class Serializer<T> {

        private void serialize(T object, String path) throws IOException{
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
        }

        @SuppressWarnings("unchecked")
        private T deserialize(String path) throws IOException, ClassNotFoundException{
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            T object = (T) in.readObject();
            in.close();
            fileIn.close();
            return object;
        }
    }

    public static <T> void guarda(T objecte, String path) throws IOException {
        Serializer<T> serializer = new Serializer<>();
        serializer.serialize(objecte, path);
    }

    public static void guarda(String path, Object... objectes) throws IOException {
        Serializer<Object[]> serializer = new Serializer<>();
        serializer.serialize(objectes, path);
    }

    public static <T> T carrega(String path) throws IOException, ClassNotFoundException{
        Serializer<T> serializer = new Serializer<>();
        return serializer.deserialize(path);
    }
}
