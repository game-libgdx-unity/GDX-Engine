package com.gdxengine.framework.test.towerdefense;

import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IService;
import com.gdxengine.framework.test.towerdefense.gui.GuiManager;

public class Level extends ObjectCollection<Wave> implements IService {

    public static final int MAX_INDEX = 14;
    int index = 0;
    boolean playerWin = false;

    public Level(IGameService services) {
	super(services);
	addItem(new Wave(getGameService(), 0));
	setEnabled(false);
	TowerDefenseScene.gameDialog.build(0);
    }

    @Override
    public void render(float gameTime) {

	getCurrentWave().render(gameTime);
    }

    @Override
    public void update(float gameTime) {
	getCurrentWave().update(gameTime);
    }

    public Wave getCurrentWave() {
	return get(index);
    }

    public void nextWave() {
	GuiManager gui;
	index++;
	if (index > MAX_INDEX) {
	    playerWin = true;
	    TowerDefenseScene.gameDialog.build(index);
	    Player.hidePreBuild();
	    gui = services.getService(GuiManager.class);
	    gui.hide();
	    return;
	}
	
	TowerDefenseScene scene = services
		    .getScene(TowerDefenseScene.class);
	    scene.btnStartWave.setText(" Unleash Monsters! ");
	    setEnabled(false);
	    TowerDefenseScene.gameDialog.build(index);
	    Player.hidePreBuild();
	    gui = services.getService(GuiManager.class);
	    gui.hide();

	Wave w = new Wave(getGameService(), index);
	w.initialize();
	addItem(w);

	Player.hidePreBuild();
	gui = services.getService(GuiManager.class);
	gui.hide();
    }
}
