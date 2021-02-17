package graphics;

import javax.swing.*;
import java.awt.*;

public class DifficultyButton extends JButton {
    public DifficultyButton(String text){
        super(text);
        this.setPreferredSize(new Dimension(150, 50));
        this.setBorderPainted(false);
        this.setBackground(Color.BLACK);
        this.setForeground(new Color(250, 247, 10));
        this.setFont(new Font("Consolas", Font.BOLD, 20));
    }
}
