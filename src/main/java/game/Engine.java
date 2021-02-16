package game;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

//Game process control: initiate game frame, init status, init game logic
//DO NOT modify this file!

public class Engine {

    public static void runGame() {
        GameFrame frame = new GameFrame("CHM - Endless Challenge");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(Engine::runGame);
    }
}
