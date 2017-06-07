package com.gdxengine.framework.test.towerdefense.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AnimationPlayer;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.test.towerdefense.GameAsset;
import com.gdxengine.framework.test.towerdefense.GameSpecs;
import com.gdxengine.framework.test.towerdefense.Monster;
import com.gdxengine.framework.test.towerdefense.Tower;

public class Slower extends Weapon {

    float slowAmount = 0f;
    private float timeToRestore = 1f;

    public Slower(IGameService services, Tower tower) {
	super(services, GameAsset.slower, tower);
	setType(Type.Slower);
	setScale(.8f);
	setOrigin(32f, 43f);
    }

    @Override
    public void setAttributes(int level) {
	cost = GameSpecs.slower_cost[level];
	damage = GameSpecs.slower_damage[level];
	range = GameSpecs.slower_range[level];
	timeToAction = GameSpecs.slower_timeToAction[level];
	slowAmount = GameSpecs.slower_slowerRate[level];
	timeToRestore =  GameSpecs.slower_timeToRestore[level];
    }
    
    @Override
    protected void onAttack(Monster target) {
	float slowRate = 1f - slowAmount;
	if(target.getSlowRate() > slowRate){
	    target.setSlowRate(slowRate);
	    target.getActionRestoreSpeed().setElapsedTime(timeToRestore );
	}
	target.setColor(Color.BLUE);
	target.getActionRestoreSpeed().resetTimeLine();
    }
    
//    @Override
//    public void getTarget(ArrayList<Monster> enemies) {
//	for (int i = 0; i < enemies.size(); i++) {
//	    Monster monster = enemies.get(i);
//	    if(monster.getSlowRate() != 1f){
//		continue;
//	    }
//	    if (Utils.distance(tower.getCenterX(), tower.getCenterY(),
//		    monster.getCenterX(), monster.getCenterY()) < range) {
//		target = monster;
//		faceEnemy();
//		return;
//	    }
//	    
//	}
//	
//	if(target == null)
//	    super.getTarget(enemies);
//	return;
//    }
}
