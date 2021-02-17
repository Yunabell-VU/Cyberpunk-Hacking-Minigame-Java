package graphics;

import javax.swing.*;
import java.awt.*;

public class MatrixCell extends JButton {
    public MatrixCell(String text){
        super(text);
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new Dimension(60, 60));
        this.setFont(new Font("Consolas", Font.BOLD, 20));

        this.setBorderPainted(false);
        this.setOpaque(true);
        this.setForeground(new Color(250, 247, 10));
    }
}
