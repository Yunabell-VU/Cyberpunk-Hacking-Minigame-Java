package graphics;

import javax.swing.*;
import java.awt.*;

public class MenuGraphicStyle {
    static Color themeColor = new Color(250, 247, 10);
    static Color subThemeColor = Color.BLACK;
    static String fontStyle = "Consolas";

    private MenuGraphicStyle() { throw new IllegalStateException("Utility class"); }

    public static void styleMenuSelectionPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0));
        panel.setBounds(230, 460, 800, 60);
        panel.setBackground(themeColor);
        panel.setBorder(null);
    }

    public static void styleDifficultyButton(JButton button){
        button.setPreferredSize(new Dimension(150, 50));
        button.setBorderPainted(false);
        button.setBackground(subThemeColor);
        button.setOpaque(true);
        button.setForeground(themeColor);
        button.setFont(new Font(fontStyle, Font.BOLD, 20));
    }

    public static void styleDifficultyLabel(JLabel label){
        label.setBounds(300, 560, 180, 50);
        label.setOpaque(false);
        label.setForeground(subThemeColor);
        label.setBorder(BorderFactory.createLineBorder(subThemeColor, 3, false));
        label.setFont(new Font(fontStyle, Font.BOLD, 30));
    }

    public static void styleLevelInfoLabel(JLabel label){
        label.setBounds(550, 540, 800, 90);
        label.setForeground(subThemeColor);
        label.setFont(new Font(fontStyle, Font.BOLD, 18));
    }

    public static void styleStartButton(JButton button){
        button.setBackground(subThemeColor);
        button.setOpaque(true);
        button.setForeground(themeColor);
        button.setFont(new Font(fontStyle, Font.BOLD, 20));
        button.setBounds(0, 650, 1200, 50);
    }
}
