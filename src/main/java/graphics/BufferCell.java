package graphics;

import javax.swing.*;
import java.awt.*;

public class BufferCell extends JLabel {
    public BufferCell(String text){
        super(text,CENTER);
        this.setPreferredSize(new Dimension(35,35));
        this.setFont(new Font("Consolas", Font.BOLD, 18));
        this.setForeground(new Color(250, 247, 10));
        this.setBorder(BorderFactory.createDashedBorder(new Color(250, 247, 10, 90), 12, 5));
    }
}
