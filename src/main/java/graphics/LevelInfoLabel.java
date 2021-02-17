package graphics;

import javax.swing.*;
import java.awt.*;

public class LevelInfoLabel extends JLabel {
    public LevelInfoLabel(String text){
        super(text,LEFT);
        this.setBounds(550, 540, 800, 90);
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Consolas", Font.BOLD, 18));
    }
}
