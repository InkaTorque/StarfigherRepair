package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.leapgs.starfighter.Actors.StaticImageActor;
import com.leapgs.starfighter.InputProcessors.WelcomeScreenInputProcessor;
import com.leapgs.starfighter.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class WelcomeScreen extends BaseScreen {

    String gameLogo="starfighterLogo.png";

    StaticImageActor logoActor,tapMessageActor;

    public WelcomeScreen(MainGame game) {
        super(game);
    }

    @Override
    public void render(float delta)
    {
        super.render(delta);
    }

    @Override
    public void show() {
        super.show();
        logoActor = new StaticImageActor(game,gameLogo,200,250);
        tapMessageActor = new StaticImageActor(game,"tapTex.png",200,150);
        stage.addActor(logoActor);
        stage.addActor(tapMessageActor);

        WelcomeScreenInputProcessor p =  new WelcomeScreenInputProcessor(this);
        Gdx.input.setInputProcessor(new GestureDetector(p));
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
    }
}