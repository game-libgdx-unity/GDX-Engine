package com.me.mygdxgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdxengine.framework.test.physics.angrybirds.AngryBirds;
import com.gdxengine.framework.test.towerdefense.TowerDefenseGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "gdxengine";
		cfg.useGL20 = true;
		cfg.width = 1024;
		cfg.height = 600;
		
		new LwjglApplication(new TowerDefenseGame(), cfg);
	}
}
