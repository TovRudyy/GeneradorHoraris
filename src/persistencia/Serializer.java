package persistencia;

import java.io.*;

public class Serializer<T> {

    public void serialize(T object, String path) throws IOException{
        FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    @SuppressWarnings("unchecked")
    public T deserialize(String path) throws IOException, ClassNotFoundException{
        FileInputStream fileIn = new FileInputStream(path);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        T object = (T) in.readObject();
        in.close();
        fileIn.close();
        return object;
    }
}
