package com.gdxengine.framework.test.catchdrop;

import com.gdxengine.framework.DefaultGameSetting;
import com.gdxengine.framework.Game;
import com.gdxengine.framework.BaseGameSetting;
import com.gdxengine.framework.interfaces.IGameAsset;
import com.gdxengine.framework.interfaces.IScene;

public class CatchDropGame extends Game {

	@Override
	public void create() {		
		//set the Oxy axis system have axis-y down
		setYdown(true);
		//initialize settings and assets
		BaseGameSetting baseGameSetting = new DefaultGameSetting();
		IGameAsset asset = new Asset();
		
		//initialize game service for all scene can use later
		initializeGameService(asset, baseGameSetting);
		
		//initialize scene(s) and add them to scenes collection
		IScene scene = new GamePlayScene(getGameService());
		addScene(scene);
		//Active the beginning scene, initialize first active scene
		getGameService().changeScene(GamePlayScene.class);
	}
}
