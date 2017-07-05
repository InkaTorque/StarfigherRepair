package com.leapgs.starfighter.Models;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class LevelData {

    private int minFireLevel,maxFireLevel, rows, colums;
    private float minAppearFrecuency,maxAppearFrecuency,health, marginXLeft, marginXRight,marginYUp,marginYDown;
    private Array<Sector> sectors;

    public LevelData(float health, int minFireLevel, int maxFireLevel, float minAppearFrecuency, float maxAppearFrecuency, int rows, int colums , float marginXL, float marginXR, float marginYU, float marginYD) {
        this.minFireLevel = minFireLevel;
        this.maxFireLevel = maxFireLevel;
        this.minAppearFrecuency = minAppearFrecuency;
        this.maxAppearFrecuency = maxAppearFrecuency;
        this.health = health;
        this.rows = rows;
        this.colums = colums;
        this.marginXLeft = marginXL;
        this.marginXRight = marginXR;
        this.marginYUp = marginYU;
        this.marginYDown = marginYD;

        createSectorList();
    }

    public LevelData() {
    }

    public int getMinFireLevel() {
        return minFireLevel;
    }

    public int getMaxFireLevel() {
        return maxFireLevel;
    }

    public int getRows() {
        return rows;
    }

    public int getColums() {
        return colums;
    }

    public float getMinAppearFrecuency() {
        return minAppearFrecuency;
    }

    public float getMaxAppearFrecuency() {
        return maxAppearFrecuency;
    }

    public float getHealth() {
        return health;
    }

    public float getMarginXLeft() {
        return marginXLeft;
    }

    public float getMarginXRight() {
        return marginXRight;
    }

    public float getMarginYUp() {
        return marginYUp;
    }

    public float getMarginYDown() {
        return marginYDown;
    }

    public Array<Sector> getSectors() {
        return sectors;
    }

    public void createSectorList() {

        sectors = new Array<Sector>();
        float xArea,yArea,xOrigin,yOrigin,xStep,yStep;

        xArea = 400 - marginXLeft - marginXRight;
        yArea = 400 - marginYUp - marginYDown;

        xStep = xArea/colums;
        yStep = yArea/rows;

        xOrigin = marginXLeft;
        yOrigin = 400 - marginYUp;

        for(int i=0;i<rows;i++)
        {
            for(int j=0;j<colums;j++)
            {
                sectors.add(new Sector(xStep,yStep,xOrigin+xStep*j,yOrigin-yStep*i));
            }
        }

    }

}
