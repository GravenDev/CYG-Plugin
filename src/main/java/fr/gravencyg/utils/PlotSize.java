package fr.gravencyg.utils;

public enum PlotSize {

    NONE(0),
    NORMAL(75),
    MEDIUM(100),
    BIG(150),
    LARGE(200),
    MEGA(250),
    EXTREME(500),
    GOD(1000);

    private int borderSize;

    PlotSize(int borderSize)
    {
        this.borderSize = borderSize;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public String getName(){
        return name().toUpperCase().charAt(0) + name().toLowerCase().substring(1);
    }
}
