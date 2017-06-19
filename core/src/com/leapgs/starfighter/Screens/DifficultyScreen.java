package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.leapgs.starfighter.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class DifficultyScreen extends BaseScreen {

    Skin skin;
    TextButton btn1, btn2, btn3, btn4, btn5;
    float buttonHeight = 50, buttonWidth = 150;

    public DifficultyScreen(MainGame game) {
        super(game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        setUpButtons();

    }

    @Override
    public void hide() {
        super.hide();
        skin.dispose();
    }

    @Override
    public void dispose() {
        super.dispose();

    }

    private void setUpButtons() {
        btn1 = new TextButton("Level 1", skin, "default");
        btn2 = new TextButton("Level 2", skin, "default");
        btn3 = new TextButton("Level 3", skin, "default");
        btn4 = new TextButton("Level 4", skin, "default");
        btn5 = new TextButton("Level 5", skin, "default");

        btn1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(1);
            }
        });
        btn2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(2);
            }
        });
        btn3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(3);
            }
        });
        btn4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(4);
            }
        });
        btn5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                goToLevel(5);
            }
        });

        btn1.setSize(buttonWidth, buttonHeight);
        btn1.setPosition(200 - buttonWidth / 2, 400 - buttonHeight);
        stage.addActor(btn1);

        btn2.setSize(buttonWidth, buttonHeight);
        btn2.setPosition(200 - buttonWidth / 2, 325 - buttonHeight);
        stage.addActor(btn2);

        btn3.setSize(buttonWidth, buttonHeight);
        btn3.setPosition(200 - buttonWidth / 2, 250 - buttonHeight);
        stage.addActor(btn3);

        btn4.setSize(buttonWidth, buttonHeight);
        btn4.setPosition(200 - buttonWidth / 2, 175 - buttonHeight);
        stage.addActor(btn4);

        btn5.setSize(buttonWidth, buttonHeight);
        btn5.setPosition(200 - buttonWidth / 2, 100 - buttonHeight);
        stage.addActor(btn5);
    }

    public void goToLevel(int level) {
        game.goToGameplayLevel(level);
    }
}