package com.adidesi95.nodechess.model;

/**
 * Created by DESAIs on 2/11/2016.
 */
public class Move {
    public int xOffset;
    public int yOffset;
    public boolean isVector;//true if the x and y is a vector

    public Move(int xOffset, int yOffset, boolean isVector) {
        this.isVector = isVector;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}
