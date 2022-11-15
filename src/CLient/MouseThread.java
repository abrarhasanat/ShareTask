package CLient;

import javafx.scene.image.Image;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MouseThread extends Thread {
    private final String ip;
    private final int port;
    private final double pos_x;
    private final double pos_y;
    private final int click;
    private String command;
    Lock lock = new ReentrantLock();

    public MouseThread(String ip, int port, Double pos_x, Double pos_y, int click) {
        this.ip = ip;
        this.port = port;
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.click = click;
    }

    public void run() {
        try {
            this.lock.lock();
            Socket socket = new Socket(ip, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeInt(Constants.SENDMOUSECLICK);
            oos.writeDouble(pos_x);
            oos.writeDouble(pos_y);
            oos.writeInt(click);
            oos.flush();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.lock.unlock();
        }

    }


}
