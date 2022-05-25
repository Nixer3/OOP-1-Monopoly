package sk.stuba.fei.uim.oop;

import java.util.ArrayList;

public class Player {
    private String name;
    private Figure figure;
    private boolean alive;

    private int position;
    private int money;
    private ArrayList<PropertyTile> properties;

    private int isJailed;
    private boolean hasJailPass;


    public Player(String name, Figure figure, int money, int position) {
        this.name = name;
        this.figure = figure;
        this.money = money;
        this.position = position;
        this.alive = true;
        this.properties = new ArrayList<>();
    }


    public void addMoney(int amount){
        this.money += amount;
    }
    //bankrupt not checked
    public void takeMoney(int amount){
        this.money -= amount;
    }

    //you should use .move() instead
    private void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public Figure getFigure() {
        return figure;
    }

    public int getPosition() {
        return position;
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<PropertyTile> getProperties() {
        return properties;
    }

    public boolean buyPropery(PropertyTile property){
        if(this.money >= property.getPrice() ){//able to buy
            this.takeMoney(property.getPrice());
            this.properties.add(property);
            return true;                    //purchase successful
        }
        return false; // not enough fund
    }

    public boolean isAlive() {
        if(this.money<0) alive = false;
        return alive;
    }

    public void sendOutOfBoard(){
        this.position = -1;
    }
    public void sendToJail() {
        if(hasJailPass) {
            Monopoly.displayMessage("You escaped jail time. Hurayy!\n");
            this.hasJailPass = false;
        }
        else {
            Monopoly.displayMessage("You've been arrested, cool down for "+Constants.jailTime+" turns\n");
            this.isJailed = Constants.jailTime;
            this.position = Constants.jailPos;
        }
    }
    public void visitJail(){
        this.position = Constants.jailPos;
    }

    public boolean hasJailPass() {
        return hasJailPass;
    }

    public void setJailPass(boolean hasJailPass) {
        this.hasJailPass = hasJailPass;
    }

    //all movement should be handled by this or .enterJail()
    //returns dest. position
    public int move(int tiles) {
        if(this.position+tiles > Constants.boardSize-1){
            this.addMoney(Constants.annualMoney);
            Monopoly.displayMessage("You passed start.\nReceived "+Constants.annualMoney+Constants.currency+"\n");
        }
        return justMove(tiles);
    }
    private int justMove(int tiles){
        this.position= (this.position+tiles) % (Constants.boardSize-1);//move
        return this.position;
    }

    //reduces isJail,
    public int reduceJail() {
        if(--this.isJailed < 0) this.isJailed = 0;
        return this.isJailed;
    }
    public int isJailed() {
        return this.isJailed;
    }
}
