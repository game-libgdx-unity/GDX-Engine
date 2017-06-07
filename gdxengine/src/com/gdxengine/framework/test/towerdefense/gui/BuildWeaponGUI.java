package com.gdxengine.framework.test.towerdefense.gui;

import com.badlogic.gdx.Gdx;
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
import com.gdxengine.framework.test.towerdefense.GameSpecs;
import com.gdxengine.framework.test.towerdefense.Player;
import com.gdxengine.framework.test.towerdefense.Tower;
import com.gdxengine.framework.test.towerdefense.weapon.Canon;
import com.gdxengine.framework.test.towerdefense.weapon.Gun;
import com.gdxengine.framework.test.towerdefense.weapon.Slower;

public class BuildWeaponGUI extends BaseGUI {

    TextButton btnAddGun;
    TextButton btnAddCanon;
    TextButton btnAddSlower;

    public BuildWeaponGUI(Skin skin) {
	super(skin);
    }
    
    @Override
    public void show() {
	checkUI(btnAddGun, "Add Gun", GameSpecs.gun_cost_first);
	checkUI(btnAddCanon, "Add Canon", GameSpecs.canon_cost_first);
	checkUI(btnAddSlower, "Add Slower", GameSpecs.slower_cost_first);
	super.show();
    }

    @Override
    public void addListener(final IGameService service, final int x, final int y) {
	// clear all added listener
	for (int i = 1; i < btnAddGun.getListeners().size; i++) {
	    btnAddGun.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnAddGun.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		Player player = service.getService(Player.class);
		if (player.gold >= GameSpecs.gun_cost_first) {
		    player.gold -= GameSpecs.gun_cost_first;

		    Gun g = new Gun(service, tower);
		    g.setPosition(x - 16, y - 16);
		    tower.setWeapon(g);
		    hide();

		    drawTowerRange(service);
		}
	    }
	});
	// clear all added listener
	for (int i = 1; i < btnAddCanon.getListeners().size; i++) {
	    btnAddCanon.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnAddCanon.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		Player player = service.getService(Player.class);
		if (player.gold >= GameSpecs.canon_cost_first) {
		    player.gold -= GameSpecs.canon_cost_first;

		    Canon g = new Canon(service, tower);
		    g.setPosition(x - 16, y - 32);
		    tower.setWeapon(g);
		    hide();
		    drawTowerRange(service);
		}
	    }
	});
	// clear all added listener
	for (int i = 1; i < btnAddSlower.getListeners().size; i++) {
	    btnAddSlower.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnAddSlower.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		Player player = service.getService(Player.class);
		if (player.gold >= GameSpecs.slower_cost_first) {
		    player.gold -= GameSpecs.slower_cost_first;

		    Slower g = new Slower(service, tower);
		    g.setPosition(x - 16, y - 32);
		    tower.setWeapon(g);
		    hide();
		    drawTowerRange(service);
		}
	    }
	});
    }

    @Override
    protected void onBuild(Skin skin) {
	btnAddGun = new TextButton("Add Gun Lv.1 ($" + GameSpecs.gun_cost_first
		+ ")", skin);
	row().fill().expandX();
	add(btnAddGun).expandX();
	btnAddCanon = new TextButton("Add Canon Lv.1 ($"
		+ GameSpecs.canon_cost_first + ")", skin);
	row().fill().expandX();
	add(btnAddCanon).expandX();
	btnAddSlower = new TextButton("Add Slower Lv.1 ($"
		+ GameSpecs.slower_cost_first + ")", skin);
	row().fill().expandX();
	add(btnAddSlower).expandX();
	super.onBuild(skin);
    }

    private void drawTowerRange(final IGameService service) {
	TouchEvent e = ((BaseGameScene)service.getCurrentScene()).getTouchEvent();
	TiledMap map = service.getService(TileRenderer.class).getMap();
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
	// show a edit option for existing tower's weapon  
	GuiManager guiManager = service
	    .getService(GuiManager.class);
	guiManager.setTower("editWeapon", tower);
	if(Gdx.graphics.getWidth() >= 800)
	guiManager.show("editWeapon", service,
	    (int) Vector3.tmp.x, (int) Vector3.tmp.y, i, j);
	else
	    guiManager.show("editWeapon", service,
		    0, 0, i, j);
	Player.towerRange.visible = true;
	Player.towerRange.setRange(tower.getWeapon().getRange());
	Player.towerRange.setX((int) tower.getCenterX());
	Player.towerRange.setY((int) tower.getCenterY());
    }
}
