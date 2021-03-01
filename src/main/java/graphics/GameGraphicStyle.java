package graphics;

import javax.swing.*;
import java.awt.*;

public class GameGraphicStyle {
    static Color themeColor = new Color(250, 247, 10);
    static Color subThemeColor = Color.BLACK;
    static Color opaqueThemeColor = new Color(250, 247, 10, 90);
    static Color succeedColor = new Color(27, 213, 117);
    static Color failColor = new Color(255, 87, 81);
    static Color resultFontColor = new Color(0, 0, 0, 98);
    static Color matrixCellSelectedColor = new Color(70, 44, 84);
    static Color matrixCellAvailableColor = new Color(41, 44, 57);
    static Color matrixCellMouseEnterColor = Color.CYAN;
    static String fontStyle = "Consolas";

    private GameGraphicStyle() { throw new IllegalStateException("Utility class"); }

    public static void styleBufferCell(JLabel label){
        label.setPreferredSize(new Dimension(35,35));
        label.setFont(new Font(fontStyle, Font.BOLD, 18));
        label.setForeground(themeColor);
        label.setBorder(BorderFactory.createDashedBorder(opaqueThemeColor, 12, 5));
    }

    public static void styleBufferPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBounds(555,70,550,120);
        panel.setOpaque(false);
    }

    public static void styleCodeMatrixPanel(JPanel panel, int matrixSpan){

        panel.setBounds(90,192,449,370);
        panel.setOpaque(false);

        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        panel.setLayout(gridLayout);
    }

    public static void styleMatrixCellButton(JButton button){
        button.setBackground(subThemeColor);
        button.setPreferredSize(new Dimension(60, 60));
        button.setFont(new Font(fontStyle, Font.BOLD, 20));

        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setForeground(themeColor);
    }

    public static void styleMatrixCellSelected(JButton button){
        button.setForeground(matrixCellSelectedColor);
    }

    public static void styleMatrixCellAvailable(JButton button){
        button.setBackground(matrixCellAvailableColor);
    }

    public static void styleMatrixCellMouseEnter(JButton button){
        button.setForeground(matrixCellMouseEnterColor);
    }
    public static void styleMatrixCellMouseExit(JButton button){
        button.setForeground(themeColor);
    }

    public static void styleCountDownLabel(JLabel label){
        label.setPreferredSize(new Dimension(82,46));
        label.setForeground(themeColor);
        label.setBorder(BorderFactory.createLineBorder(themeColor));
        label.setFont(new Font(fontStyle, Font.BOLD, 35));
    }

    public static void styleDaemonCellLabel(JLabel label){
        label.setForeground(themeColor);
        label.setPreferredSize(new Dimension(40,40));
        label.setFont(new Font(fontStyle, Font.BOLD, 18));
    }

    public static void styleDaemonCellNotAdded(JLabel label){
        label.setForeground(Color.WHITE);
    }

    public static void styleDaemonCellSelected(JLabel label){
        label.setBorder(BorderFactory.createLineBorder(themeColor));
    }

    public static void styleDaemonsPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(548, 65));
    }

    public static void styleDaemonPanel(JPanel panel){
        panel.setBounds(560,200,550,350);
        panel.setOpaque(false);
    }

    public static void styleResultLabel(JLabel label){
        label.setPreferredSize(new Dimension(548,65));
        label.setForeground(resultFontColor);
        label.setOpaque(true);
        label.setFont(new Font(fontStyle,Font.BOLD, 20));
        if(label.getText().equals("SUCCEEDED")) label.setBackground(succeedColor);
        if(label.getText().equals("FAILED")) label.setBackground(failColor);
    }

    public static void styleGameMenuButton(JButton button){
        button.setForeground(subThemeColor);
        button.setPreferredSize(new Dimension(100,30));
        button.setBorderPainted(false);
        button.setBackground(themeColor);
        button.setFont(new Font(fontStyle, Font.BOLD, 22));
    }

    public static void styleMenuBarPanel(JPanel panel){
        panel.setBounds(502,490,700,50);
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 86, 6));
    }
    public static void styleTimeLimitPanel(JPanel panel){
        panel.setOpaque(false);
        panel.setBounds(432, 63, 85, 52);
    }
    public static void styleTimeOutPanel(JPanel panel){
        panel.setBounds(90,160,450,405);
        panel.setPreferredSize(new Dimension(450,400));
        panel.setBackground(themeColor);
    }
    public static void styleTimeOutLabel(JLabel label){
        label.setPreferredSize(new Dimension(450,400));
        label.setFont(new Font(fontStyle, Font.BOLD, 50));
        label.setForeground(subThemeColor);
    }
}
