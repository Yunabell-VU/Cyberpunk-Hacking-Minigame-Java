package entity;

public class DaemonCell extends Cell {

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
