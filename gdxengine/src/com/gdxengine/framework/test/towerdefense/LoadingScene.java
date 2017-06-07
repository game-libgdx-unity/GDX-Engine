package com.gdxengine.framework.test.towerdefense;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.DefaultGameAsset;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IScene2D;
import com.gdxengine.framework.scene.BaseGameScene;
import com.gdxengine.framework.scene.TiledScene;
import com.gdxengine.framework.ui.ITabContent;
import com.gdxengine.framework.ui.TabPane;

public class LoadingScene implements IScene2D {

	
	private BitmapFont bigFont;
	SpriteBatch batch;

	public LoadingScene(SpriteBatch batch) {
	    bigFont = AssetLoader.load(BitmapFont.class, "default.fnt");
	    bigFont.scale(1f);
	    this.batch = batch;
	}

	@Override
	public void update(float gameTime) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void render(float gameTime) {
	    bigFont.draw(batch, "LOADING", 0, 0);
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
	public void dispose() {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public boolean isContinueNeedInit() {
	    // TODO Auto-generated method stub
	    return false;
	}

	@Override
	public void setContinueNeedInit(boolean value) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void initScene() {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public void initialize() {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public SpriteBatch getSpriteBatch() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public <T> ArrayList<T> getObjectCollection() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void addOrRecycleObject(Object obj) {
	    // TODO Auto-generated method stub
	    
	}

	@Override
	public IGameService getGameService() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public void addDrawbleObject(IDrawable obj) {
	    // TODO Auto-generated method stub
	    
	}

	
}
