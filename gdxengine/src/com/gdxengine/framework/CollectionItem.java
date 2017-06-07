package com.gdxengine.framework;

import com.gdxengine.framework.interfaces.ICollectionItem;

/**
 * the standard class that implements ICollectionItem interface Yours derived
 * CollectionItem can be inherited from the class
 * 
 * @author Vinh
 * 
 */
public abstract class CollectionItem implements ICollectionItem {

    boolean isEnabled = true;

    /**
	 * 
	 */
    public CollectionItem() {
	isEnabled = true;
	isDead = false;
	visible = true;
    }

    protected boolean isDead = false;
    private boolean visible = true;

    @Override
    public boolean isEnabled() {
	// TODO Auto-generated method stub
	return isEnabled;
    }

    @Override
    public void setEnabled(boolean enable) {
	isEnabled = enable;
    }

    @Override
    public boolean isDead() {
	// TODO Auto-generated method stub
	return isDead;
    }

    @Override
    public void setDead(boolean value) {
	setEnabled(!value);
	setVisible(!value);
	isDead = value;
    }

    @Override
    public void pause() {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume() {
	// TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isVisible() {
	// TODO Auto-generated method stub
	return visible;
    }

    @Override
    public void setVisible(boolean visible) {
	this.visible = visible;
    }
}
