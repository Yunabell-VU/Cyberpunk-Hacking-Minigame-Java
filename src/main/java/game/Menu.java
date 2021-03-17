package game;

import graphics.*;

import javax.swing.*;
import java.awt.event.ActionListener;

import static graphics.MenuGraphicStyle.*;

class Menu extends JPanel implements Redraw, MenuGraphicStyle {

    public final transient ActionListener startGame;
    private final Difficulty gameDifficulty;

    public Menu(ActionListener startGame, Difficulty gameDifficulty) {

        this.setBackground(ColorFactory.createColor("theme"));
        this.setBorder(null);

        this.startGame = startGame;
        this.gameDifficulty = gameDifficulty;

        this.add(drawMenuPanel());
    }

    private JPanel drawMenuPanel() {
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

        return panel;
    }

    private JPanel drawSelectionButtons() {
        JPanel selectionPanel = new JPanel();
        styleMenuSelectionPanel(selectionPanel);

        selectionPanel.add(createSelectionButton("VERY EASY"));
        selectionPanel.add(createSelectionButton("EASY"));
        selectionPanel.add(createSelectionButton("NORMAL"));
        selectionPanel.add(createSelectionButton("HARD"));

        return selectionPanel;
    }

    private JButton createSelectionButton(String text){
        JButton button = new JButton(text);
        styleDifficultyButton(button);
        button.addActionListener(selectDifficulty());
        return button;
    }

    private void updatePanel() {
        Redraw.redraw(this,drawMenuPanel());
    }

    private ActionListener selectDifficulty() {
        return e -> {
            String buttonName = e.getActionCommand();
            gameDifficulty.setDifficulty(buttonName);
            updatePanel();
        };
    }
}
