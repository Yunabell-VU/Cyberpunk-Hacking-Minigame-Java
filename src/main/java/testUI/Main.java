package testUI;

import static testUI.SwingDemo.createGUI;

public class Main {
    public static void main (String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                createGUI();
            }
        });
    }
}
