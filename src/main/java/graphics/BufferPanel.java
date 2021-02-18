package graphics;

import javax.swing.*;
import java.awt.*;

public class BufferPanel extends JPanel{
    public BufferPanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.setBounds(555,70,550,120);
        this.setOpaque(false);
    }
}
