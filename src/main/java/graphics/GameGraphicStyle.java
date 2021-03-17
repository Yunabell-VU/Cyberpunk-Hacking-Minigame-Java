package graphics;

import javax.swing.*;
import java.awt.*;

public interface GameGraphicStyle {

    static void styleBufferCell(JLabel label){
        label.setPreferredSize(new Dimension(35,35));
        label.setFont(FontFactory.createFont(18));
        label.setForeground(ColorFactory.createColor("theme"));
        label.setBorder(BorderFactory.createDashedBorder(ColorFactory.createColor("opaqueTheme"), 12, 5));
    }

    static void styleBufferPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBounds(555,70,550,120);
        panel.setOpaque(false);
    }

    static void styleCodeMatrixPanel(JPanel panel, int matrixSpan){

        panel.setBounds(90,192,449,370);
        panel.setOpaque(false);

        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        panel.setLayout(gridLayout);
    }

    static void styleMatrixCellButton(JButton button){
        button.setBackground(ColorFactory.createColor("subTheme"));
        button.setPreferredSize(new Dimension(60, 60));
        button.setFont(FontFactory.createFont(20));

        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setForeground(ColorFactory.createColor("theme"));
    }

    static void styleMatrixCellSelected(JButton button){
        button.setForeground(ColorFactory.createColor("matrixCellSelected"));
    }

    static void styleMatrixCellAvailable(JButton button){
        button.setBackground(ColorFactory.createColor("matrixCellAvailable"));
    }

    static void styleMatrixCellMouseEnter(JButton button){
        button.setForeground(ColorFactory.createColor("matrixCellMouseEnter"));
    }
    static void styleMatrixCellMouseExit(JButton button){
        button.setForeground(ColorFactory.createColor("theme"));
    }

    static void styleCountDownLabel(JLabel label){
        label.setPreferredSize(new Dimension(82,46));
        label.setForeground(ColorFactory.createColor("theme"));
        label.setBorder(BorderFactory.createLineBorder(ColorFactory.createColor("theme")));
        label.setFont(FontFactory.createFont(35));
    }

    static void styleDaemonCellLabel(JLabel label){
        label.setForeground(ColorFactory.createColor("theme"));
        label.setPreferredSize(new Dimension(40,40));
        label.setFont(FontFactory.createFont(18));
    }

    static void styleDaemonCellNotAdded(JLabel label){
        label.setForeground(Color.WHITE);
    }

    static void styleDaemonCellSelected(JLabel label){
        label.setBorder(BorderFactory.createLineBorder(ColorFactory.createColor("theme")));
    }

    static void styleDaemonsPanel(JPanel panel){
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(548, 65));
    }

    static void styleDaemonPanel(JPanel panel){
        panel.setBounds(560,200,550,350);
        panel.setOpaque(false);
    }

    static void styleResultLabel(JLabel label){
        label.setPreferredSize(new Dimension(548,65));
        label.setForeground(ColorFactory.createColor("resultFont"));
        label.setOpaque(true);
        label.setFont(FontFactory.createFont(20));
        if(label.getText().equals("SUCCEEDED")) label.setBackground(ColorFactory.createColor("succeed"));
        if(label.getText().equals("FAILED")) label.setBackground(ColorFactory.createColor("fail"));
    }

    static void styleScorePanel(JPanel panel){
        panel.setBounds(-10,650,1200,200);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 330, 0));
        panel.setOpaque(false);
    }

    static void styleScoreLabel(JLabel label){
        label.setPreferredSize(new Dimension(220,55));
        label.setFont(FontFactory.createFont(25));
    }

    static void styleGameMenuButton(JButton button){
        button.setForeground(ColorFactory.createColor("subTheme"));
        button.setPreferredSize(new Dimension(100,30));
        button.setBorderPainted(false);
        button.setBackground(ColorFactory.createColor("theme"));
        button.setFont(FontFactory.createFont(22));
    }

    static void styleMenuBarPanel(JPanel panel){
        panel.setBounds(502,490,700,50);
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 86, 6));
    }

    static void styleUndoCDPanel(JPanel panel){
        panel.setBounds(650,530,40,40);
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    }
    static void styleUndoCDLabel(JLabel label){
        label.setPreferredSize(new Dimension(40,40));
        label.setForeground(ColorFactory.createColor("theme"));
        label.setFont(FontFactory.createFont(25));
    }

    static void styleTimeLimitPanel(JPanel panel){
        panel.setOpaque(false);
        panel.setBounds(432, 63, 85, 52);
    }

    static void styleTimeOutPanel(JPanel panel){
        panel.setBounds(90,160,450,405);
        panel.setPreferredSize(new Dimension(450,400));
        panel.setBackground(ColorFactory.createColor("theme"));
    }

    static void styleTimeOutLabel(JLabel label){
        label.setPreferredSize(new Dimension(450,400));
        label.setFont(FontFactory.createFont(50));
        label.setForeground(ColorFactory.createColor("subTheme"));
    }
}
