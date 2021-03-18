package graphics;

import javax.swing.*;
import java.awt.*;

public interface Redraw {
    static void
    redraw(Container canvas, JPanel panel) {
        canvas.removeAll();
        canvas.repaint();
        canvas.add(panel);
        canvas.revalidate();
    }

    //Overloading
    static void
    redraw(JPanel canvas, JPanel panel) {
        canvas.removeAll();
        canvas.repaint();
        canvas.add(panel);
        canvas.revalidate();
    }

    static void
    clearCanvas(JPanel canvas){
        canvas.removeAll();
        canvas.repaint();
    }

}
