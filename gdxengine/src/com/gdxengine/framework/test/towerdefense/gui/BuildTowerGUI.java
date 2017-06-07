package com.gdxengine.framework.test.towerdefense.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.event.TouchEvent;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;
import com.gdxengine.framework.scene.TileRenderer;
import com.gdxengine.framework.test.towerdefense.GameAsset;
import com.gdxengine.framework.test.towerdefense.Player;
import com.gdxengine.framework.test.towerdefense.Tower;

public class BuildTowerGUI extends BaseGUI {

    TextButton btnAddTower;

    public BuildTowerGUI(Skin skin) {
	super(skin);
    }

    @Override
    public void addListener(final IGameService service, final int x, final int y) {
	// clear all added listener
	for (int i = 1; i < btnAddTower.getListeners().size; i++) {
	    btnAddTower.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnAddTower.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		if (player.gold >= Tower.TowerCost) {
		    player.gold -= Tower.TowerCost;

		    Tower t = new Tower(service);
		    t.setPosition(x, y);
		    player.addItemWithInitialize(t);
		    hide();

		    TouchEvent e = ((BaseGameScene) service.getCurrentScene())
			    .getTouchEvent();
		    TiledMap map = service.getService(TileRenderer.class)
			    .getMap();
		    int i = e.getX();
		    int j = e.getY();
		    // Get the size from tile map
		    i = i / map.tileWidth;
		    j = j / map.tileHeight;
		    // get the size from world
		    i = i * map.tileWidth;
		    j = j * map.tileHeight;

		    Vector3.tmp.set(i + map.tileWidth, j + map.tileHeight / 2,
			    0);
		    service.getCamera().project(Vector3.tmp);

		    // show a option for build a new tower
		    GuiManager guiManager = service
			    .getService(GuiManager.class);
		    guiManager.setTower("addWeapon", t);
		    if (Gdx.graphics.getWidth() >= 800)
			guiManager.show("addWeapon", service,
				(int) Vector3.tmp.x, (int) Vector3.tmp.y, i, j);
		    else
			guiManager.show("addWeapon", service, 0, 0, i, j);
		}
	    }
	});
    }
    
    @Override
    public void show() {
	checkUI(btnAddTower, "Add tower", 100);
	super.show();
    }

    @Override
    protected void onBuild(Skin skin) {
	btnAddTower = new TextButton("",skin);
	btnAddTower.setText("Add Tower ($100)");
	row().fill().expandX();
	add(btnAddTower).expandX();
	super.onBuild(skin);
    }
    
    
}
