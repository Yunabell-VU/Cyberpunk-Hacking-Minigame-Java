package entity;

public class SeqCell extends Cell {

    private boolean added = false;

    public SeqCell(String code){super(code);}

    public boolean isAdded() {return added;}
    public void setAdded(boolean added) {this.added = added;}
}
