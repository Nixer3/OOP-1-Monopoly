package sk.stuba.fei.uim.oop;

import java.util.Random;

// pretty much static class
public final class Cards {
    private Cards(){}

    static Random rand = new Random();

    static final int activeCards = 15; //cards implemented

    public static void cardAction(int id, Player player){
        switch (id){
            case 1: func1(player);		break;
            case 2: func2(player);      break;
            case 3: func3(player);      break;
            case 4: func4(player);      break;
            case 5: func5(player);      break;
            case 6: func6(player);      break;
            case 7: func7(player);      break;
            case 8: func8(player);      break;
            case 9: func9(player);      break;
            case 10: func10(player);    break;
            case 11: func11(player);    break;
            case 12: func12(player);    break;
            case 13: func13(player);    break;
            case 14: func14(player);    break;
            case 15: func15(player);    break;
        }

    }


    //receive start bonus
    private static void func1(Player player) {
        Monopoly.displayMessage("Early year end bonus! \n Collected "+Constants.annualMoney+Constants.currency+"\n");
        player.addMoney(Constants.annualMoney);
    }
    // go to parking lot
    private static void func2(Player player) {
        Monopoly.displayMessage("You took the midnight train, Goin' anywhere! \n");
        int steps = rand.nextInt(Constants.boardSize-1);
        if(player.getPosition()+steps==Constants.policePos)steps--;
        player.move(steps);
    }

    //death threat
    private static void func3(Player player){
        Monopoly.displayMessage("You received a death threat. You stepped back.\n");
        player.move(-1);

    }

    //almost mugged, 4 outcomes
    private static void func4(Player player){
        Monopoly.displayMessage("You saw mugger behind the corner.\nYou ran back 3 tiles...\n");
        int newPos = player.getPosition()-3;
        int move = -3;
        int robbedAmount = rand.nextInt(Constants.fine2/1000)*1000;
        switch (newPos){
            case Constants.jailPos: Monopoly.displayMessage("Nearby jail guards helped you out!\n");
                break;

            case Constants.policePos: Monopoly.displayMessage("The police officers caught them,\n you receive a small bonus!"+Constants.annualMoney/2+Constants.currency+"\n They left you behind the station.\n");
                                      player.addMoney(Constants.annualMoney/2);
                                      move=-2;
                break;

            case Constants.parkPos: Monopoly.displayMessage("Your runaway route lead you to empty parking lot!\n they robbed you "+ robbedAmount);
                player.takeMoney(robbedAmount);
                break;

            case Constants.startPos: Monopoly.displayMessage("You know it here really well.\nYou outsmarted them and ran 2 tiles forward\n");
                                     move=-1;
                break;
        }
        player.move(move);
    }

    //fine too ugly
    private static void func5(Player player) {
                                    // You are too ugly. Pay 15000M fine"
        Monopoly.displayMessage("You are too ugly. Pay "+Constants.fine3+Constants.currency +" fine");
        player.takeMoney(Constants.fine3);
    }

