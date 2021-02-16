package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static game.Settings.BUFFER_OFFSET;

public class MenuPanel extends JPanel {
    ActionListener listener;
    public MenuPanel(ActionListener listener){
        this.setBackground(new Color(250,247,10));
        this.listener = listener;

        drawMenuPanel();
        this.setBorder(null);
    }

    private ActionListener selectDifficulty(){
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonName = e.getActionCommand();
                if(buttonName.equals("VERY EASY")){
                    BUFFER_OFFSET = 3;
                }
                if(buttonName.equals("EASY")){
                    BUFFER_OFFSET = 2;
                }
                if(buttonName.equals("NORMAL")){
                    BUFFER_OFFSET = 1;
                }
                if(buttonName.equals("HARD")){
                    BUFFER_OFFSET = 0;
                }
                removeAll();
                repaint();
                drawMenuPanel();
                revalidate();
            }
        };
        return  listener;
    }
    private  void drawMenuPanel(){
        Image image=new ImageIcon("src/main/java/image/background2.jpg").getImage();
        JPanel panel = new BackgroundPanel(image);
        panel.setPreferredSize(new Dimension(1200,800));
        panel.setLayout(null);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
        selectionPanel.setBounds(230,480,800,60);
        selectionPanel.setBackground(new Color(250,247,10));
        selectionPanel.setBorder(null);

        JButton b1 = new JButton("VERY EASY");
        b1.setPreferredSize(new Dimension(150,50));
        b1.setBorderPainted(false);
        b1.setBackground(Color.BLACK);
        b1.setForeground(new Color(250,247,10));
        b1.setFont(new Font("Consolas", Font.BOLD, 20));
        b1.addActionListener(selectDifficulty());

        JButton b2 = new JButton("EASY");
        b2.setPreferredSize(new Dimension(150,50));
        b2.setBorderPainted(false);
        b2.setBackground(Color.BLACK);
        b2.setForeground(new Color(250,247,10));
        b2.setFont(new Font("Consolas", Font.BOLD, 20));
        b2.addActionListener(selectDifficulty());

        JButton b3 = new JButton("NORMAL");
        b3.setPreferredSize(new Dimension(150,50));
        b3.setBorderPainted(false);
        b3.setBackground(Color.BLACK);
        b3.setForeground(new Color(250,247,10));
        b3.setFont(new Font("Consolas", Font.BOLD, 20));
        b3.addActionListener(selectDifficulty());

        JButton b4 = new JButton("HARD");
        b4.setPreferredSize(new Dimension(150,50));
        b4.setBorderPainted(false);
        b4.setBackground(Color.BLACK);
        b4.setForeground(new Color(250,247,10));
        b4.setFont(new Font("Consolas", Font.BOLD, 20));
        b4.addActionListener(selectDifficulty());

        selectionPanel.add(b1);
        selectionPanel.add(b2);
        selectionPanel.add(b3);
        selectionPanel.add(b4);

        panel.add(selectionPanel);

        JLabel bufferOffsetLabel = new JLabel(BUFFER_OFFSET+"");
        bufferOffsetLabel.setBounds(430,560,60,60);
        bufferOffsetLabel.setOpaque(false);
        bufferOffsetLabel.setForeground(Color.BLACK);
        bufferOffsetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bufferOffsetLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3,false));
        bufferOffsetLabel.setFont(new Font("Consolas", Font.BOLD, 30));
        panel.add(bufferOffsetLabel);

        JButton startButton = new JButton("START GAME");
        startButton.setOpaque(true);
        startButton.setForeground(new Color(250,247,10));
        startButton.setFont(new Font("Consolas", Font.BOLD, 22));
        startButton.setBackground(Color.BLACK);
        startButton.addActionListener(listener);
        startButton.setBounds(0,650,1200,50);
        panel.add(startButton);
        this.add(panel);
    }
}

