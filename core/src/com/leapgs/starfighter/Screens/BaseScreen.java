package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.leapgs.starfighter.Actors.ScreenOverlayActor;
import com.leapgs.starfighter.MainGame;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class BaseScreen implements Screen {

    public MainGame game;

    //Camera
    protected OrthographicCamera camera;
    protected Viewport viewport;

    Stage stage;

    ScreenOverlayActor overlayActor;
    Preferences scorePrefs;

    public BaseScreen(MainGame mainGame)
    {
        this.game = mainGame;

        //Camera Settings
        camera=new OrthographicCamera();
        camera.setToOrtho(false, game.window.width, game.window.height);

        if(game.testing) viewport=new FitViewport(game.window.width*1.65f, game.window.height*1.65f, camera);
        else viewport=new FitViewport(game.window.width, game.window.height, camera); //Keep Screen Size

        scorePrefs = Gdx.app.getPreferences("Score");
    }

    @Override
    public void show() {
        stage = new Stage(viewport);
        overlayActor = new ScreenOverlayActor(game);
        stage.addActor(overlayActor);
    }


    public void render(float delta) {
        Gdx.gl20.glClearColor(0.0f,0.0f,0.0f,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();


    }
    public void render(float delta,float r, float g, float b) {
        Gdx.gl20.glClearColor(r,g,b,1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
    }
}
