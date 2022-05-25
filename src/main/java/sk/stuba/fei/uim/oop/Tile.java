package sk.stuba.fei.uim.oop;

import java.util.ArrayList;

public abstract class Tile {
    private String name;
    private int pos;
    private TileType type;
    private ArrayList<Player> playersOnMe= new ArrayList<>();

    public abstract void action(Player player);
    public abstract int getValue();
    public abstract String[] toStrings(int width);


    public void cameToTile(Player p){
        playersOnMe.add(p);
    }
    public void leavingTile(Player p){
        playersOnMe.remove(p);
    }

    public  TileType getType(){
        return type;
    }

    public int getPos() {
        return pos;
    }
    public String getName(){
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    //new features
    public void setType(TileType type) {
        this.type = type;
    }


    public ArrayList<Player> getPlayerOnMe(){return playersOnMe;}
}
