package graphics;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private final transient Image image;

    public Background(String text) {
        if(text.equals("MENU")) this.image = new ImageIcon("src/main/java/image/background4.jpg").getImage();
        else this.image = new ImageIcon("src/main/java/image/gamePanel2.jpg").getImage();

        this.setPreferredSize(new Dimension(1200,800));
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
