package graphics;

import javax.swing.*;
import java.awt.*;

public class CodeMatrixPanel extends JPanel {

    public CodeMatrixPanel(int matrixSpan){

        this.setBounds(90,192,449,370);
        this.setOpaque(false);

        final GridLayout gridLayout = new GridLayout(matrixSpan, matrixSpan);
        this.setLayout(gridLayout);
    }
}
