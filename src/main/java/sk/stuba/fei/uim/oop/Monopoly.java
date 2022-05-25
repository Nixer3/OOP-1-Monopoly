package sk.stuba.fei.uim.oop;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Monopoly {

    ArrayList<Tile> board = new ArrayList<>();
    ArrayList<PropertyTile> properties = new ArrayList<>();
    List<Integer> cardPack;
    List<Player> players = new ArrayList<>();
    ArrayList<Figure> usedFigures = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    Random rand = new Random(); //dice and cards
    Random notRandom = new Random(1); // price distribution

    /**
     * St= start, C= chance, Ja = jail, 0 = nothing, p = property
     * p is random permutation
     * 0 1 2 3 4 5 6
     * St p p C p p Ja
     * 23 p           p 7
     * 22 p           p 8
     * 21 C           C 9
     * 20 p           p 10
     * 19 p           p 11
     * GJ p p C p p 0 12
     * 10+8 7 6 5 4 3
     * indexing from St clockwise 0-23
     */

    public void start() {
        usedFigures.add(new Figure("", '\0'));// adding "NULL" figure
        cardPack_init();
        tiles_init();
        displayMessage("Welcome to the game of monopoly!\n");
        displayMessage("'*' means waiting for input, just press Enter\n");
        players_init();

        int generations = 0;//no usage really just at the end of game
        int cardPackIndex = 0; // only increment, use always with %cardPack.size()
        while (alivePlayers() > 1) { //lifecycle
            for (Player p : players) { //cycle player
                if (p.isAlive()) {    // I mean alive ones
                    int dice = 0;//not initialized if in jail warning/error
                    //clearScreen();
                    displayStats();
                    printBoard();
                    Monopoly.displayMessage(p.getFigure().getColorCode());
                    Monopoly.displayMessage(p.getName() + "'s turn\n");

                    do { //dicing  dice==6 and p.isAlive
                        if (p.reduceJail() > 0) {
                            Monopoly.displayMessage("Send  another day in jail. "+p.isJailed()+" left\n");
                            continue;
                        }//if jailed continue to another player
                        dice = rand.nextInt(6) + 1; //dice once
                        board.get(p.getPosition()).leavingTile(p);
                        Monopoly.displayMessage("You tossed " + dice + " and moved to " + p.move(dice) + " | " + board.get(p.getPosition()).getName() + "\n");

                        if (board.get(p.getPosition()).getType() == TileType.CHANCE) {// if on chance   must be handled here, because cardPack can't be static.
                            Monopoly.displayMessage("You pulled the card.:\n");
                            Cards.cardAction(cardPack.get((cardPackIndex++) % cardPack.size()), p);  //pull a card%size,
                        } else
                            board.get(p.getPosition()).action(p); // title on board interact with player
                        board.get(p.getPosition()).cameToTile(p);

                        Monopoly.displayMessage("*");
                        try {
                            System.in.read();//waiting for a key
                        } catch (IOException e) {
                        }
                    } while (dice == Constants.diceAgainNumber && p.isAlive() && p.isJailed()==0);//if diced six, go again
                    //not diced 6 or dead
                    if (!p.isAlive())
                        this.bankruptPlayer(p);
                    Monopoly.displayMessage(ConsoleColors.RESET);
                }//end player's turn

            }//new generations of turns
            generations++;
        }//end game

        Player lastStandingPlayer = null;
        for (Player p : players) {
            if (p.isAlive())
                lastStandingPlayer = p;
        }
        if (lastStandingPlayer != null) {
            Monopoly.displayMessage("WINNER IS " + lastStandingPlayer.getName() + "\n Congratulations\n");
            Monopoly.displayMessage("You won in  " + generations + " turns.\n");
        }

    }

    void displayStats() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        StringBuilder header = new StringBuilder();
        StringBuilder position = new StringBuilder();
        StringBuilder uniques = new StringBuilder();

        header.append("||");
        position.append("||");
        uniques.append("||");
        for (var p : players) {//let width of column is 30, including ||
            if (!p.isAlive()) {
                header.append(String.format("%-20.20s LOST %s|| ", p.getName(), ConsoleColors.RESET));
                position.append(String.format("%2d = gone  %s||", p.getPosition(), ConsoleColors.RESET));
                uniques.append(String.format("%-28.28s%s||", " ", ConsoleColors.RESET));
                continue;
            }

            header.append(p.getFigure().getColorCode());
            position.append(p.getFigure().getColorCode());
            uniques.append(p.getFigure().getColorCode());
            header.append(String.format("%-12.12s = %10s %.2s %s||", p.getName(), formatter.format(p.getMoney()), Constants.currency, ConsoleColors.RESET));

            if (p.isJailed() == 0)
                position.append(String.format("%2d| %-24.24s%s||", p.getPosition(), board.get(p.getPosition()).getName(), ConsoleColors.RESET));
            else
                position.append(String.format("%2d| %-24.24s%s||", p.getPosition(), "Jail for " + p.isJailed() + " turns", ConsoleColors.RESET));
            uniques.append(String.format("%-28s%s||", p.hasJailPass() ? "JAIL_PASS" : "", ConsoleColors.RESET));
        }
        Monopoly.displayMessage(header + "\n" + position + "\n" + uniques + "\n");

        int biggestSize = 0;
        for (var p : players)
            biggestSize = Math.max(p.getProperties().size(), biggestSize);

        StringBuilder props = new StringBuilder();

        for (int i = 0; i < biggestSize; i++) {
            props.append("||");
            for (var p : players) {
                if (i < p.getProperties().size())
                    props.append(String.format("%2d|%-14.14s%9.9s%2.2s||",
                            p.getProperties().get(i).getPos(),
                            p.getProperties().get(i).getName(),
                            formatter.format(p.getProperties().get(i).getPrice()),
                            Constants.currency));
                else
                    props.append(String.format("%28.28s||", ""));
            }
            props.append("\n");
        }
        props.append("||");
        props.append("-".repeat(29 * players.size()));
        props.append("||");
        Monopoly.displayMessage(props + "\n");
    }

    //work in progress... resume
    void printBoard() {

        int width=14;
        Monopoly.displayMessage("-".repeat((width+1)*7)+"\n");
        StringBuilder[] lines = new StringBuilder[4];
        for (int i = 0; i < 4; i++)
            lines[i] = new StringBuilder();

        for (int i = 0; i <= 6; i++) {
            String[] tiles;
            tiles = board.get(i).toStrings(width);
            for (int j = 0; j < 4; j++)
                lines[j].append(tiles[j]).append("|");
        }
        for (var l:lines)
            Monopoly.displayMessage(l+"\n");

        String verticalTileBridge = "-".repeat(width)+"|"+" ".repeat(((width+1) *5)-1)+"|"+"-".repeat(width)+"|";
        String horizontalBridge = "-".repeat(width)+"|"+"-".repeat(((width+1) *5)-1)+"|"+"-".repeat(width)+"|";

        Monopoly.displayMessage(horizontalBridge+"\n");

        /* |---width----|
         * ---------------------------------------------------------------------------------------------------------
         *     START     | 1  Mediter av| 2   Baltic av|    CHANCE    | 4 Reading Rai| 5  Oriental a|     JAIL     |
         *               |     148 000 M|     164 000 M|              |      41 000 M|     170 000 M|m             |        //phase 1) first row
         *               |owner:        |owner:        |              |owner:        |owner:        |--------------|
         * k             |              |              |m             |              |              |k             |
         * ---------------------------------------horizontalBridge--------------------------------------------------
         * 23  Mediter av|                                                                          | 7  Mediter av|
         *      148 000 M|                                                                          |     148 000 M|
         * owner:        |                    --- ((width+1) *5)-1+'|'   ---                        |owner:        |        //phase 2) columns
         *               |                                                                          |              |
         * --------------|                   ----- verticalTileBridge ------                        |--------------|
         * 22  Mediter av|                                                                          | 8  Mediter av|
         *      148 000 M|                                                                          |     148 000 M|
         * owner:        |                                                                          |owner:        |        //phase 3) last row
         *               |                                                                          |              |
         * --------------|------------------------horizontalBridge----------------------------------|--------------|
         * */

        int inc =7;
        int dec = 23;

        for (int i = 0; i < 5; i++) {
            lines = new StringBuilder[4];
            for (int j = 0; j < 4; j++)         //new 4 rows a.k.a. one tile
                lines[j] = new StringBuilder();

            String[] tile;
            tile = board.get(dec--).toStrings(width);  //appending left tile to builder
            for (int j = 0; j < 4; j++)
                lines[j].append(tile[j]).append("|");


            for (int j = 0; j < 4; j++){     //appending middle grround to builder
                lines[j].append(" ".repeat(((width+1) *5)-1)).append("|");}


            tile = board.get(inc++).toStrings(width);  //appending right tile to builder
            for (int j = 0; j < 4; j++)
                lines[j].append(tile[j]).append("|");

            for (var l:lines)
                Monopoly.displayMessage(l+"\n");
            if(i!=4)
                Monopoly.displayMessage(verticalTileBridge+"\n");
            else Monopoly.displayMessage(horizontalBridge+"\n");
        }


        lines = new StringBuilder[4];
        for (int i = 0; i < 4; i++)
            lines[i] = new StringBuilder();

        for (; dec>11; dec--) {
            String[] tiles;
            tiles = board.get(dec).toStrings(width);
            for (int j = 0; j < 4; j++)
                lines[j].append(tiles[j]).append("|");
        }
        for (var l:lines)
            Monopoly.displayMessage(l+"\n");
        Monopoly.displayMessage("-".repeat((width+1)*7)+"\n");


    }




    private void printProperties(ArrayList<PropertyTile> properties) {
        for (var prop : properties) {
            Monopoly.displayMessage(String.format("%20s %7d%s\n",prop.getName(),prop.getPrice(), Constants.currency));
        }
    }


    private void bankruptPlayer(Player p) {
        int worth = 0;
        for (PropertyTile prop : p.getProperties()){
            worth += prop.getPrice();
            prop.free();
        }
        board.get(p.getPosition()).leavingTile(p);
        p.sendOutOfBoard();

        Monopoly.displayMessage("Good bye, you were worth\n"+worth+Constants.currency+" :( \n");

    }

    private int alivePlayers() {
        int alive=0;
        for (var p: players) {//count alive players
            alive += p.isAlive()?1:0;
        }
        return alive;
    }



    private void players_init(){
        Monopoly.displayMessage("How many players?\n");
        int playerCount=0;
        while(true) {
            try {
                Monopoly.displayMessage("Please, enter integer 2+ : ");
                playerCount = input.nextInt();          // asking for how many players,throws InputMismatchE.
                if(playerCount<2){
                    throw new Exception("small number");
                }
                break;
            } catch(InputMismatchException e){
                Monopoly.displayMessage("Please enter a number\n");
            } catch (Exception e){
                Monopoly.displayMessage(e.getMessage()+"\n");
            }
        }
        input.nextLine();

        for (int i = 1; i <=playerCount; i++) {
            System.out.printf("Adding player '%d'>",i);
            Monopoly.displayMessage("Name: ");
            String name = input.nextLine();


            boolean randomFigures;
            Figure fig = new Figure("",'\0');

            while(fig.getShape()==0){ //while invalid figure
                randomFigures = getYN("Random figure? (y/n): ");
                if(randomFigures)fig=generateFigure(name.charAt(0)); // generate or chose
                else fig = newFigureDialog();

                if(isFigIn(fig,usedFigures)) {
                    fig.setShape('\0'); // invalidate figure,
                    Monopoly.displayMessage("Figure already in use\n");
                }
            }
            usedFigures.add(fig);

            Player p = new Player(name,fig,Constants.startingMoney,0);
            board.get(0).cameToTile(p);
            players.add(p);
        }
    }

    private boolean isFigIn(Figure figure, ArrayList<Figure> usedFigures) {
        for (var fig : usedFigures){
            if(figure.equals(fig))
                return true; // found
        }
        return false; // not found = unique
    }

    private Figure newFigureDialog(){
        Monopoly.displayMessage("Color (black,red,green,blue,yellow,cyan,purple): ");
        String color = input.nextLine().toUpperCase();
        Monopoly.displayMessage("Shape (char): ");
        char shape = input.nextLine().charAt(0);
        if(!ConsoleColors.colorsMap.containsKey(color)) {
            Monopoly.displayMessage("Invalid color\n");
            return new Figure("",'\0');
        }
        return new Figure(color, shape);
    }
    private Figure generateFigure() {
        List<String> keys = new ArrayList<>(ConsoleColors.colorsMap.keySet());//color names
        keys.removeIf(s -> s.contains("hi"));//removes hi contrast colors

        if (players.size() < keys.size()){ // if there are more players than color, colors have to be used twice
            for (var fig : usedFigures) {
                keys.remove(fig.getColorName());
            }
        }
        Figure outFig;
        do { // generate until unique
            outFig = new Figure(keys.get(0), (char) ('A' + rand.nextInt(26)));
        }while(usedFigures.contains(outFig));

        return outFig;
    }
    private Figure generateFigure(char name) {
        Figure outFig = generateFigure();
        outFig.setShape(name);
        return outFig;
    }


    private void tiles_init() {
        //properties = new ArrayList<>();
        board = new ArrayList<>();
        int usedProps =0;
        for (int i = 0; i <Constants.boardSize; i++) {
            if(i%3==0) //skipping others
                switch (i){// special tiles
                    case 0: board.add(new ActionTile(TileType.START, i));break;
                    case 3: board.add(new ActionTile(TileType.CHANCE,i));break;
                    case 6: board.add(new ActionTile(TileType.JAIL,  i));break;//jail
                    case 9: board.add(new ActionTile(TileType.CHANCE,i));break;
                    case 12:board.add(new ActionTile(TileType.TAX,   i));break;
                    case 15:board.add(new ActionTile(TileType.CHANCE,i));break;
                    case 18:board.add(new ActionTile(TileType.POLICE,i));break;
                    case 21:board.add(new ActionTile(TileType.CHANCE,i));break;
                }
            else {
                PropertyTile prop =  new PropertyTile(Constants.propertyNames[usedProps++],i, //name, pos
                        notRandom.nextInt(Constants.maximumPropertyPrice)/1000*1000);//price
                board.add(prop);
                properties.add(prop);
            }
        }
    }

    void cardPack_init(){
        cardPack = new ArrayList<>();
        for(int i=1;i <= Cards.activeCards;i++)
            cardPack.add(i); // fill the cardPack with card numbers
        java.util.Collections.shuffle(cardPack);

    }



    // I O
    public static void displayMessage(String message){
        System.out.print(message);
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static boolean getYN(String prompt){ // asks promt, gets (Y/N)
        while(true) {
            try {
                Monopoly.displayMessage(prompt);
                char ans = Monopoly.input.nextLine().charAt(0); // bere aj enter z buffru?
                return Character.toUpperCase(ans) == 'Y';
            } catch (StringIndexOutOfBoundsException e) {
                Monopoly.displayMessage("no input!\n");

            }
        }
    }

    static String getColoredPlayersPaddings(int size, char pad, Player... playersToPrint){
        StringBuilder out = new StringBuilder();
        for (var p : playersToPrint) {
            if(p==null)break;
            out.append(p.getFigure().renderString()).append(pad);
        }
        int start = playersToPrint.length*2; //end of "a-b-" a,b=players - pad

        out.append(String.valueOf(pad).repeat(Math.max(0, size - start)));

        return out.toString();
    }

    public static String stringCenter(String s, int size, char pad) {
        if (s == null || size <= s.length())
            return s;

        StringBuilder sb = new StringBuilder(size);
        sb.append(String.valueOf(pad).repeat((size - s.length()) / 2));
        sb.append(s);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

}
