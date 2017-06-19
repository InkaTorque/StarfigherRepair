package com.leapgs.starfighter.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.leapgs.starfighter.Constants.Constants;
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
            case 5:burnFrecuency = Constants.level5FlameBurnFrecuency;break;
            case 4:burnFrecuency = Constants.level4FlameBurnFrecuency;break;
            case 3:burnFrecuency = Constants.level3FlameBurnFrecuency;break;
            case 2:burnFrecuency = Constants.level2FlameBurnFrecuency;break;
            case 1:burnFrecuency = Constants.level1FlameBurnFrecuency;break;
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
            case 5:setSize(Constants.level5FlameSize,Constants.level5FlameSize);break;
            case 4:setSize(Constants.level4FlameSize,Constants.level4FlameSize);break;
            case 3:setSize(Constants.level3FlameSize,Constants.level3FlameSize);break;
            case 2:setSize(Constants.level2FlameSize,Constants.level2FlameSize);break;
            case 1:setSize(Constants.level1FlameSize,Constants.level1FlameSize);break;
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
