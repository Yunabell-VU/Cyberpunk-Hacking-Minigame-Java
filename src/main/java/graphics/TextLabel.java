package graphics;

import javax.swing.*;
import java.awt.*;

 class TextLabel extends JLabel {
    public TextLabel(String text, int width, int height){
        this.setText(text);
        this.setHorizontalAlignment(LEFT);
        this.setForeground(new Color(250, 247, 10));
        this.setPreferredSize(new Dimension(width,height));
    }
}
