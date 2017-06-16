package com.leapgs.starfighter.InputProcessors;

import com.badlogic.gdx.input.GestureDetector;
import com.leapgs.starfighter.Screens.WelcomeScreen;
import com.sun.org.apache.xpath.internal.SourceTree;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class WelcomeScreenInputProcessor extends GestureDetector.GestureAdapter {

    WelcomeScreen screen;

    public WelcomeScreenInputProcessor(WelcomeScreen screen)
    {
        this.screen=screen;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return true;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("TAP");
        screen.game.goToDifficultyScreen();
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return true;
    }
}