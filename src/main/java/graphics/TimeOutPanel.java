package graphics;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class TimeOutPanel extends JPanel {
    public TimeOutPanel(){
        this.setBounds(90,160,450,405);
        this.setPreferredSize(new Dimension(450,400));
        this.setBackground(new Color(250,247,10));
        JLabel textLabel = new JLabel("TIME OUT", CENTER);
        textLabel.setPreferredSize(new Dimension(450,400));
        textLabel.setFont(new Font("Consolas", Font.BOLD, 50));
        textLabel.setForeground(Color.BLACK);
        this.add(textLabel);
    }
}
