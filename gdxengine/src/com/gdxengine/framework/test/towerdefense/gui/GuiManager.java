package com.gdxengine.framework.test.towerdefense.gui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxengine.framework.BaseManager;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IService;
import com.gdxengine.framework.test.towerdefense.Player;
import com.gdxengine.framework.test.towerdefense.Tower;
import com.gdxengine.framework.ui.UIManager;

public class GuiManager extends BaseManager<String, BaseGUI> {

    BuildTowerGUI buildTowerGUI;
    BuildWeaponGUI buildWeaponGUI;
    EditWeaponGUI editTowerGUI;
    
    public GuiManager(Skin skin, UIManager manager) {
	super();
	
	buildTowerGUI = new BuildTowerGUI(skin);
	add("buildTower", buildTowerGUI);
	buildWeaponGUI = new BuildWeaponGUI(skin);
	add("addWeapon", buildWeaponGUI);
	
	editTowerGUI = new EditWeaponGUI(skin);
	add("editWeapon", editTowerGUI);
	
	manager.addActor(buildWeaponGUI);
	manager.addActor(buildTowerGUI);
	manager.addActor(editTowerGUI);
    }
    
    public void show(String key, IGameService service,int screenX, int screenY, int buildX, int buildY){
	
	for (BaseGUI gui : collection.values()) {
	    gui.hide();
	}
	BaseGUI gui = collection.get(key);
	gui.setScreen(screenX, screenY);
	gui.addListener(service, buildX, buildY);
	
	Player p = service.getService(Player.class);
	gui.setPlayer(p);
	gui.show();
    }
    public void setTower(String key,Tower tower)
    {
	collection.get(key).setTower(tower);
    }

    public void hide() {
	for (BaseGUI gui : collection.values()) {
	    gui.hide();
	}
    }
}
