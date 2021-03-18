package graphics;

import java.awt.*;

public final class ColorFactory {
    private
    ColorFactory(){}

    public static Color
    createColor(String text) {
        switch (text) {
            case "theme":
                return new Color(250, 247, 10);
            case "subTheme":
                return Color.BLACK;
            case "opaqueTheme":
                return new Color(250, 247, 10, 90);
            case "succeed":
                return new Color(27, 213, 117);
            case "fail":
                return new Color(255, 87, 81);
            case "resultFont":
                return new Color(0, 0, 0, 98);
            case "matrixCellSelected":
                return new Color(70, 44, 84);
            case "matrixCellAvailable":
                return new Color(41, 44, 57);
            case "matrixCellMouseEnter":
                return Color.CYAN;
            default:
                return Color.WHITE;
        }
    }
}
