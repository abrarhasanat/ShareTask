package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

import Server.Controller;

public class Server extends Thread {
    ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(1234);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (!Controller.stop) {
            try {
                Socket socket = serverSocket.accept();
                new ClientThred(socket).start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}