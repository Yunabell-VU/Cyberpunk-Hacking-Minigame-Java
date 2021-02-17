package graphics;

import javax.swing.*;
import java.awt.*;

public class DaemonCell extends JLabel {
    public DaemonCell(String text){
        super(text,CENTER);
        this.setForeground(new Color(250, 247, 10));
        this.setPreferredSize(new Dimension(40,40));
        this.setFont(new Font("Consolas", Font.BOLD, 18));
    }
}
