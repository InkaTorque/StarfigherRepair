package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.leapgs.starfighter.Actors.FireActor;
import com.leapgs.starfighter.MainGame;
import com.leapgs.starfighter.Models.LevelData;
import com.leapgs.starfighter.Models.Sector;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Leap-Pancho on 6/15/2017.
 */

public class GameplayScreen extends BaseScreen {
    private int currentLevel, currentMinFireLevel,currentMaxFireLevel,maxFiresOnScreen;
    private float currentHealth,currentMinFrequency,currentMaxFrecuency,currentSpawnTime,spawnTimer ;
    public boolean burnAvailable;

    private Label healthLabel;
    private Label.LabelStyle style;

    private Array<Vector2> currentSectorsOnScreen;
    private Array<Sector>  currentSectorList;
    private HashMap<Integer, LevelData> levelDataArray;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        currentLevel = level;

        style = new Label.LabelStyle();
        style.font=game.font;
        style.fontColor= Color.WHITE;

        currentSectorsOnScreen = new Array<Vector2>();
        levelDataArray = new HashMap<Integer, LevelData>();

        setUpLevelData();
    }

    private void setUpLevelData() {
        new LevelData(100,1,5,0.5f,1.5f,4,3,25,30,75,0);
        levelDataArray.put(1,new LevelData(100,1,5,2.0f,4.0f,4,3,30,30,75,170));
        levelDataArray.put(2,new LevelData(100,1,5,0.5f,1.5f,4,3,30,30,75,170));
        levelDataArray.put(3,new LevelData(100,1,5,0.5f,1.5f,4,3,30,30,75,170));
        levelDataArray.put(4,new LevelData(100,1,5,0.5f,1.5f,4,3,30,30,75,170));
        levelDataArray.put(5,new LevelData(100,1,5,0.5f,1.5f,4,3,30,30,75,170));

    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(stage);
        burnAvailable=true;

        healthLabel = new Label("",style);
        healthLabel.setAlignment(Align.center);
        healthLabel.setColor(Color.WHITE);
        healthLabel.setSize(150,50);
        healthLabel.setPosition(125,350);

        stage.addActor(healthLabel);

        stage.setDebugAll(true);
        setUpCurrentLevel();
        setCurrentSpawnTime();
    }

    private void setCurrentSpawnTime() {
        Random ran = new Random();
        currentSpawnTime = currentMinFrequency + (currentMinFrequency+currentMaxFrecuency)*ran.nextFloat();
    }

    private void setUpCurrentLevel() {
        currentSectorsOnScreen.clear();

        currentMaxFireLevel = levelDataArray.get(currentLevel).maxFireLevel;
        currentMinFireLevel = levelDataArray.get(currentLevel).minFireLevel;
        currentMinFrequency = levelDataArray.get(currentLevel).minAppearFrecuency;
        currentMaxFrecuency = levelDataArray.get(currentLevel).maxAppearFrecuency;
        currentSectorList = levelDataArray.get(currentLevel).sectors;
        currentHealth = levelDataArray.get(currentLevel).health;
        maxFiresOnScreen = currentSectorList.size;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        healthLabel.setText("Current Health = "+currentHealth);
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
        int level;
        level = ran.nextInt(currentMaxFireLevel-currentMinFireLevel+1)+currentMinFireLevel;
        System.out.println("LOOKING FOR SECTOR");
        do{
            sect = currentSectorList.get(ran.nextInt(currentSectorList.size));
            System.out.println(sect.toString());
        }while (currentSectorsOnScreen.contains(sect.sectorOrigin,false));
        System.out.println("SECTOR FOUND");
        currentSectorsOnScreen.add(sect.sectorOrigin);

        spawnPos.x = sect.sectorOrigin.x + (sect.sectorWidth/2);
        spawnPos.y = sect.sectorOrigin.y - (sect.sectorWidth/2);

        stage.addActor(new FireActor(this,level, sect.sectorOrigin,spawnPos));
        setCurrentSpawnTime();
        spawnTimer=0f;
    }

    public void inflictBurnDamage() {
        currentHealth--;
        if(currentHealth<=0)
        {
            burnAvailable=false;
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    game.goToResultsScreen(currentLevel);
                }
            },1.0f);
        }
    }

    public void notifyFlameExtinguished(Vector2 sp) {
        currentSectorsOnScreen.removeValue(sp,false);
    }
}
