package entity;

import java.io.Serializable;

public abstract class Cell implements Serializable {
    private String code;

    private boolean selected = false;

    protected Cell(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
