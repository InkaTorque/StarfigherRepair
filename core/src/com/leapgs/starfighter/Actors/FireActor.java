package com.leapgs.starfighter.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.leapgs.starfighter.Screens.GameplayScreen;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class FireActor extends Actor {

    private int flameLevel;
    private Texture fireTexture;
    private GameplayScreen screen;
    private float burnFrecuency,burnTimer;
    private Vector2 sp, assignedSectorOrigin;


    public FireActor(GameplayScreen screen, int fireLevel, Vector2 sectorOrigin, Vector2 spawnPosition) {
        this.screen = screen;
        this.fireTexture = new Texture("sprites/flame.png");
        this.flameLevel = fireLevel;
        assignBurnRate(flameLevel);
        sp = spawnPosition;
        resizeFlame(flameLevel);
        addListener(new ClickListener(){
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            lowerFlameLevel();
                        }
                    }
        );
        assignedSectorOrigin = sectorOrigin;
        screen.inflictBurnDamage();
    }

    private void assignBurnRate(int flameLevel) {
        switch (flameLevel)
        {
            case 5:burnFrecuency = 1;break;
            case 4:burnFrecuency = 3;break;
            case 3:burnFrecuency = 4;break;
            case 2:burnFrecuency = 5;break;
            case 1:burnFrecuency = 6;break;
        }
    }

    public void lowerFlameLevel()
    {
        System.out.println("flame clicked");
        flameLevel--;
        if(flameLevel==0)
        {
            screen.notifyFlameExtinguished(assignedSectorOrigin);
            remove();
        }
        else {
            resizeFlame(flameLevel);
        }
    }

    private void resizeFlame(int flameLevel) {

        switch (flameLevel)
        {
            case 5:setSize(70.f,70.f);break;
            case 4:setSize(60.f,60.f);break;
            case 3:setSize(50.f,50.f);break;
            case 2:setSize(40.f,40.f);break;
            case 1:setSize(30.f,30.f);break;
        }
        setPosition(sp.x - getWidth()/2,sp.y -getHeight()/2);
    }

    @Override
    public void act(float delta) {
        if(burnTimer>=burnFrecuency)
        {
            if(screen.burnAvailable)
            {
                screen.inflictBurnDamage();
                burnTimer=0f;
            }
        }
        else
        {
            burnTimer+=delta;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(fireTexture,getX(),getY(),getWidth(),getHeight());
    }


}
