package graphics;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String text){
        this.setText(text);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(60, 60));
        this.setFont(new Font("Consolas", Font.BOLD, 20));

        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setForeground(new Color(222, 255, 85));
    }
}
