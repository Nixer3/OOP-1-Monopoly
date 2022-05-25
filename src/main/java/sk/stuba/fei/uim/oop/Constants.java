package sk.stuba.fei.uim.oop;

public final class Constants {
    public static final int startingMoney = 500000;
    public static final int annualMoney = 20000;
    public static final String currency = "M";

    public static final int diceAgainNumber = 6;
    public static final int jailTime = 3;

    public static final int startPos = 0;
    public static final int jailPos = 6;
    public static final int parkPos =12;
    public static final int policePos = 18;
    public static final int boardSize =24;//including start

    public static final int fine1 =50000;
    public static final int fine2 =20000;
    public static final int fine3 =15000;
    public static final int fine4 =10000;
    public static final int fine5 = 5000;
    public static final int fine6 =    1;
    public static final int taxes =20000; // annualMoney

    public static final int maximumPropertyPrice = 200000;
    public static final float trespassingFraction = 1/10f;
    public static final float upgradeMultiplication = 1.5f;


    public static final String[] propertyNames ={
            "Mediter av.",
            "Baltic av.",
            "Reading Rails",
            "Oriental av.",
            "Vermont av.",
            "Connecticut av.",
            "St.Charles pl.",
            "Electric co.",
            "States av.",
            "Virginia av.",
            "Pennsylvania Rails",
            "St.James pl.",
            "Tennessee av.",
            "New York av.",
            "Kentucky av.",
            "Indiana av.",
            "Illinois av.",
            "B & O Rails",
            "Atlantic av.",
            "Ventnor av.",
            "Marvin Gardens",
            "Pacific av.",
            "S. Caroline av.",
            "Pennsylvania av.",
            "Short Line",
            "Park pl.",
            "Boardwalk",
    };



    private Constants() {
        // restrict instantiation
    }
}
