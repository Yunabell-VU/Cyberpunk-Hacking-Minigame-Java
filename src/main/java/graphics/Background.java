package graphics;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel {
    private static final long serialVersionUID = -6352788025440244338L;
    private Image image;

    public Background(Image image) {
        this.image = image;
        this.setPreferredSize(new Dimension(1200,800));
        this.setLayout(null);

    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
