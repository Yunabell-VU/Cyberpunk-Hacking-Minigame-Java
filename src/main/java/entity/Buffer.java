package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Buffer implements Serializable {
    private final int bufferSize;
    private final List<String> bufferContent = new ArrayList<>();
    private int bufferCounter; //This counter indicates the tail of the "real" buffer content, not the "space" ones.

    public
    Buffer(int size) {
        bufferSize = size;

        for (int i = 0; i < bufferSize; i++)
            bufferContent.add(" "); //empty buffer content is filled with space because of the characteristic of the Swing JLabel.

        bufferCounter = 0;
    }

    public int
    getBufferCounter() {
        return bufferCounter;
    }

    public int
    getBufferSize() {
        return bufferSize;
    }

    public boolean
    isBufferFull() {
        return bufferCounter >= bufferSize;
    }

    public String
    getBufferCode(int index) {
        return bufferContent.get(index);
    }

    public String
    getLastCodeInBuffer() {
        return bufferContent.get(bufferCounter - 1);
    }

    public void
    addCellToBuffer(String code) {
        bufferContent.set(bufferCounter, code);
        bufferCounter++;
    }
}
