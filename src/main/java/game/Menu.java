package game;

import graphics.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    ActionListener startGame;
    Difficulty gameDifficulty;
    public Menu(ActionListener startGame, Difficulty gameDifficulty){
        this.setBackground(new Color(250,247,10));
        this.startGame = startGame;
        this.gameDifficulty = gameDifficulty;
        drawMenuPanel();
        this.setBorder(null);
    }

    private ActionListener selectDifficulty(){
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonName = e.getActionCommand();
                if (buttonName.equals("VERY EASY")) {
                    gameDifficulty.setDifficultyVeryEasy();
                }
                if (buttonName.equals("EASY")) {
                    gameDifficulty.setDifficultyEasy();
                }
                if (buttonName.equals("NORMAL")) {
                    gameDifficulty.setDifficultyNormal();
                }
                if (buttonName.equals("HARD")) {
                    gameDifficulty.setDifficultyHard();
                }
                updateDifficultyLabel();
            }
        };
    }
    private  void drawMenuPanel(){
        Image image=new ImageIcon("src/main/java/image/background4.jpg").getImage();
        JPanel panel = new BackgroundPanel(image);
        panel.setPreferredSize(new Dimension(1200,800));
        panel.setLayout(null);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,0));
        selectionPanel.setBounds(230,460,800,60);
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

        JLabel bufferOffsetLabel = new JLabel(gameDifficulty.getLevel());
        bufferOffsetLabel.setBounds(300,560,180,50);
        bufferOffsetLabel.setOpaque(false);
        bufferOffsetLabel.setForeground(Color.BLACK);
        bufferOffsetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bufferOffsetLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3,false));
        bufferOffsetLabel.setFont(new Font("Consolas", Font.BOLD, 30));
        panel.add(bufferOffsetLabel);

        JLabel difficultyInfoLabel = new JLabel(setInfoText(gameDifficulty.getLevel()));
        difficultyInfoLabel.setBounds(550,540,800,90);
        difficultyInfoLabel.setForeground(Color.BLACK);
        difficultyInfoLabel.setFont(new Font("Consolas", Font.BOLD, 18));
        panel.add(difficultyInfoLabel);

        JButton startButton = new JButton("START GAME");
        startButton.setOpaque(true);
        startButton.setForeground(new Color(250,247,10));
        startButton.setFont(new Font("Consolas", Font.BOLD, 22));
        startButton.setBackground(Color.BLACK);
        startButton.addActionListener(startGame);
        startButton.setBounds(0,650,1200,50);
        panel.add(startButton);
        this.add(panel);
    }

    private void updateDifficultyLabel(){
        removeAll();
        repaint();
        drawMenuPanel();
        revalidate();
    }
    private String setInfoText(String text){
        String info = "";
        if ( text.equals("VERY EASY")) {
            info = "<html> Initial Time Limit : 30 seconds <br> Buffer Size : + 3 <br> Time Reward : 15 seconds <br> Score Reward : 15</html>";
        }
        if ( text.equals("EASY")) {
            info = "<html> Initial Time Limit : 20 seconds <br> Buffer Size : + 2 <br> Time Reward : 10 seconds <br> Score Reward : 25</html>";
        }
        if ( text.equals("NORMAL")) {
            info = "<html> Initial Time Limit : 15 seconds <br> Buffer Size : + 0 <br> Time Reward : 10 seconds <br> Score Reward : 35</html>";
        }
        if ( text.equals("HARD")) {
            info = "<html> Initial Time Limit : 10 seconds <br> Buffer Size : + 0 <br> Time Reward : 5 seconds <br> Score Reward : 50</html>";
        }
        return info;
    }

}

