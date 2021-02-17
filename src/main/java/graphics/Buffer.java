package graphics;

import javax.swing.*;
import java.awt.*;

public class Buffer extends JPanel{
    public Buffer(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        this.setBounds(555,70,550,120);
        this.setOpaque(false);
    }
}
