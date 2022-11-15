package CLient;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KeyThread extends Thread {
    private final String ip;
    private final int port;
    private final int command;

    public KeyThread(String ip, int port, int command) {
        this.ip = ip;
        this.port = port;
        this.command = command;
    }

    Lock lock = new ReentrantLock();

    public void run() {
        try {
           this.lock.lock();
            Socket socket = new Socket(ip, port);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());oos.writeInt(Constants.SENDKEYBOARD);
            oos.writeObject(command);
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

