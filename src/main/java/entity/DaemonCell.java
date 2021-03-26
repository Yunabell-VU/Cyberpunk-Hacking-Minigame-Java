package entity;

import java.io.Serializable;

public class DaemonCell extends Cell implements Serializable {

    private boolean matched = false;

    public
    DaemonCell(String code) {
        super(code);
    }

    public boolean
    isMatched() {
        return matched;
    }

    //set matched to true if the DaemonCell has the same code as the code in the buffer
    public void
    setMatched(boolean matched) {
        this.matched = matched;
    }

    // is the code of the DaemonCell the same as the ... ?
    public boolean
    isMatch(String pickedCode) {
        return this.getCode().equals(pickedCode);
    }
}
