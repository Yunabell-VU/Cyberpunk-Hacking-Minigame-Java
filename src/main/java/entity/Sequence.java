package entity;

import java.util.ArrayList;

//One single sequence set

//DO NOT modify this file!

public class Sequence {
    private boolean succeeded = false;
    private boolean failed = false;

    private ArrayList<SeqCode> seq;

    public Sequence(ArrayList<SeqCode> seq) {
        this.seq = seq;
    }

    public ArrayList<SeqCode> getSeq() {
        return seq;
    }

    public void setSeq(ArrayList<SeqCode> seq) {
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
