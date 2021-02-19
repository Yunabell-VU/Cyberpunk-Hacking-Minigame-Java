package entity;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private final int bufferSize;
    private final List<String> bufferContent = new ArrayList<>();
    private int bufferCounter;

    public Buffer(int size){
        bufferSize = size;

        for (int i = 0; i < bufferSize; i++)
            bufferContent.add(" ");

        bufferCounter = 0;
    }

    public int getBufferCounter() {
        return bufferCounter;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public boolean isBufferFull(){
        return bufferCounter >= bufferSize;
    }

    public String getBufferCode(int index){
        return bufferContent.get(index);
    }

    public void addCellToBuffer(String code){
        bufferContent.set(bufferCounter, code);
        bufferCounter++;
    }
}
