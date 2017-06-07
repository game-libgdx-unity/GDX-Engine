package com.gdxengine.framework;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IGameAsset;

/**
 * This class allow you to load resources quickly. All you should do with
 * AssetLoader is: 1. call the setGameAsset(), pass the asset from game services
 * 2. Call the load() and pass the Class of resource you want to load. calling
 * the setGameAsset() is optional.
 * 
 * @author Vinh
 * 
 */
public final class AssetLoader {

	static IGameAsset gameAsset;
	static Class type = null;
	private AssetLoader() {

	}

	/**
	 * Set the game asset
	 * 
	 * @param asset
	 */
	public static void setGameAsset(IGameAsset asset) {
		gameAsset = asset;
	}
	/**
	 * Set the class of asset
	 * 
	 * @param asset
	 */
	public static void setAssetClass(Class type) {
		AssetLoader.type = type;
	}

	@SuppressWarnings("unchecked")
	public static <T> T load(String filepath,
			Object... parameters) {
		
		if(AssetLoader.type == null){
			throw new GdxRuntimeException("setAssetClass() must be called before you call this method");
		}
		
		return AssetLoader.<T>load(AssetLoader.type, filepath, parameters);
	}
	@SuppressWarnings("unchecked")
	public static <T> T load(Class<T> type, String filepath,
			Object... parameters) {
		T output = null;
		setAssetClass(type);
		try {
			if (gameAsset == null)
				gameAsset = new DefaultGameAsset();
			if (type == Texture.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadTexture(filepath);
				else if (parameters.length == 1) {
					output = (T) gameAsset.loadTexture(filepath,
							(Format) parameters[0], true);
				} else if (parameters.length == 2) {
					output = (T) gameAsset.loadTexture(filepath,
							(Format) parameters[0], (Boolean) parameters[1]);
				} else {
					throw new GdxRuntimeException("Parameters is not valid!");
				}
			} else if (type == BitmapFont.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadBitmapFont(filepath);
				else if (parameters.length == 1) {
					output = (T) gameAsset.loadBitmapFont(filepath,
							(String) parameters[0]);
				} else {
					throw new GdxRuntimeException("Parameters is not valid!");
				}
			} else if (type == TextureRegion.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadTextureRegion(filepath);
				else if (parameters.length == 1) {
					output = (T) gameAsset.loadTextureRegion(filepath,
							(Rectangle) parameters[0]);
				} else if (parameters.length == 2) {
					output = (T) gameAsset.loadTextureRegion(
							(Texture) parameters[0], (Rectangle) parameters[1]);
				} else {
					throw new GdxRuntimeException("Parameters is not valid!");
				}
			} else if (type == Music.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadMusic(filepath);
				else if (parameters.length == 1) {
					output = (T) gameAsset.loadMusic(filepath,
							(Float) parameters[0]);
				} else {
					throw new GdxRuntimeException("Parameters is not valid!");
				}
			} else if (type == Audio.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadSound(filepath);
			} else if (type == ShaderProgram.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadShader(filepath);
				else if (parameters.length == 1) {
					output = (T) gameAsset.loadShader(filepath,
							(String) parameters[0]);
				} else {
					throw new GdxRuntimeException("Parameters is not valid!");
				}
			} else if (type == StillModel.class) {
				if (parameters.length == 0)
					output = (T) gameAsset.loadModel(filepath);
			}
		} catch (Exception ex) {
			throw new GdxRuntimeException("Invalid parameters you passed");
		}

		return output;
	}
}
