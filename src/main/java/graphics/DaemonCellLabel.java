package graphics;

import javax.swing.*;
import java.awt.*;

public class DaemonCellLabel extends JLabel {
    public DaemonCellLabel(String text){
        super(text,CENTER);
        this.setForeground(new Color(250, 247, 10));
        this.setPreferredSize(new Dimension(40,40));
        this.setFont(new Font("Consolas", Font.BOLD, 18));
    }
}
