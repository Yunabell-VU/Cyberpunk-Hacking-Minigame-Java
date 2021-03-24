package entity;

import java.io.Serializable;

public abstract class Cell implements Serializable {
    private String code;

    private boolean selected = false;

    protected
    Cell(String code) {
        this.code = code;
    }

    public String
    getCode() {
        return code;
    }

    public void
    setCode(String code) {
        this.code = code;
    }

    public boolean
    isSelected() {
        return selected;
    }

    /*MatrixCell: setSelected(true) when a cell is picked
    **DaemonCell: setSelected(true) when waiting cell is matched
    **            setSelected(false) for all DaemonCells for next picking
    */
    public void
    setSelected(boolean selected) {
        this.selected = selected;
    }
}
