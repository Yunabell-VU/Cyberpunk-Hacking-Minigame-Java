package entity;

import java.util.List;

//One single sequence set

//DO NOT modify this file!

public class Daemon {
    private boolean succeeded = false;
    private boolean failed = false;
    private boolean rewarded = false;

    private List<DaemonCell> seq;

    public Daemon(List<DaemonCell> seq) {
        this.seq = seq;
    }

    public List<DaemonCell> getSeq() {
        return seq;
    }

    public void setSeq(List<DaemonCell> seq) {
        this.seq = seq;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public boolean isFailed() {
        return failed;
    }

    public  boolean isRewarded(){
        return rewarded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public void setRewarded(boolean rewarded){
        this.rewarded = rewarded;
    }

    public void addEmptyCell() {
        this.seq.add(0, new DaemonCell(""));
    }

}
