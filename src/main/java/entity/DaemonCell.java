package entity;

import java.io.Serializable;

public class DaemonCell extends Cell implements Serializable {

    private boolean matched = false;

    public DaemonCell(String code) {
        super(code);
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public boolean isMatch(String pickedCode) {
        return this.getCode().equals(pickedCode);
    }
}
