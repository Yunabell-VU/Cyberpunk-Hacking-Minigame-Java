package graphics;

import javax.swing.*;
import java.awt.*;

public class DaemonLabel extends JPanel {
    public DaemonLabel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.setOpaque(false);
        this.setPreferredSize(new Dimension(548, 65));
    }
}
