package engine.UI;

import javax.swing.*;
import java.awt.*;

 class TextLabel extends JLabel {
    public TextLabel(String text, int width, int height){
        this.setText(text);
        this.setHorizontalAlignment(LEFT);
        this.setForeground(new Color(222,255,85));
        this.setPreferredSize(new Dimension(width,height));
    }
}
