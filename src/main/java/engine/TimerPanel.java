package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerPanel extends JPanel{
    private static final int TIMER_PERIOD = 1000;
    public int currentCount = 15;
    private int count;
    JLabel countDownLabel = new JLabel("", SwingConstants.CENTER);

    public TimerPanel() {
        JPanel timePanel = new JPanel();
        timePanel.setBackground(Color.BLACK);
        add(timePanel);

        timePanel.add(countDownLabel);

        String text = (currentCount -count)+"";
        setCountDownLabelText(text);
        countDownLabel.setForeground(Color.YELLOW);
        countDownLabel.setFont(new Font(Font.DIALOG,Font.BOLD,14));

    }

    public void setCountDownLabelText(String text) {
        countDownLabel.setText(text);
    }

    public void start() {
        new Timer(TIMER_PERIOD, e -> {
            if (count < currentCount) {
                count++;
                String text = (currentCount -count)+"";
                setCountDownLabelText(text);
            } else {
                ((Timer) e.getSource()).stop();
            }
        }).start();
    }
}
