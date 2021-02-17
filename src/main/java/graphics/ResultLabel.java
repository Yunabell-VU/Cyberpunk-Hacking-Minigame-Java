package graphics;

import javax.swing.*;
import java.awt.*;

public abstract class ResultLabel extends JLabel {
    protected ResultLabel(String text){
        super(text, LEFT);
        this.setPreferredSize(new Dimension(548,65));
        this.setForeground(new Color(0, 0, 0, 98));
        this.setOpaque(true);
        this.setFont(new Font("Consolas",Font.BOLD, 20));
    }
}
