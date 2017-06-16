package com.leapgs.starfighter.Models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Leap-Pancho on 6/16/2017.
 */

public class Sector {

    public float sectorWidth,sectorHeight;
    public Vector2 sectorOrigin;

    public Sector(float w,float h,float x,float y) {
        sectorWidth = w;
        sectorHeight = h;
        sectorOrigin = new Vector2(x,y);
    }

    @Override
    public String toString() {
        return "X "+sectorOrigin.x+" , Y "+sectorOrigin.y+"";
    }
}
