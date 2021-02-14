package engine.UI;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    public Panel(int width, int height){
        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.BLACK);
        this.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        this.setBorder(null);
    }
}
