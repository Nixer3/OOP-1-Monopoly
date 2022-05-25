package sk.stuba.fei.uim.oop;

import java.util.ArrayList;

public class ActionTile extends Tile{

    public ActionTile() {
        super.setType(TileType.CHANCE);
    }

    public ActionTile(TileType type) {
        super.setName(type.toString());
        super.setType(type);
    }
    public ActionTile(TileType type,int pos) {
        super.setName(type.toString());
        super.setType(type);
        super.setPos(pos);
    }

    public void setType(TileType type) {
        super.setType(type);
    }

    private void payTaxes(Player player){
        Monopoly.displayMessage("You payed "+Constants.taxes+ Constants.currency+" due to taxes.\n");
        player.takeMoney(Constants.taxes);
    }

    @Override
    public void action(Player player) {
        switch (this.getType()){
            case START:   player.addMoney(Constants.annualMoney);break;
            case NOTHING: break;
            case POLICE:  player.sendToJail(); break;
            case TAX:     payTaxes(player); break;
            //case CHANCE: must be implemented in Monopoly, since I don't know which card should I use
        }
    }

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public String[] toStrings(int width) {
        String[] out = new String[4];
        out[0] = Monopoly.stringCenter(getName(), width, ' ');

        if(this.getPos()==Constants.jailPos){//JAIL
            ArrayList<Player> imprisoned = new ArrayList<>();
            ArrayList<Player> freeJail = new ArrayList<>();
            for (var p : this.getPlayerOnMe()) {
                    if (p.isJailed() > 0) {     //doesnt have to be jailed
                        imprisoned.add(p);
                    } else { //is just passing
                        freeJail.add(p);
                    }
            }
            out[1]  = Monopoly.getColoredPlayersPaddings(width, ' ', imprisoned.toArray(new Player[0]));
            out[2]  = "-".repeat(width);
            out[3] = Monopoly.getColoredPlayersPaddings(width, ' ', freeJail.toArray(new Player[0]));
        }
        else {
            out[1] = " ".repeat(width);
            out[2] = " ".repeat(width);
            out[3] = Monopoly.getColoredPlayersPaddings(width, ' ', this.getPlayerOnMe().toArray(new Player[0]));
        }
        return out;
    }


}

