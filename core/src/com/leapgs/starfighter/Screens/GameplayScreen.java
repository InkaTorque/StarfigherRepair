package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Timer;
import com.leapgs.starfighter.Actors.FireActor;
import com.leapgs.starfighter.Actors.StaticImageActor;
import com.leapgs.starfighter.Constants.Constants;
import com.leapgs.starfighter.MainGame;
import com.leapgs.starfighter.Models.LevelData;
import com.leapgs.starfighter.Models.Sector;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class GameplayScreen extends BaseScreen {
    private MainGame mainGame;

    private int currentLevel, currentMinFireLevel,currentMaxFireLevel,maxFiresOnScreen;
    private float currentHealth,currentMinFrequency,currentMaxFrecuency,currentSpawnTime,spawnTimer,currentPoints ;
    public boolean burnAvailable;

    //private StaticImageActor background;

    private TextureAtlas gameAtlas;
    private TextureRegion background;

    private Label healthLabel,pointLabel;
    private Label.LabelStyle style;

    private Array<Vector2> currentSectorsOnScreen;
    private Array<Sector>  currentSectorList;

    private LevelData currentLevelData;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);

        this.mainGame = mainGame;

        mainGame.assetManager.load("sprites/game.atlas", TextureAtlas.class);
        mainGame.assetManager.finishLoading();
        gameAtlas=mainGame.assetManager.get("sprites/game.atlas", TextureAtlas.class);

        background = gameAtlas.findRegion("bg_gameplay");

        System.out.println("CREATING LEVEL "+level);
        currentLevel = level;

        style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor= Color.WHITE;

        currentSectorsOnScreen = new Array<Vector2>();

        setUpLevelData(currentLevel);
    }

    private void setUpLevelData(int currentLevel) {
        FileHandle file = Gdx.files.internal("levels/level"+currentLevel+".json");
        String levelString = file.readString();
        Json json = new Json();

        currentLevelData = json.fromJson(LevelData.class,levelString);
        currentLevelData.createSectorList();
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        burnAvailable=true;

        /*background = new StaticImageActor(mainGame, "bg_gameplay.png", 200, 200);
        stage.addActor(background)*/;

        healthLabel = new Label("",style);
        pointLabel = new Label("",style);

        addLabelToStage(healthLabel,0,350,150,50,Color.WHITE);
        addLabelToStage(pointLabel,250,350,150,50,Color.WHITE);

        currentPoints = 0;

        stage.setDebugAll(true);
        setUpCurrentLevel();
        setCurrentSpawnTime();
    }

    private void addLabelToStage(Label label, float x , float y , float width , float height , Color color) {
        label.setAlignment(Align.center);
        label.setColor(color);
        label.setSize(width,height);
        label.setPosition(x,y);
        stage.addActor(label);
    }

    private void setCurrentSpawnTime() {
        Random ran = new Random();
        currentSpawnTime = currentMinFrequency + (currentMinFrequency+currentMaxFrecuency)*ran.nextFloat();
    }

    private void setUpCurrentLevel() {
        currentSectorsOnScreen.clear();

        currentMaxFireLevel = currentLevelData.getMaxFireLevel();
        currentMinFireLevel = currentLevelData.getMinFireLevel();
        currentMinFrequency = currentLevelData.getMinAppearFrecuency();
        currentMaxFrecuency = currentLevelData.getMaxAppearFrecuency();
        currentSectorList = currentLevelData.getSectors();
        currentHealth = currentLevelData.getHealth();
        maxFiresOnScreen = currentSectorList.size;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        healthLabel.setText("Current Health = "+currentHealth+" LEVEL "+currentLevel);
        pointLabel.setText("Your Score = "+currentPoints);

        if(currentSectorsOnScreen.size!=maxFiresOnScreen)
        {
            if(spawnTimer >= currentSpawnTime)
            {
                spawnFlame();
            }
            else {
                spawnTimer+=delta;
            }

        }

    }

    private void spawnFlame() {
        Sector sect;
        Random ran = new Random();
        Vector2 spawnPos = new Vector2();
        int level,verticalVarianceModifier,horizontalVarianceModifier;

        level = ran.nextInt(currentMaxFireLevel-currentMinFireLevel+1)+currentMinFireLevel;
        do
        {
            sect = currentSectorList.get(ran.nextInt(currentSectorList.size));
        }
        while (currentSectorsOnScreen.contains(sect.sectorOrigin,false));
        currentSectorsOnScreen.add(sect.sectorOrigin);

        verticalVarianceModifier =  assignVarianceModifier(ran);
        horizontalVarianceModifier =  assignVarianceModifier(ran);

        spawnPos.x = sect.sectorOrigin.x + (sect.sectorWidth/2) + horizontalVarianceModifier*Constants.spawnPositionVariance*sect.sectorWidth;
        spawnPos.y = sect.sectorOrigin.y - (sect.sectorWidth/2) + verticalVarianceModifier*Constants.spawnPositionVariance*sect.sectorHeight;

        stage.addActor(new FireActor(this,level, sect.sectorOrigin,spawnPos));
        setCurrentSpawnTime();
        spawnTimer=0f;
    }

    private int assignVarianceModifier(Random ran) {
        if(ran.nextInt(10)%2==0)
        {
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void inflictBurnDamage() {
        currentHealth = currentHealth - Constants.flameDamage;
        if(currentHealth<=0)
        {
            burnAvailable=false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    endGame();
                }
            },1.0f);
        }
    }

    private void endGame() {

        if (scorePrefs.getFloat("highScore"+currentLevel, -1000) == -1000)
        {
            scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
        }
        else
        {
            if(scorePrefs.getFloat("highScore"+currentLevel)<currentPoints)
            {
                scorePrefs.putFloat("highScore"+currentLevel,currentPoints);
            }
        }
        scorePrefs.putFloat("currentScore"+currentLevel,currentPoints);


        game.goToResultsScreen(currentLevel);
        scorePrefs.flush();

    }

    public void notifyFlameExtinguished(Vector2 sp) {
        currentSectorsOnScreen.removeValue(sp,false);
        addPoints();
    }

    private void addPoints() {
        currentPoints += Constants.pointMultiplier*Constants.flamePoints;
    }

    @Override
    public void dispose()
    {
        super.dispose();

    }
}
