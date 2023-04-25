package Server;

import same.Message;
import same.MessageType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    public static final int requestPort = 1379;
    public static final String serverIP = "localhost";
    private static ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        Server.start();
    }
    public static void start() throws IOException {
        //ye thread brye server
        try {
            serverSocket = new ServerSocket(requestPort);
            Thread serverThread = new Thread(new Server(), "Server Thread");
            serverThread.start();
        } catch (IOException e) {
            // ignore it
        }

    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            try {
                new Thread(new ServerRunner(serverSocket.accept()), "Server Runner").start();
            } catch (IOException e) {
                // ignore it
            }
        }
    }
}

class ServerRunner implements Runnable {
    private Socket serverSocket;
    private ServerHandler serverHandler;

    public ServerRunner(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        //thread e client dar inja be server connect mishe
        Object clientRequest = null;
        try {
            serverHandler = new ServerHandler(serverSocket,
                    new ObjectInputStream(serverSocket.getInputStream()),
                    new ObjectOutputStream(serverSocket.getOutputStream()));

            while (true) {
                clientRequest = serverHandler.getInputStream().readObject();
                serverHandler.handle(clientRequest);
            }
        } catch (IOException | ClassNotFoundException e) {
            /* Ignore it */
            e.printStackTrace();
        } /*finally {
            userDisconnect();
        }*/
    }

}


