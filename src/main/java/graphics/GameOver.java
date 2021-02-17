package graphics;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class GameOver extends JPanel {
    public GameOver(){
        this.setPreferredSize(new Dimension(450,400));
        this.setBackground(new Color(255, 87, 81, 90));
        JLabel textLabel = new JLabel("TIME OUT", CENTER);
        textLabel.setPreferredSize(new Dimension(450,400));
        textLabel.setFont(new Font("Consolas", Font.BOLD, 50));
        textLabel.setForeground(new Color(255, 87, 81));
        this.add(textLabel);
    }
}
