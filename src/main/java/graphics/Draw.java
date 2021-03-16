package graphics;

import javax.swing.*;
import java.awt.*;

public interface Draw {
    public static void redraw(Container canvas, JPanel panel){
        canvas.removeAll();
        canvas.repaint();
        canvas.add(panel);
        canvas.revalidate();
    }

    //Overloading
    public static void redraw(JPanel canvas, JPanel panel){
        canvas.removeAll();
        canvas.repaint();
        canvas.add(panel);
        canvas.revalidate();
    }
}
