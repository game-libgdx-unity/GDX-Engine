package com.gdxengine.framework.test.towerdefense;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.DefaultSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.event.TouchEvent;
import com.gdxengine.framework.event.listener.TouchListener;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.test.towerdefense.weapon.Weapon;
import com.gdxengine.framework.test.towerdefense.weapon.Weapon.Type;

public class Tower extends DefaultSprite implements TouchListener {

    public static final int TowerCost = 100;
    public static final Animation weaponAnimation = new Animation(false, .08f, 4);

    protected Player player;
    protected Weapon weapon;
    protected Level gameLevel;

    int rotateSpeed = 1;
    float targetAngle;

    public boolean hasWeapon() {
	if (weapon == null)
	    return false;
	else if (weapon.type == Type.None)
	    return false;
	else
	    return true;
    }

    public Tower(IGameService services) {
	super(services, GameAsset.towerTexture);
    }

    public Tower(IGameService services, TextureRegion texture) {
	super(services, texture);
    }

    @Override
    public void update(float gameTime) {
	if (hasWeapon())
	    weapon.update(gameTime);
    }

    public void renderWeapon(float gameTime) {
	if (hasWeapon())
	    weapon.render(gameTime);
    }

    public Player getPlayer() {
	return player;
    }

    public void setPlayer(Player player) {
	this.player = player;
    }

    @Override
    public boolean onTouchUp(TouchEvent e) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean onTouchDown(TouchEvent e) {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean onTouchDragged(TouchEvent e) {
	// TODO Auto-generated method stub
	return false;
    }

    public Weapon getWeapon() {
	return weapon;
    }

    public void setWeapon(Weapon weapon) {
	this.weapon = weapon;
    }

}
