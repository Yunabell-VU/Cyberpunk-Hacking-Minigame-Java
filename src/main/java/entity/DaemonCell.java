package entity;

import java.io.Serializable;

public class DaemonCell extends Cell implements Serializable {

    private boolean added = false;

    public DaemonCell(String code) {
        super(code);
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }
}
