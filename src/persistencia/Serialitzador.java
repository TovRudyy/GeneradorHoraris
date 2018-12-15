package persistencia;

import java.io.*;

final class Serialitzador {

    private Serialitzador() {
    }

    static void serialize(Object object, String path) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    static Object deserialize(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object object = in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
