package game;

import graphics.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static graphics.MenuGraphicStyle.*;

class Menu extends JPanel {

    private final transient ActionListener startGame;
    private final Difficulty gameDifficulty;

    public Menu(ActionListener startGame, Difficulty gameDifficulty) {

        this.setBackground(new Color(250, 247, 10));
        this.setBorder(null);

        this.startGame = startGame;
        this.gameDifficulty = gameDifficulty;

        drawMenuPanel();
    }

    private void drawMenuPanel() {
        JPanel panel = new Background("MENU");

        panel.add(drawSelectionButtons());
        JLabel difficultyLabel = new JLabel(gameDifficulty.getLevel(),SwingConstants.CENTER);
        styleDifficultyLabel(difficultyLabel);
        panel.add(difficultyLabel);

        JLabel levelInfoLabel = new JLabel(gameDifficulty.getDifficultyInfo(),SwingConstants.LEFT);
        styleLevelInfoLabel(levelInfoLabel);
        panel.add(levelInfoLabel);

        JButton startButton = new JButton("START GAME");
        styleStartButton(startButton);
        startButton.addActionListener(startGame);
        panel.add(startButton);

        this.add(panel);
    }

    private JPanel drawSelectionButtons() {
        JPanel selectionPanel = new JPanel();
        styleMenuSelectionPanel(selectionPanel);

        JButton buttonVeryEasy = new JButton("VERY EASY");
        styleDifficultyButton(buttonVeryEasy);
        buttonVeryEasy.addActionListener(selectDifficulty());

        JButton buttonEasy = new JButton("EASY");
        styleDifficultyButton(buttonEasy);
        buttonEasy.addActionListener(selectDifficulty());

        JButton buttonNormal = new JButton("NORMAL");
        styleDifficultyButton(buttonNormal);
        buttonNormal.addActionListener(selectDifficulty());

        JButton buttonHard = new JButton("HARD");
        styleDifficultyButton(buttonHard);
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
            gameDifficulty.setDifficulty(buttonName);
            updatePanel();
        };
    }
}
