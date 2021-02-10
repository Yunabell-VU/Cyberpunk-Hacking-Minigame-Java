package testUI;

import javax.swing.*;
import java.awt.*;

public class SwingDemo {
    public static void createGUI()
    {   //Jframe创建窗口
        MyFrame frame = new MyFrame("Cyberpunk-hacking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小
        frame.setSize(800, 600);
        //显示窗口
        frame.setVisible(true);
    }
}
