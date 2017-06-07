package com.gdxengine.framework.test.towerdefense.weapon;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AnimationPlayer;
import com.gdxengine.framework.DefaultSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.test.towerdefense.Level;
import com.gdxengine.framework.test.towerdefense.Monster;
import com.gdxengine.framework.test.towerdefense.Player;
import com.gdxengine.framework.test.towerdefense.Tower;

public abstract class Weapon extends DefaultSprite {

    public enum Type {
	Gun, Canon, Slower, None
    }

    public Type type = Type.None;
    //Tower that contains this weapon
    Tower tower;
    //attributes of weapon
    protected int level;
    protected int range = 200;
    protected int cost = 100;
    protected int damage = 10;
    protected float timeToAction = .5f;
    protected AnimationPlayer aPlayer;

    protected Player player;
    protected Level gameLevel;

    Monster target;
    boolean readyToFire = true;
    BaseSimpleAction action;

    

    public void levelUp() {
	level++;
	setAttributes(level);
    }

    /**
     * set attributes for weapon each level
     * 
     * @param level
     */
    public abstract void setAttributes(int level);

    public Weapon(IGameService services, TextureRegion texture, Tower tower) {
	super(services, texture);
	level = 0;
	setAttributes(level);
	this.tower = tower;
	this.aPlayer = new AnimationPlayer(Tower.weaponAnimation) {

	    @Override
	    public void onFrameChanged() {
		setRegion(frameIndex * 63, 0, 63, 63);
	    }

	    @Override
	    public void onFinishedLooping() {
		//fire after finish animation
		if (readyToFire) {
		    if (target != null) {
			target.hitPoint -= damage;
			onAttack(target);
			if (!target.isAlive()) {
			    player.gold += target.getBonus();
			    setTarget(null);
			}
		    }
		    readyToFire = false;
		    action.replay();
		}
	    }
	    @Override
	    public void onAnimationChanged() {
	    }
	};
	this.action = new BaseSimpleAction(true, timeToAction) {

	    @Override
	    public void onActionPerformance() {
		readyToFire = true;
		pause();
	    }
	};
	initialize();
    }
    protected void onAttack(Monster target) {
	// TODO Auto-generated method stub
	
    }
    @Override
    public void initialize() {
	player = getGameService().getService(Player.class);
	gameLevel = getGameService().getService(Level.class);
	super.initialize();
    }

    @Override
    public void update(float gameTime) {
	action.update(gameTime);
	aPlayer.updateAnimation(gameTime);
	if (target != null) {
	    faceEnemy();
	    if (readyToFire)
		if (aPlayer.isStop()) {
		    aPlayer.replayAnimation();
		}
	    if (Utils.distance(tower.getCenterX(), tower.getCenterY(),
		    target.getCenterX(), target.getCenterY()) > range) {
		getTarget(gameLevel.getCurrentWave().getObjectCollection());
	    }
	} else {
	    getTarget(gameLevel.getCurrentWave().getObjectCollection());
	}
    }

    public void faceEnemy() {
	Vector2 direction = new Vector2(getX() + 16 - target.getX()
		- Monster.REGION_WIDTH / 2f, getY() + 16 - target.getY()
		- Monster.REGION_HEIGHT / 2f);
	direction.nor();

	setRotation((float) Math.toDegrees(Math
		.atan2(-direction.x, direction.y)));
    }

    public void getTarget(ArrayList<Monster> enemies) {
	for (Monster monster : enemies) {
	    
	    if(!monster.isAlive())
		continue;
	    if (Utils.distance(tower.getCenterX(), tower.getCenterY(),
		    monster.getCenterX(), monster.getCenterY()) < range) {
		
		target = monster;
		faceEnemy();
		return;
	    }
	}
	target = null;
	return;
    }

    public void sold() {
	player.gold += getCost() * .5f;
    }

    public Type getType() {
	return type;
    }

    public void setType(Type type) {
	this.type = type;
    }

    public Tower getTower() {
	return tower;
    }

    public void setTower(Tower tower) {
	this.tower = tower;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public int getRange() {
	return range;
    }

    public void setRange(int range) {
	this.range = range;
    }

    public int getCost() {
	return cost;
    }

    public void setCost(int cost) {
	this.cost = cost;
    }

    public AnimationPlayer getAnimationPlayer() {
	return aPlayer;
    }

    public Monster getTarget() {
	return target;
    }

    public void setTarget(Monster target) {
	this.target = target;
    }
}
