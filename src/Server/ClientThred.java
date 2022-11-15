package Server;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import Server.Controller;

import javax.imageio.ImageIO;
import Server.Controller;
public class ClientThred extends Thread {
    Socket socket;
    private double pos_x;
    private double pos_y;
    private int click;
    private int command;
    ObjectInputStream os ;

    ClientThred(Socket socket) {
        this.socket = socket;
    }


    public void run() {
        try {
           os = new ObjectInputStream(socket.getInputStream());
           int x = os.readInt();
           if(x == Constants.SENDMOUSECLICK)
               MouseFunction() ;
           else KeyBoardFunction() ;
           os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void KeyBoardFunction() {

    }

    private void MouseFunction() {
        try {
           pos_x = os.readDouble() ;
           pos_y = os.readDouble();
           pos_x *= Controller.weidth;
           pos_y *= Controller.height;
           click = os.readInt();
           Robot r = new Robot();
           r.mouseMove((int)pos_x , (int)pos_y);
           if(click == Constants.LEFTCLICKED) {
               r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
           }
           if(click == Constants.LEFTRELEASED){
               r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
           }
           if(click == Constants.RIGHTCLICKED) {
               r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
           }
           if(click == Constants.RIGHTRELEASED) {
               r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
           }

        }catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void closeConnection() {
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
