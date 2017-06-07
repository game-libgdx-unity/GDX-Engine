package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;


/**
 * Interface for game asset
 * @author Vinh
 * 
 */
public interface IGameAsset {
	
	/**
	 * abstract method for load all game's resources in startup time.
	 */
	public void load();

	/**
	 * Dispose the resources in game asset when exiting game
	 */
	public void dispose();
	
	/**
	 * load a bitmap font by font file (*.fnt) and font image file
	 * @param fileFont font
	 * @param fileImage font image
	 * @return
	 */
	public BitmapFont loadBitmapFont(String fileFont, String fileImage);
	/**
	 * Load a Music by filepath name
	 * @param fileName music filepath to load.
	 * @param isLoop set audio is looping
	 * @param volumn the Volumn (0f -> 1f)
	 * @return the Music
	 */
	public Music loadMusic(String fileName, boolean isLoop, float volumn);
	/**
	 * Load Music by filepath name
	 * @param fileName music filepath to load.
	 * @return
	 */
	public Music loadMusic(String fileName);
	/**
	 * Load a Music by filepath name
	 * @param fileName music filepath to load.
	 * @param volumn the Volumn (0f -> 1f)
	 * @return
	 */
	public Music loadMusic(String fileName, float volume);
	/**
	 * Load a Sound from filepath
	 * @param file File to load
	 * @return the Sound
	 */
	public Sound loadSound(String file);

	/**
	 * Load a shader from shader-vs and shader-fs from file path
	 * @param vsProcess
	 * @param fsProcess
	 * @return ShaderProgram
	 */
	public ShaderProgram loadShader(String vsProcess, String fsProcess);

	/**
	 * Load a Model for object 3D from file path
	 * @param filepath
	 * @return
	 */
	public StillModel loadModel(String filepath);
	/**
	 * Load texture from file path
	 * @param file File to load
	 * @return The texture
	 */
	public Texture loadTexture(String file, Format format, boolean useMipMap);

//	/**abstract method for load TextureRegion
//	 * @param filepath  File to load
//	 * @param format format of texture
//	 * @param useMipMap
//	 * @param rec 
//	 * @return
//	 */
//	TextureRegion loadTextureRegion(String filepath, Format format,
//			boolean useMipMap, Rectangle rec);

	/**abstract method for load TextureRegion
	 * @param texture texture of TextureRegion
	 * @param rec rectangle used to specify the region on texture
	 * @return
	 */
	public TextureRegion loadTextureRegion(Texture texture, Rectangle rec);
	/**
	 * load a bitmap font by font file (*.fnt) and font image file
	 * @param fileFont font
	 * @param fileImage font image
	 * @param flip Flip the font for axis-y down
	 * @return
	 */
	public BitmapFont loadBitmapFont(String fileFont, String fileImage, boolean flip);

	public TextureRegion loadTextureRegion(Texture t);

	TextureRegion loadTextureRegion(String textureFile, Rectangle rec);

	TextureRegion loadTextureRegion(String textureFile);

	Texture loadTexture(String filepath);

	BitmapFont loadBitmapFont(String fileFont);

	ShaderProgram loadShader(String vsFilePath);
}
