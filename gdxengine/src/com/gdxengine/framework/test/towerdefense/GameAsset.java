package com.gdxengine.framework.test.towerdefense;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.DefaultGameAsset;

public class GameAsset extends DefaultGameAsset {

    public static Texture monsterTexture[] = new Texture[15];
    public static TextureRegion towerTexture;
    public static TextureRegion canon;
    public static TextureRegion gun;
    public static TextureRegion slower;
    public static BitmapFont font;
    
    public GameAsset() {
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public void load(){
	
	AssetLoader.setAssetClass(Texture.class);
	for(int i = 0; i < 15; i++){
	    monsterTexture[i] = AssetLoader.load("towerdefense/monster/monster ("+i+").png");
	}
	AssetLoader.setAssetClass(TextureRegion.class);
	canon = AssetLoader.load("towerdefense/tower/canon.png", new Rectangle(190, 0, 63, 63));
	towerTexture = AssetLoader.load("towerdefense/tower/tower.png");
	gun = AssetLoader.load("towerdefense/tower/gun.png", new Rectangle(190, 0, 63, 63));
	slower = AssetLoader.load("towerdefense/tower/slower.png", new Rectangle(190, 0, 63, 63));
    }
    @Override
    public void dispose(){
	for(int i = 0; i < 15; i++){
	    monsterTexture[i].dispose();
	}
	
	canon.getTexture().dispose();
	towerTexture.getTexture().dispose();
	gun.getTexture().dispose();
	slower.getTexture().dispose();
    }
}
