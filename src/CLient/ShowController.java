package CLient;

import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowController implements Initializable {
    public boolean stop = false;
    public double clicked_X = -1, clicked_Y = -1 , hight , weidth ,ImageHeight = 600 , ImageWidth  = 1000;
    public boolean isClicked = false;
    public String ip = "127.0.0.1" ;
    public  int given_port;
    @FXML
    private  AnchorPane pane ;
    @FXML
    private Button startBTN ;

    @FXML
    private ImageView image;

    @FXML
    private TextField port;

    @FXML
    void start(ActionEvent event) throws Exception {
        given_port = Integer.parseInt(port.getText());
        port.setVisible(false);
        new myAnim().start();
    }

    @FXML
    void stop(ActionEvent event) {
        stop = true;
        port.setVisible(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;
        weidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth() ;
        image.setOnMouseClicked(mouseEvent -> {
            clicked_X = mouseEvent.getX() - image.getX();
            clicked_Y = mouseEvent.getY() - image.getY();
            clicked_X = (clicked_X / ImageWidth);
            clicked_Y = (clicked_Y / ImageHeight);
            int state = Constants.LEFTCLICKED  ;
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
                state = Constants.RIGHTCLICKED ;
            new MouseThread(ip , 1234 , clicked_X , clicked_Y ,state).start();



            /*clicked_X = (clicked_X /ImageWidth) * (weidth) ;
            clicked_Y = (clicked_Y/ImageHeight) * (hight) ;

            try {
                Robot r = new Robot();
                r.mouseMove((int)clicked_X , (int)clicked_Y) ;
                r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            }catch (Exception e) {
                e.printStackTrace();
            }
            isClicked = true;

             */
        });

        image.setOnMouseReleased(mouseEvent -> {
            clicked_X = mouseEvent.getX() - image.getX();
            clicked_Y = mouseEvent.getY() - image.getY();
            clicked_X = (clicked_X / ImageWidth);
            clicked_Y = (clicked_Y / ImageHeight);
            int state = Constants.LEFTRELEASED ;
            if(mouseEvent.getButton() == MouseButton.SECONDARY)
                state = Constants.RIGHTRELEASED ;
            new MouseThread(ip , 1234 , clicked_X , clicked_Y ,state).start();
        });
        pane.setOnKeyPressed(keyEvent -> {
            //System.out.println(keyEvent.getCode());
            KeyCode keyCode = keyEvent.getCode();
            new KeyThread(ip , given_port , keyCode.getCode()).start();
        });


    }

    private class myAnim extends AnimationTimer {
        @Override
        public void handle(long l) {
            dohandle();
        }

        private void dohandle() {
            if (stop)
                stop();
            try {
                Socket socket = new Socket("localhost", Integer.parseInt(port.getText()));
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                BufferedImage bufferedImage = ImageIO.read(inputStream);
                //System.out.println("THis far2");
                Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                image.setImage(img);
                image.setPreserveRatio(false);
               // System.out.println("THis far3");
                inputStream.close();
                socket.close();
               // System.out.println("THis far4");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}


