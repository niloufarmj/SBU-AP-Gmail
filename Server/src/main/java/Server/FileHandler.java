package Server;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    public Object read(String address) throws ClassNotFoundException {
        Object obj = null;
        try(FileInputStream fis = new FileInputStream(address);
            ObjectInputStream ois = new ObjectInputStream(fis)){
            obj = ois.readObject();
        } catch (IOException e) {
            System.out.println("failed to read from file! " + e.getMessage());
        }
        return obj;
    }

    public void write(String address, Object obj) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(address);
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("failed to write to file! " + e.getMessage());
        }
    }

    public byte[] transferToBytes(String address) throws IOException {
        return Files.readAllBytes(Paths.get(address));
    }

    public File transferToFile(byte[] bytes) {
        File file = null;
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            //ignore it
        }
        return file;
    }
}
