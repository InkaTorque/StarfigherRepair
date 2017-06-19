package com.leapgs.starfighter.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.leapgs.starfighter.Actors.FireActor;
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
    private int currentLevel, currentMinFireLevel,currentMaxFireLevel,maxFiresOnScreen;
    private float currentHealth,currentMinFrequency,currentMaxFrecuency,currentSpawnTime,spawnTimer,currentPoints ;
    public boolean burnAvailable;

    private Label healthLabel,pointLabel;
    private Label.LabelStyle style;

    private Array<Vector2> currentSectorsOnScreen;
    private Array<Sector>  currentSectorList;
    private HashMap<Integer, LevelData> levelDataArray;

    public GameplayScreen(MainGame mainGame, int level) {
        super(mainGame);
        System.out.println("CREATING LEVEL "+level);
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

    }

    public void notifyFlameExtinguished(Vector2 sp) {
        currentSectorsOnScreen.removeValue(sp,false);
        addPoints();
    }

    private void addPoints() {
        currentPoints += Constants.pointMultiplier*Constants.flamePoints;
    }
}
