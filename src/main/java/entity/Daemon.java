package entity;

import java.io.Serializable;
import java.util.List;

//One single sequence set

//DO NOT modify this file!

public class Daemon implements Serializable {
    private boolean succeeded = false;
    private boolean failed = false;
    private boolean rewarded = false;

    private final List<DaemonCell> daemonCells;

    public Daemon(List<DaemonCell> daemonCells) {
        this.daemonCells = daemonCells;
    }

    public List<DaemonCell> getDaemonCells() {
        return daemonCells;
    }

    public DaemonCell getDaemonCell(int index) {
        return daemonCells.get(index);
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public boolean isFailed() {
        return failed;
    }

    public boolean isNotRewarded() {
        return !rewarded;
    }

    public void setDaemonSucceeded() {
        this.succeeded = true;
    }

    public boolean isLastCellAdded() {
        return daemonCells.get(daemonCells.size() - 1).isMatched();
    }

    public void setDaemonFailed() {
        this.failed = true;
    }

    public void setRewarded() {
        this.rewarded = true;
    }

    public void addEmptyCell() {
        this.daemonCells.add(0, new DaemonCell(""));
    }

    public void setAllDaemonCellsUnSelected() {
        for (DaemonCell daemonCell : daemonCells) {
            daemonCell.setSelected(false);
        }
    }

    public void setAllDaemonCellsUnAdded() {
        for (DaemonCell daemonCell : daemonCells) {
            daemonCell.setMatched(false);
        }
    }
}
