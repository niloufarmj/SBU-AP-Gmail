package AP.Model.Thread;
import static AP.App.in;
public class Listener implements Runnable {

    public static Object serverRespond;

    @Override
    public void run() {
        try {
            while (true) {
                this.handle(in.readObject());
            }
        } catch (Exception e) {
            // ignore it
        }
    }

    public void handle(Object respond) {
        //System.out.println("I'm going to handle the respond");
        serverRespond = respond;
    }

}
