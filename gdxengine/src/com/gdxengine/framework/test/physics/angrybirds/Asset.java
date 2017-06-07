package com.gdxengine.framework.test.physics.angrybirds;


import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxengine.framework.DefaultGameAsset;


public class Asset extends DefaultGameAsset {
	
		//game asset
		public static Texture longwood;
		public static Texture tallwood;
		public static Texture smallblock;
		public static Texture block;
		public static TextureRegion bird;
		public static Texture pig;
		public static BitmapFont font;
		
		public static TextureRegion sling;
		public static Texture background;

		public static Texture damaged_longwood;
		public static Texture damaged_tallwood;
		public static Texture damaged_smallblock;
		public static Texture damaged_block;
	
	@Override
	public void load() {
		
		if(isLoaded())
			return;
		
		bird = loadTextureRegion("angrybirds/sprites/bird.png");
		pig = loadTexture("angrybirds/sprites/pig.png");
		sling = loadTextureRegion("angrybirds/sprites/sling.png");
		block = loadTexture("angrybirds/sprites/woods/block.gif");
		longwood = loadTexture("angrybirds/sprites/woods/long.gif");
		smallblock = loadTexture("angrybirds/sprites/woods/small.gif");
		tallwood = loadTexture("angrybirds/sprites/woods/tall.gif");
		background = loadTexture("bg.jpg", Format.RGB565, true);
		font = loadBitmapFont("default.fnt", "default.png");
		
		damaged_block = loadTexture("angrybirds/sprites/damaged_woods/block.gif");
		damaged_longwood = loadTexture("angrybirds/sprites/damaged_woods/long.gif");
		damaged_smallblock = loadTexture("angrybirds/sprites/damaged_woods/small.gif");
		damaged_tallwood = loadTexture("angrybirds/sprites/damaged_woods/tall.gif");
		
		setLoaded(true);
	}

	

	@Override
	public void dispose() {

	}
}
