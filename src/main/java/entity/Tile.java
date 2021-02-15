package entity;

//One single Tile/Code
//DO NOT modify this file!

public class Tile extends Code{
    private boolean available = false;
    public Tile(String code){super(code);}

    public boolean isAvailable(){return available;}
    public void setAvailable(boolean available){this.available=available;}
}
