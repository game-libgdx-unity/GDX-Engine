package com.gdxengine.framework.test.catchdrop;


import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.gdxengine.framework.DefaultGameAsset;

public class Asset extends DefaultGameAsset {

	public static TextureRegion dropTexture;
	public static TextureRegion buckerTexture;
	public static BitmapFont bigFont;

	@Override
	public void load() {
		
		if(isLoaded())
			return;

		dropTexture = loadTextureRegion(loadTexture("dropcatch/drop.png", Format.RGBA4444, true), new Rectangle(0, 0, 32, 32));
		buckerTexture = loadTextureRegion(loadTexture("dropcatch/bucker.png", Format.RGBA4444, true), new Rectangle(0, 0, 64, 64));
		bigFont = loadBitmapFont("data/font16.fnt", "data/font16.png");
		
		setLoaded(true);
	}

	@Override
	public void dispose() {
		dropTexture.getTexture().dispose();
		buckerTexture.getTexture().dispose();
		bigFont.dispose();
	}
}
