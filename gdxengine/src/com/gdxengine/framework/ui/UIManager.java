package com.gdxengine.framework.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;



public class UIManager extends Stage {

	boolean blockOtherInputs = false;
	
	private Window mainMenu;
	private boolean rs;
	
	SpriteBatch batch;
	private Group components;
	
	IGameService service;

	public UIManager(String title, Skin skin,IGameService service) {
		super();
		
		mainMenu = new Window(title, skin);
		addActor(mainMenu);
		
		this.components = getRoot();
		this.batch = getSpriteBatch();
		
		this.service = service;
	}
	
	public boolean isMenuVisible(){
		return mainMenu.isVisible();
	}
	
	public void setMenuVisible(boolean visible){
		if(visible)
			//always make main menu on top when it's visible
			mainMenu.setZIndex(100);
		
		for(Actor actor : getActors()){
			if(actor != mainMenu){
				if(visible)
					actor.setTouchable(Touchable.disabled);
				else
					actor.setTouchable(Touchable.enabled);
			}
			else
			{
				mainMenu.setVisible(visible);
				mainMenu.setTouchable(Touchable.enabled);
				//make the windows in screen center
				float windowX = (Gdx.graphics.getWidth() - mainMenu.getWidth())/2f;
				float windowY = (Gdx.graphics.getHeight() - mainMenu.getHeight())/2f;
				mainMenu.setPosition(windowX, windowY);
			}
		}
	}

	
//	@Override
//	public boolean keyDown(int keycode) {
//		rs = super.keyDown(keycode);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}
//
//	@Override
//	public boolean keyUp(int keycode) {
//		rs = super.keyUp(keycode);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}
//
//	@Override
//	public boolean keyTyped(char character) {
//		rs = super.keyTyped(character);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}

//	@Override
//	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//		rs = super.touchDown(screenX, screenY, pointer, button);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}

//	@Override
//	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//		rs = super.touchUp(screenX, screenY, pointer, button);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}
//
//	@Override
//	public boolean touchDragged(int screenX, int screenY, int pointer) {
//
//		rs = super.touchDragged(screenX, screenY, pointer);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}
//
//	@Override
//	public boolean mouseMoved(int screenX, int screenY) {
//		rs = super.mouseMoved(screenX, screenY);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}
//
//	@Override
//	public boolean scrolled(int amount) {
//		rs = super.scrolled(amount);
//
//		if (blockOtherInputs)
//			return true;
//		else
//			return rs;
//	}


	public Window getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(Window mainMenu) {
		this.mainMenu = mainMenu;
	}

	public Window getWindows() {
		// TODO Auto-generated method stub
		return mainMenu;
	}
	
	@Override
	public void draw(){
		components.draw(batch, 1);
	}

	public IGameService getGameServices() {
	    // TODO Auto-generated method stub
	    return service;
	}
}
