package graphics;

import javax.swing.*;
import java.awt.*;

public class DifficultyLabel extends JLabel {
    public DifficultyLabel(String text){
        super(text,CENTER);
        this.setBounds(300, 560, 180, 50);
        this.setOpaque(false);
        this.setForeground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, false));
        this.setFont(new Font("Consolas", Font.BOLD, 30));
    }
}
