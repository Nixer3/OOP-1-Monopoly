package sk.stuba.fei.uim.oop;

import java.text.DecimalFormat;

public class PropertyTile extends Tile{
    private int defPrice; //starting price of property
    private int price; //current price of property
    private int trespassing; // price to pay when trespassing
    private Player owner;


    public PropertyTile(String name, int pos, int price) {
        super.setType(TileType.PROPERTY);
        super.setName(name);
        super.setPos(pos);
        this.defPrice = price;
        this.price =  price;
        this.trespassing = 0; //after a buy it gets value
    }


    public String getName() {
        return super.getName();
    }

    @Override
    public int getValue() {return price;}

    @Override
    /*
    * [0] Name
    * [1] price
    * [2] owner
    * [3] players
    * */
    public String[] toStrings(int width) {
        String[] out = new String[4];
        DecimalFormat formatter = new DecimalFormat("#,###");
        String format = "%2d %"+(width-3)+"."+(width-3)+"s";
        out[0]  = String.format(format,this.getPos(), Monopoly.stringCenter(this.getName(), width, ' '));
        format = "%"+(width-2)+"."+(width-2)+"s%2.2s";
        out[1]  = String.format(format, formatter.format(this.getPrice()), Constants.currency);
        out[2]  = String.format("owner: %s", Monopoly.getColoredPlayersPaddings(width-(this.getOwner()==null?5:7), ' ', this.getOwner()));
        out[3]  = Monopoly.getColoredPlayersPaddings(width, ' ', this.getPlayerOnMe().toArray(new Player[0]));

        return out;
    }

    public int getPrice() {
        return price;
    }
    public Player getOwner() {
        return owner;
    }
    public int getTrespassing() {
        return trespassing;
    }

    //Property loses owner
    public void free(){
        this.price = this.defPrice;
        this.owner = null;
        this.trespassing = 0;
    }


    @Override
    public void action(Player player){
        // noone ownes propery
        if(this.owner == null){
            boolean purchase = Monopoly.getYN("Do you want to buy "+this.getName()+"\nfor: "+this.price+Constants.currency+"? (y,n)");
            if(purchase){
                if(player.buyPropery(this)){ // successful
                    this.owner = player;
                    this.trespassing = (int)(price*Constants.trespassingFraction);
                }
                else{
                    Monopoly.displayMessage("Purchase not successful, not enough money\n");
                }
            }
        }
        //technically pointer should be same, but it checks it there anyway
        else if(player.equals(this.owner)) {
            int newPrice = (int)(this.price * Constants.upgradeMultiplication);

            if(player.getMoney() >= this.price * Constants.upgradeMultiplication) {
                if(Monopoly.getYN("Do you want upgrade?\nfor "+newPrice+Constants.currency+"?  ")){//wants to buy
                    player.takeMoney(newPrice);
                    this.price += newPrice;
                    this.trespassing = (int)(price*Constants.trespassingFraction);
                }
            }
        }
        //pay fine
        else {
            Monopoly.displayMessage("You are trespassing. Fine is: "+this.trespassing+Constants.currency+"\n");
            player.takeMoney(trespassing);
        }
    }



}
