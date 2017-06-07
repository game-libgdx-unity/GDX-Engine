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

public class Canon extends Weapon {


    public Canon(IGameService services, Tower tower) {
	super(services, GameAsset.canon, tower);
	setType(Type.Canon);
	setScale(.8f);
	setOrigin(32f, 48f);
    }

    @Override
    public void setAttributes(int level) {
	cost = GameSpecs.canon_cost[level];
	damage = GameSpecs.canon_damage[level];
	range = GameSpecs.canon_range[level];
	timeToAction = GameSpecs.canon_timeToAction[level];
    }
}
