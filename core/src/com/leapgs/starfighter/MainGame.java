package com.leapgs.starfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.leapgs.starfighter.Models.LevelData;
import com.leapgs.starfighter.Screens.DifficultyScreen;
import com.leapgs.starfighter.Screens.GameplayScreen;
import com.leapgs.starfighter.Screens.ResultsScreen;
import com.leapgs.starfighter.Screens.WelcomeScreen;

public class MainGame extends Game {

	public SpriteBatch batch;
	public Rectangle window;

	public AssetManager assetManager;

	public BitmapFont font;

	public Skin skin;

	public boolean testing = false;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();

		assetManager=new AssetManager();

		//Game Window
		window = new Rectangle();
		window.width = 400;
		window.height = 400;
		window.x = 0;
		window.y = 0;
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		goToWelcomeScreen();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		skin.dispose();
		assetManager.dispose();
		this.getScreen().dispose();
	}

	public void goToGameplayLevel(int level)
	{
		setScreen(new GameplayScreen(this,level));
	}

	public void goToWelcomeScreen()
	{
		setScreen(new WelcomeScreen(this));
	}

	public void goToResultsScreen(int previousLevel)
	{
		setScreen(new ResultsScreen(this,previousLevel));
	}

	public void goToDifficultyScreen()
	{
		setScreen(new DifficultyScreen(this));
	}


}