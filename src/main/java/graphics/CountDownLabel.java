package graphics;

import javax.swing.*;
import java.awt.*;

public class CountDownLabel extends JLabel {
    public CountDownLabel(String text){
        super(text,CENTER);
        this.setPreferredSize(new Dimension(82,46));
        this.setForeground(new Color(250, 247, 10));
        this.setBorder(BorderFactory.createLineBorder(new Color(250, 247, 10)));
        this.setFont(new Font("Consolas", Font.BOLD, 35));
    }
}
