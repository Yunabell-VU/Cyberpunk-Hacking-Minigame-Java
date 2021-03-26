package entity;

import java.io.Serializable;
import java.util.List;

//One single Daemon set
//DO NOT modify this file!

public class Daemon implements Serializable {
    private boolean succeeded = false;
    private boolean failed = false; //this attribute is needed because the default state of a Daemon is neither succeeded nor failed
    private boolean checked = false;

    private final List<DaemonCell> daemonCells;

    public
    Daemon(List<DaemonCell> daemonCells) {
        this.daemonCells = daemonCells;
    }

    public List<DaemonCell>
    getDaemonCells() {
        return daemonCells;
    }

    public DaemonCell
    getDaemonCell(int index) {
        return daemonCells.get(index);
    }

    public boolean
    isSucceeded() {
        return succeeded;
    }

    public boolean
    isFailed() {
        return failed;
    }

    public boolean
    isNotChecked() {
        return !checked;
    }

    public void
    setDaemonSucceeded() {
        this.succeeded = true;
    }

    public boolean
    isLastCellMatched() {
        return daemonCells.get(daemonCells.size() - 1).isMatched();
    }

    public void
    setDaemonFailed() {
        this.failed = true;
    }

    public void
    setChecked() {
        this.checked = true;
    }

    //insert empty cells to the front to align the index of waiting cell to the buffer counter
    public void
    addEmptyCell() {
        this.daemonCells.add(0, new DaemonCell(""));
    }

    public void
    setAllDaemonCellsUnSelected() {
        for (DaemonCell daemonCell : daemonCells) {
            daemonCell.setSelected(false);
        }
    }

    public void
    setAllDaemonCellsUnMatched() {
        for (DaemonCell daemonCell : daemonCells) {
            daemonCell.setMatched(false);
        }
    }
}
