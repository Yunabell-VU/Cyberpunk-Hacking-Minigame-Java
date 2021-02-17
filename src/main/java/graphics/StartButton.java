package graphics;

import javax.swing.*;
import java.awt.*;

public class StartButton extends JButton {
    public StartButton(){
        super("START GAME");
        this.setOpaque(true);
        this.setForeground(new Color(250, 247, 10));
        this.setFont(new Font("Consolas", Font.BOLD, 20));
        this.setBackground(Color.BLACK);
        this.setBounds(0, 650, 1200, 50);
    }
}