    private static void func6(Player player) {
        Monopoly.displayMessage("Your friend has strong position in Police force.\n You don't have to go to jail once.\n");
        player.setJailPass(true);
    }
    private static void func7(Player player) {
        Monopoly.displayMessage("You stepped into an animal's poop.\n  ...\n  You got lucky and found \n"+Constants.fine5+Constants.currency+" on the ground.");
        player.addMoney(Constants.fine5);
    }
    private static void func8(Player player) {
        Monopoly.displayMessage("You see a pretty girl around the corner. You run to speak with her.\n  Turns out she is a prostitute.\n  You had a great time just for  \n"+Constants.fine5+Constants.currency+"\n");
        player.takeMoney(Constants.fine5);
    }
    private static void func9(Player player) {
        Monopoly.displayMessage("Your girlfriend broke up with you.\n  Now you can spend your "+Constants.fine4+Constants.currency+" on yourself\n");
        player.addMoney(Constants.fine5);
    }
    private static void func10(Player player) {
        Monopoly.displayMessage("You bought a girlfriend.\n  You put "+Constants.fine4+Constants.currency+" on her account\n");
        player.takeMoney(Constants.fine5);
    }
    private static void func11(Player player) {
        Monopoly.displayMessage("You just discovered onlyfans.com\n  Let's say you helped some people out of you generosity.\n  -"+Constants.fine5+Constants.currency+"\n");
        player.takeMoney(Constants.fine5);
    }
    private static void func12(Player player) {
        Monopoly.displayMessage("You sold your plie of weed for \n  "+Constants.fine1+Constants.currency+"\n");
        player.addMoney(Constants.fine1);
    }
    private static void func13(Player player) {
        Monopoly.displayMessage("You bought your plie of weed for \n  "+Constants.fine1+Constants.currency+"\n");
        player.takeMoney(Constants.fine1);
    }
    private static void func14(Player player) {
        Monopoly.displayMessage("You just took shower. Chill out. \n");
    }
    //lottery wheel
    private static void func15(Player player) {
        Monopoly.displayMessage("Let's play a game.\n" +
                                "  A lottery wheel:\n" +ConsoleColors.RESET+
                                "  Prices are:1)  "+Constants.fine1+Constants.currency+"\n"+
                                "             2)  "+Constants.fine2+Constants.currency+"\n"+
                                "             3)  "+Constants.fine3+Constants.currency+"\n"+
                                "             4)  "+Constants.fine4+Constants.currency+"\n"+
                                "             5)   "+Constants.fine5+Constants.currency+"\n"+
                                "             6)      0"+Constants.currency+"\n"+
                                "             7)  -"+Constants.fine5+Constants.currency+"\n"+
                                "             8) -"+Constants.fine4+Constants.currency+"\n"+
                                "             9) -"+Constants.fine3+Constants.currency+"\n"+
                                "            10) -"+Constants.fine2+Constants.currency+"\n"+
                                "            11) -"+Constants.fine1+Constants.currency+"\n");
        Monopoly.displayMessage(player.getFigure().getColorCode());
        int spin = rand.nextInt(10)+1;
        Monopoly.displayMessage("  You spun: "+ConsoleColors.WHITE_BOLD+spin+"\n");
        Monopoly.displayMessage(player.getFigure().getColorCode());

        switch (spin){
            case  1: player.takeMoney(Constants.fine1); Monopoly.displayMessage("   You lost "+Constants.fine1+Constants.currency+"\n");break;
            case  2: player.takeMoney(Constants.fine2); Monopoly.displayMessage("   You lost "+Constants.fine2+Constants.currency+"\n");break;
            case  3: player.takeMoney(Constants.fine3); Monopoly.displayMessage("   You lost "+Constants.fine3+Constants.currency+"\n");break;
            case  4: player.takeMoney(Constants.fine4); Monopoly.displayMessage("   You lost "+Constants.fine4+Constants.currency+"\n");break;
            case  5: player.takeMoney(Constants.fine5); Monopoly.displayMessage("   You lost "+Constants.fine5+Constants.currency+"\n");break;
            case  6:                                    Monopoly.displayMessage("   You wonlost 0"            +Constants.currency+"\n");break;//just a joke
            case  7: player.addMoney(Constants.fine5);  Monopoly.displayMessage("   You won "+Constants.fine5+Constants.currency+"\n") ;break;
            case  8: player.addMoney(Constants.fine4);  Monopoly.displayMessage("   You won "+Constants.fine4+Constants.currency+"\n") ;break;
            case  9: player.addMoney(Constants.fine3);  Monopoly.displayMessage("   You won "+Constants.fine3+Constants.currency+"\n") ;break;
            case 10: player.addMoney(Constants.fine2);  Monopoly.displayMessage("   You won "+Constants.fine2+Constants.currency+"\n") ;break;
            case 11: player.addMoney(Constants.fine1);  Monopoly.displayMessage("   You won "+Constants.fine1+Constants.currency+"\n") ;break;
        }


    }
}
