package graphics;

import javax.swing.*;
import java.awt.*;

public interface MenuGraphicStyle {

    static void styleMenuSelectionPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panel.setBounds(230, 460, 800, 60);
        panel.setBackground(ColorFactory.createColor("theme"));
        panel.setBorder(null);
    }

    static void styleDifficultyButton(JButton button){
        button.setPreferredSize(new Dimension(150, 50));
        button.setBorderPainted(false);
        button.setBackground(ColorFactory.createColor("subTheme"));
        button.setOpaque(true);
        button.setForeground(ColorFactory.createColor("theme"));
        button.setFont(FontFactory.createFont(20));
    }

    static void styleDifficultyLabel(JLabel label){
        label.setBounds(300, 560, 180, 50);
        label.setOpaque(false);
        label.setForeground(ColorFactory.createColor("subTheme"));
        label.setBorder(BorderFactory.createLineBorder(ColorFactory.createColor("subTheme"), 3, false));
        label.setFont(FontFactory.createFont(30));
    }

    static void styleLevelInfoLabel(JLabel label){
        label.setBounds(550, 540, 800, 90);
        label.setForeground(ColorFactory.createColor("subTheme"));
        label.setFont(FontFactory.createFont(18));
    }

    static void styleStartButton(JButton button){
        button.setBackground(ColorFactory.createColor("subTheme"));
        button.setOpaque(true);
        button.setForeground(ColorFactory.createColor("theme"));
        button.setFont(FontFactory.createFont(20));
        button.setBounds(0, 650, 1200, 50);
    }
}
