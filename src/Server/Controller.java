package Server;

import com.sun.glass.events.KeyEvent;
import com.sun.glass.events.MouseEvent;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;


import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public static boolean stop = false;
    public static double Ratio = 1.00;
   public static double height, weidth;
    public int i = 0;
    public ServerSocket serverSocket = null;
    Animation animation;
    @FXML
    private TextField Port;

    @FXML
    public void StopAction() {
        stop = true;
    }

    private void Compress() throws Exception {
        File input = new File(System.getProperty("user.dir") + "/2.jpg");
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(System.getProperty("user.dir") + "/0.jpg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality((float) Ratio);  // Change the quality value you prefer
        writer.write(null, new IIOImage(image, null, null), param);
        os.close();
        ios.close();
        writer.dispose();

    }

    @FXML
    void SSsender(ActionEvent event) throws AWTException {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(Port.getText()));
            new Server().start();
        } catch (IOException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("Could not create a server");
            a.setContentText("Please try with another port");
            a.showAndWait();
        }
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_WINDOWS);
        r.keyPress(KeyEvent.VK_D);
        r.keyRelease(KeyEvent.VK_WINDOWS);
        r.keyRelease(KeyEvent.VK_D);
        new Anim().start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        weidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();

    }

    private class Anim extends AnimationTimer {
        @Override
        public void handle(long now) {
            try {
                dohandle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void dohandle() throws Exception {
            if (stop) {
                stop();
                serverSocket.close();
            }
            Socket socket = serverSocket.accept();
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            BufferedImage img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ImageIO.write(img, "jpg", outputStream);
            outputStream.close();

            /*
            if (x == Constants.SENDMOUSECLICK) {
                double x1 = ois.readDouble();
                double y1 = ois.readDouble();
                int click = ois.readInt();
                ois.close();
                Robot r = new Robot();
                r.mouseMove((int) x1, (int) y1);
                if (click == Constants.LEFTCLICKED) r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                if (click == Constants.LEFTRELEASED) r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                if (click == Constants.RIGHTCLICKED) r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                if (click == Constants.RIGHTRELEASED) r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            }
            if (x == Constants.SENDKEYBOARD) {
                Robot r = new Robot();
                r.keyPress(ois.readInt());
                r.keyRelease(ois.readInt());
            }

             */
            //  ois.close();
            socket.close();
        }
    }
}