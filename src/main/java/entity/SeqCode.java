package entity;

public class SeqCode extends Code{

    private boolean added = false;

    public SeqCode(String code){super(code);}

    public boolean isAdded() {return added;}
    public void setAdded(boolean added) {this.added = added;}
}
