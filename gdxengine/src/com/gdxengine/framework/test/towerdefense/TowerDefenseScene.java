package com.gdxengine.framework.test.towerdefense;

import java.util.ArrayDeque;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
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
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.DefaultGameAsset;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.TiledScene;
import com.gdxengine.framework.test.towerdefense.Monster.Direction;
import com.gdxengine.framework.test.towerdefense.Player.BuildType;
import com.gdxengine.framework.test.towerdefense.gui.GuiManager;
import com.gdxengine.framework.test.towerdefense.gui.GameDialog;
import com.gdxengine.framework.test.towerdefense.weapon.Gun;
import com.gdxengine.framework.ui.ITabContent;
import com.gdxengine.framework.ui.TabPane;

public class TowerDefenseScene extends TiledScene {

    public static GameDialog gameDialog;
    public static final Animation monsterAnimation = new Animation(true, .12f,
	    4);;
    public static ArrayDeque<Path> path;

    TiledLayer noPass;
    private BitmapFont bigFont;
    Label fpsLabel;

    private Texture texture;
    private Level level;
    private MonsterHealthyDrawer monsterHealthyDrawer;
    private Player player;
    private Tower t;

    private GuiManager guiManager;
    private Label lblPlayerGold;
    private Label lblPlayerLives;
    public TextButton btnStartWave;

    public TowerDefenseScene(IGameService gameService) {
	super(gameService, false);
    }

    @Override
    public void initialize() {
	clear();
	monsterHealthyDrawer = new MonsterHealthyDrawer();
	loadTileMapRenderer("towerdefense/map.tmx", "towerdefense");
	setupCamera();
	setupUIManager("data/skin/uiskin.json");
	uiManager.setMenuVisible(false);
	path = new ArrayDeque<Path>();
	for (TiledObjectGroup grp : tiledMap.objectGroups) {
	    if ("path".equals(grp.name)) {
		for (TiledObject obj : grp.objects) {
		    Path p = new Path();
		    if ("down".equals(obj.type))
			p.setDirection(Direction.Down);
		    else if ("up".equals(obj.type))
			p.setDirection(Direction.Up);
		    else if ("right".equals(obj.type))
			p.setDirection(Direction.Right);
		    else if ("left".equals(obj.type))
			p.setDirection(Direction.Left);

		    p.x = obj.x - Monster.REGION_WIDTH / 2f;
		    p.y = getMapHeight() - obj.y - Monster.REGION_HEIGHT / 2f;

		    path.push(p);
		}
	    }
	}

	player = new Player(services);
	addDrawbleObject(player);
	services.addService(player);
	addInputProcessor(player);

	level = new Level(getGameService());
	addDrawbleObject(level);
	services.addService(level);

	guiManager = new GuiManager(skin, uiManager);
	services.addService(guiManager);

	t = new Tower(services);
	t.setPosition(32 * 15, 32 * 8);
	// t.setOrigin(45f, 67.5f);
	player.addItem(t);
	super.initialize();
	Gun g = new Gun(services, t);
	g.setPosition(32 * 14.5f, 32 * 7.5f);
	t.setWeapon(g);
    }

    @Override
    public void update(float gameTime) {
	
	if(gameDialog.isVisible())
	    return;
	
	lblPlayerGold.setText("Gold: " + player.gold);
	lblPlayerLives.setText("Lives: " + player.lives);
	super.update(gameTime);
    }

    @Override
    public void render(float gameTime) {
	super.render(gameTime);
    }

    @Override
    protected boolean isDragScreenEnable() {
	if (Player.buildType == BuildType.None)
	    return true;
	else
	    return true;
    }

    @Override
    public void renderUIandFG(float gameTime) {
	monsterHealthyDrawer.draw(level.getCurrentWave().getObjectCollection(),
		mainCamera);
	uiBatch.begin();
	onRenderForeground(gameTime);
	uiManager.draw();
	uiBatch.end();

    }

    @Override
    public void onRenderForeground(float gameTime) {

    }

    @Override
    public void setupUIManager(String skinPath) {

	// create new uiManager and skin
	super.setupUIManager(skinPath);
	// after we have uiManager, skin, uiCamera...
	// Add some stuffs to uiManager
	btnStartWave = new TextButton("Unleash Monster!", skin);
	btnStartWave.setWidth(150);
	btnStartWave.setPosition(
		Gdx.graphics.getWidth() - btnStartWave.getWidth(), 0);
	btnStartWave.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {

		level.setEnabled(!level.isEnabled());
		player.setEnabled(level.isEnabled());
		if (level.isEnabled()) {
		    btnStartWave.setText("Pause Game");
		} else {
		    btnStartWave.setText("Unleash Monsters!");
		}
	    }
	});

	uiManager.addActor(btnStartWave);

	lblPlayerGold = new Label("Gold: ", skin);
	lblPlayerGold.setColor(Color.CYAN);
	lblPlayerGold.setPosition(0,
		Gdx.graphics.getHeight() - lblPlayerGold.getHeight());
	uiManager.addActor(lblPlayerGold);

	lblPlayerLives = new Label("Gold: ", skin);
	lblPlayerLives.setColor(Color.CYAN);
	lblPlayerLives.setPosition(
		Gdx.graphics.getWidth() - btnStartWave.getWidth(),
		Gdx.graphics.getHeight() - lblPlayerLives.getHeight());
	uiManager.addActor(lblPlayerLives);

	gameDialog = new GameDialog(skin){
	    
	    @Override
	    public void restartGame() {
		initialize();
	    }
	};
	gameDialog.setVisible(false);
	uiManager.addActor(gameDialog);
    }

    public void renderTowerRange() {
	player.renderTowerRange();
    }

    public Camera getCameraUI() {
	// TODO Auto-generated method stub
	return uiManager.getCamera();
    }
}
