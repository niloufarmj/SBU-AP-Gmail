package AP.Controller;

import AP.Model.Thread.Listener;
import AP.PageLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import static AP.App.client;
import static AP.App.out;
import static AP.App.in;
import static AP.App.port;
public class IPPageController {

    @FXML
    TextField serverIP;

    public void start(ActionEvent actionEvent) {
        try {
            client = new Socket(serverIP.getText(), port);
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
            Thread listenerService = new Thread(new Listener());
            listenerService.start();
            new PageLoader().load("LogIn");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}