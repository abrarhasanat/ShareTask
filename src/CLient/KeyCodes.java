package CLient;

import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class KeyCodes {
    public static HashMap<KeyCode , Integer>keys ;
    public static void main(String[] args) throws IllegalAccessException {
        Field[] fields = java.awt.event.KeyEvent.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                String s = f.getName();
                int i = s.indexOf("_");
                String t = s.substring(i + 1, s.length());
               System.out.println(t);
            }


        }
    }
}

