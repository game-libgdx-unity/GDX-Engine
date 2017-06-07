package com.gdxengine.framework.test.towerdefense.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AnimationPlayer;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.test.towerdefense.GameAsset;
import com.gdxengine.framework.test.towerdefense.GameSpecs;
import com.gdxengine.framework.test.towerdefense.Tower;

public class Gun extends Weapon {
    
    public Gun(IGameService services, Tower tower) {
	super(services, GameAsset.gun, tower);
	setType(Type.Gun);
	setScale(.7f);
    }
    
    @Override
    public void setAttributes(int level) {
	cost = GameSpecs.gun_cost[level];
	damage = GameSpecs.gun_damage[level];
	range = GameSpecs.gun_range[level];
	timeToAction = .2f;
    }
}
