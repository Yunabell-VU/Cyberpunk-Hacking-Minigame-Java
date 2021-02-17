package entity;

import java.util.List;

//One single sequence set

//DO NOT modify this file!

public class Daemon {
    private boolean succeeded = false;
    private boolean failed = false;

    private List<SeqCell> seq;

    public Daemon(List<SeqCell> seq) {
        this.seq = seq;
    }

    public List<SeqCell> getSeq() {
        return seq;
    }

    public void setSeq(List<SeqCell> seq) {
        this.seq = seq;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

}
