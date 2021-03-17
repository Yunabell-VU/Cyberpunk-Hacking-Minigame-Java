package graphics;

import java.awt.*;

public final class FontFactory {
    private FontFactory() {}

    public static Font createFont( int size) {
        String fontStyle = "Consolas";
        return new Font(fontStyle, Font.BOLD, size);
    }
}
