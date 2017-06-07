package com.gdxengine.framework.test.towerdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.AnimatedSprite;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.event.TouchEvent;
import com.gdxengine.framework.event.listener.TouchListener;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IService;
import com.gdxengine.framework.scene.TileRenderer;
import com.gdxengine.framework.scene.TiledScene;
import com.gdxengine.framework.test.towerdefense.gui.GuiManager;

public class Player extends ObjectCollection<Tower> implements IService,
	TouchListener {

    public static BuildType buildType = BuildType.None;
    static TextureRegion prerenderTexture;
    static int preBuildX = -200;
    static int preBuildY = -500;
    public static TowerRangeDrawer towerRange = new TowerRangeDrawer();

    public int gold = 1000;
    public int lives = 20;
    TileRenderer tileRender;
    TiledMap map;

    public boolean isLose() {
	return lives <= 0;
    }

    public Player(IGameService services) {
	super(services);
	// TODO Auto-generated constructor stub
    }

    @Override
    public void initialize() {
	tileRender = services.getService(TileRenderer.class);
	map = tileRender.getMap();
	super.initialize();
    }

    @Override
    public void update(float gameTime) {

	super.update(gameTime);
    }

    public void renderTowerRange() {
	if (towerRange.visible)
	    towerRange.draw(services.getCamera());
    }

    @Override
    public void render(float gameTime) {
	// render the towers
	for (Tower tower : objectCollection) {
	    tower.render(gameTime);
	}
	// render the weapon's towers
	for (Tower tower : objectCollection) {
	    tower.renderWeapon(gameTime);
	}

	if (buildType != BuildType.None) {

	    services.drawTextureRegion(prerenderTexture, preBuildX, preBuildY);
	}
    }

    @Override
    public Rectangle getBoundingRectangle() {

	return new Rectangle(0, 0, map.tileWidth * map.width,
		map.tileHeight * map.height);
    }

    @Override
    public boolean onTouchUp(TouchEvent e) {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean onTouchDown(TouchEvent e) {

	towerRange.visible = false;

	int i = e.getX();
	int j = e.getY();
	// Get the size from tile map
	i = i / map.tileWidth;
	j = j / map.tileHeight;
	int row = (int)(tileRender.getMapHeightUnits()- e.getY())/map.tileHeight;
	//cannot build anything in the "noplace" tiles
	String str = map.getTileProperty(map.layers.get(0).tiles[row][i], "noplace");
	
	if ("1".equals(str))
	{
		hidePreBuild();
		GuiManager gui= services.getService(GuiManager.class);
		gui.hide();
		return true;
	}
	
	// get the size from world
	i = i * map.tileWidth;
	j = j * map.tileHeight;

	Vector3.tmp.set(i + map.tileWidth, j + map.tileHeight / 2, 0);
	services.getCamera().project(Vector3.tmp);

	// check a tower is existing there...
	for (Tower t : objectCollection) {
	    if (t.getX() == i && t.getY() == j) {
		if (t.hasWeapon()) {

		    buildType = BuildType.None;

		    towerRange.visible = true;
		    towerRange.setRange(t.getWeapon().getRange());
		    towerRange.setX((int) t.getCenterX());
		    towerRange.setY((int) t.getCenterY());

		    // show a edit option for existing tower's weapon
		    GuiManager guiManager = services
			    .getService(GuiManager.class);
		    guiManager.setTower("editWeapon", t);
		    if (Gdx.graphics.getWidth() >= 800)
			guiManager.show("editWeapon", getGameService(),
				(int) Vector3.tmp.x, (int) Vector3.tmp.y, i, j);
		    else
			guiManager.show("editWeapon", getGameService(), 0, 0,
				i, j);

		    return true;

		} else {
		    // show a option for build a new tower
		    GuiManager guiManager = services
			    .getService(GuiManager.class);
		    guiManager.setTower("addWeapon", t);
		    if (Gdx.graphics.getWidth() >= 800)
			guiManager.show("addWeapon", getGameService(),
				(int) Vector3.tmp.x, (int) Vector3.tmp.y, i, j);
		    else
			guiManager.show("addWeapon", getGameService(),
				0, 0, i, j);
			// show the pre build
			setPreBuild(BuildType.None, i, j);
		    return true;
		}
	    }
	}
	if (towerRange.visible) {
	    hidePreBuild();
	    return true;
	}
	
	// show a option for build a new tower
	GuiManager guiManager = services.getService(GuiManager.class);
	if (Gdx.graphics.getWidth() >= 800)
	    guiManager.show("buildTower", getGameService(),
		    (int) Vector3.tmp.x, (int) Vector3.tmp.y, i, j);
	else
	    guiManager.show("buildTower", getGameService(),
		    0, 0, i, j);
	// show the pre build
	setPreBuild(BuildType.Tower, i, j);

	return true;
    }

    public enum BuildType {
	Tower, None
    }

    public static void hidePreBuild() {
	buildType = BuildType.None;
	towerRange.visible = false;
    }

    public static void setPreBuild(BuildType type, int x, int y) {
	preBuildX = x;
	preBuildY = y;
	buildType = type;
	if (type == BuildType.Tower) {
	    if (prerenderTexture == null)
		prerenderTexture = new TextureRegion(GameAsset.towerTexture);
	}
    }

    @Override
    public boolean onTouchDragged(TouchEvent e) {
	GuiManager guiManager = services.getService(GuiManager.class);
	guiManager.hide();
	Player.hidePreBuild();
	return false;
    }

}
