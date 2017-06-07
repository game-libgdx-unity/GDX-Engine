package com.gdxengine.framework.test.towerdefense;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.action.BaseLoopingAction;
import com.gdxengine.framework.interfaces.IGameService;

public class Wave extends ObjectCollection<Monster> {
    
    int index;
    BaseLoopingAction action;
    Texture texture;
    protected Level level;
    public boolean ended = false;

    /**
     * @param hitPoint
     * @param bonus
     * @param speed
     * @param numberOfMonster
     * @param timeToRelease
     */
    public Wave(IGameService service, int index) {
	super(service);
	this.index = index;
	texture= GameAsset.monsterTexture[index];
	texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	ended = false;
	action = new BaseLoopingAction(GameSpecs.numberOfMonster[index], GameSpecs.timeToRelease[index]) {
	    
	    @Override
	    public void onActionRemove() {
		ended = true;
	    }
	    
	    @Override
	    public void onActionPerformance() {
		createMonster();
	    }
	};
    }
    
    @Override
    public void initialize(){
	
	level = getGameService().getService(Level.class);
	super.initialize();
    }
    
    public boolean isFinishedWave(){
	for(Monster m : objectCollection)
	    if(m.isEnabled())
		return false;
	
	return true;
    }
    
    public void createMonster(){
	Monster m = new Monster(getGameService(),texture, index) {
	    
	    @Override
	    public void onFinishedPath() {
		setDead(true);
		//check for player is alive?
		Player p = services.getService(Player.class);
		p.lives--;
		if(p.isLose()){
		    TowerDefenseScene.gameDialog.build(-1);
		    return;
		}
	    }
	    
	    @Override
	    public void onDead() {
		setDead(true);
	    }
	};
	
	addItem(m);
	
    }
    
    @Override
    public void update(float gameTime) {
	action.update(gameTime);
	super.update(gameTime);
	if(getObjectCollection().size() <= 0 && ended){
	    level.nextWave();
	}
    }
    
    @Override
    public void render(float gameTime) {
	for (Monster m : objectCollection) {
	    if(m.isVisible())
		m.render(gameTime);
	}
    }
}
