package com.leapgs.starfighter.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.leapgs.starfighter.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class StaticImageActor extends Actor {

    MainGame game;
    Texture texture;

    public StaticImageActor(MainGame game,String name, float X , float Y)
    {
        this.game = game;
        texture = new Texture("textImages/"+name);
        //coordinates relative to the center of the image
        setPosition(X-(texture.getWidth()/2),Y-(texture.getHeight()/2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }
}
