package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.leapgs.starfighter.Actors.StaticImageActor;
import com.leapgs.starfighter.Constants.Constants;
import com.leapgs.starfighter.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class ResultsScreen extends BaseScreen {

    int lastLevel;

    TextButton goToMainMenuBtn,goToNextLevelButton;
    Label resultMessage,hiScoreMessage,currentScoreMessage;

    public ResultsScreen(MainGame game, int lastLevel)
    {
        super(game);
        this.lastLevel=lastLevel;
    }


    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        setUpResultScreen();
    }

    private void setUpResultScreen()
    {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor=Color.WHITE;

        resultMessage = new Label("LEVEL COMPLETED",style);
        currentScoreMessage = new Label("LEVEL "+lastLevel+" SCORE = "+scorePrefs.getFloat("currentScore"+lastLevel),style);
        hiScoreMessage = new Label("LEVEL "+lastLevel+" HIGH SCORE IS = "+scorePrefs.getFloat("highScore"+lastLevel),style);

        resultMessage.setAlignment(Align.center);
        currentScoreMessage.setAlignment(Align.center);
        hiScoreMessage.setAlignment(Align.center);

        resultMessage.setColor(Color.WHITE);
        currentScoreMessage.setColor(Color.WHITE);
        hiScoreMessage.setColor(Color.WHITE);

        resultMessage.setSize(150,50);
        currentScoreMessage.setSize(150,50);
        hiScoreMessage.setSize(150,50);

        resultMessage.setPosition(125,350);
        currentScoreMessage.setPosition(125,300);
        hiScoreMessage.setPosition(125,250);

        goToMainMenuBtn = new TextButton("Go to Main Menu",game.skin,"default");
        goToMainMenuBtn.addListener( new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.goToWelcomeScreen();
            }
        });
        //if the last level is level 5
        if(lastLevel == Constants.numberOfLevels)
        {
            goToMainMenuBtn.setPosition(112.5f,150);
            goToMainMenuBtn.setSize(175,100);
        }
        else
        {
            goToMainMenuBtn.setPosition(50.0f,150);
            goToMainMenuBtn.setSize(125,50);
            goToNextLevelButton = new TextButton("Go To Next Level",game.skin,"default");
            goToNextLevelButton.setPosition(225.0f,150);
            goToNextLevelButton.setSize(125,50);
            goToNextLevelButton.addListener( new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Going to Next Level");
                    lastLevel = lastLevel+1;
                    game.goToGameplayLevel(lastLevel);
                }
            });
            stage.addActor(goToNextLevelButton);

        }
        stage.addActor(goToMainMenuBtn);
        stage.addActor(resultMessage);
        stage.addActor(hiScoreMessage);
        stage.addActor(currentScoreMessage);
    }

    @Override
    public void hide() {
        super.hide();
    }
}
