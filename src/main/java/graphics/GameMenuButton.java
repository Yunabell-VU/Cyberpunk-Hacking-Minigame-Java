package graphics;

import javax.swing.*;
import java.awt.*;

public class GameMenuButton extends JButton {
    public GameMenuButton(String text){
        super(text);
        this.setForeground(Color.BLACK);
        this.setPreferredSize(new Dimension(100,30));
        this.setBorderPainted(false);
        this.setBackground(new Color(250, 247, 10));
        this.setFont(new Font("Consolas", Font.BOLD, 22));
    }
}
