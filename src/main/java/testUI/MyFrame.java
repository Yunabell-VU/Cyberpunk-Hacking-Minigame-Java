package testUI;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MyFrame extends JFrame {

    //constructor
    public MyFrame(String title) {
        super(title);
        String[][] codeSource = {
                {"55", "BD", "55", "1C", "1C", "BD"},
                {"55", "55", "BD", "BD", "BD", "1C"},
                {"7A", "55", "BD", "1C", "1C", "BD"},
                {"1C", "BD", "BD", "1C", "1C", "55"},
                {"55", "7A", "E9", "BD", "55", "55"},
                {"55", "E9", "BD", "BD", "1C", "1C"}
        };

        BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
        borderLayout.setHgap(20);
        borderLayout.setVgap(10);

        //setBounds(100, 100, 290, 282); // 设置窗体的显示位置及大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体关闭按钮的动作为退出
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.TRAILING);
        textField.setPreferredSize(new Dimension(12, 50));
        getContentPane().add(textField, BorderLayout.NORTH);
        textField.setColumns(10);

        final GridLayout gridLayout = new GridLayout(4, 0); // 创建网格布局管理器对象
        gridLayout.setHgap(5); // 设置组件的水平间距
        gridLayout.setVgap(5); // 设置组件的垂直间距
        JPanel panel = new JPanel(); // 获得容器对象
        panel.setLayout(gridLayout); // 设置容器采用网格布局管理器
        getContentPane().add(panel, BorderLayout.WEST);


        JButton[][] buttons = new JButton[6][6];
        for (int row = 0; row < codeSource.length; row++) {
            for (int col = 0; col < codeSource.length; col++) {
                buttons[row][col] = new JButton(codeSource[row][col]); // 创建按钮对象
                buttons[row][col].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        String text = textField.getText();
                        textField.setText(text + button.getText());
                    }
                });
                panel.add(buttons[row][col]); // 将按钮添加到面板中
            }
        }
    }
}
