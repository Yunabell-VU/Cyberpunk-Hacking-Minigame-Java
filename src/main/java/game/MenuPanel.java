package game;

import graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    ActionListener startGame;
    Difficulty gameDifficulty;

    public MenuPanel(ActionListener startGame, Difficulty gameDifficulty) {

        this.setBackground(new Color(250, 247, 10));
        this.setBorder(null);

        this.startGame = startGame;
        this.gameDifficulty = gameDifficulty;

        drawMenuPanel();
    }

    private void drawMenuPanel() {
        Image image = new ImageIcon("src/main/java/image/background4.jpg").getImage();
        JPanel panel = new Background(image);

        panel.add(drawSelection());
        panel.add(new DifficultyLabel(gameDifficulty.getLevel()));
        panel.add(new LevelInfoLabel(gameDifficulty.getDifficultyInfo()));

        JButton startButton = new StartButton();
        startButton.addActionListener(startGame);
        panel.add(startButton);

        this.add(panel);
    }

    private JPanel drawSelection(){
        JPanel selectionPanel = new MenuSelection();

        JButton buttonVeryEasy = new DifficultyButton("VERY EASY");
        buttonVeryEasy.addActionListener(selectDifficulty());

        JButton buttonEasy = new DifficultyButton("EASY");
        buttonEasy.addActionListener(selectDifficulty());

        JButton buttonNormal = new DifficultyButton("NORMAL");
        buttonNormal.addActionListener(selectDifficulty());

        JButton buttonHard = new DifficultyButton("HARD");
        buttonHard.addActionListener(selectDifficulty());

        selectionPanel.add(buttonVeryEasy);
        selectionPanel.add(buttonEasy);
        selectionPanel.add(buttonNormal);
        selectionPanel.add(buttonHard);

        return selectionPanel;
    }

    private void updatePanel() {
        removeAll();
        repaint();
        drawMenuPanel();
        revalidate();
    }

    private ActionListener selectDifficulty() {
        return e -> {
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
            updatePanel();
        };
    }
}

