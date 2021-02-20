package graphics;

import javax.swing.*;
import java.awt.*;

public class MenuBar extends JPanel {
    public MenuBar(){
        this.setBounds(502,490,700,50);
        this.setOpaque(false);
        //setBorder(BorderFactory.createLineBorder(new Color(250, 247, 10)));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 86, 6));
    }
}
