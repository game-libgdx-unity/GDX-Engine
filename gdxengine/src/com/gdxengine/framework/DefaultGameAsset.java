package com.gdxengine.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.loaders.ModelLoaderRegistry;
import com.badlogic.gdx.graphics.g3d.model.still.StillModel;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IGameAsset;

/**
 * The standard class that implement the IGameAsset interface
 * You can use the class to load resource such as Texture, Music, Font ...
 * @author Vinh
 *
 */
public class DefaultGameAsset implements IGameAsset {

	private boolean isLoaded = false;
	@Override
	public Texture loadTexture(String filepath, Format format, boolean useMipMap)
	{
		Texture texture = new Texture(Gdx.files.internal(filepath), format, useMipMap);
		texture.setFilter(TextureFilter.MipMap, TextureFilter.Linear);
		return texture;
	}
	@Override
	public Texture loadTexture(String filepath) {
		return loadTexture(filepath, Format.RGBA4444, true);
	}
	
	@Override
	public TextureRegion loadTextureRegion(String textureFile)
	{
		Texture texture = loadTexture(textureFile, Format.RGBA4444, true);
		TextureRegion tr = new TextureRegion(texture);
		//flip for system of y-axis down
		if(Game.isYdown())
			tr.flip(false, true);
		return tr;
	}
	@Override
	public TextureRegion loadTextureRegion(String textureFile, Rectangle rec)
	{
		Texture texture = loadTexture(textureFile, Format.RGBA4444, true);
		TextureRegion tr = new TextureRegion(texture, (int)rec.x, (int)rec.y, (int)rec.width, (int)rec.height);
		//flip for system of y-axis down
		if(Game.isYdown())
			tr.flip(false, true);
		return tr;
	}
	@Override
	public TextureRegion loadTextureRegion(Texture texture, Rectangle rec)
	{
		TextureRegion tr = new TextureRegion(texture, (int)rec.x, (int)rec.y, (int)rec.width, (int)rec.height);
		//flip for system of y-axis down
		if(Game.isYdown())
			tr.flip(false, true);
		return tr;
	}
	
	@Override
	public Sound loadSound(String file)
	{
		return Gdx.audio.newSound(Gdx.files.internal(file));
	}
	@Override
	public BitmapFont loadBitmapFont(String fileFont)
	{
		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFont), Game.isYdown());
		return font;
	}
	@Override
	public BitmapFont loadBitmapFont(String fileFont,String fileImage)
	{
		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFont), Gdx.files.internal(fileImage), Game.isYdown());
		return font;
	}
	@Override
	public BitmapFont loadBitmapFont(String fileFont,String fileImage,boolean flip)
	{
		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFont), Gdx.files.internal(fileImage), flip);
		return font;
	}
	@Override
	public Music loadMusic(String fileName,boolean isLoop, float volume)
	{
		Music music = Gdx.audio.newMusic(Gdx.files.internal(fileName));
		music.setLooping(true);
		music.setVolume(0.5f);
		return music;
	}
	@Override
	public Music loadMusic(String fileName)
	{
		return loadMusic(fileName, true, 1f);
	}
	@Override
	public Music loadMusic(String fileName,float volume)
	{
		return loadMusic(fileName, true, volume);
	}
	
	@Override
	public ShaderProgram loadShader(String vsProcess,String fsProcess)
	{
		if(!Game.isSupportOpenGL20)
			return null;
		
		ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vsProcess),
				Gdx.files.internal(fsProcess));
		
		if(shader.isCompiled())
			return shader;
		else
			throw new GdxRuntimeException("Cannot compile the shader");
	}
	
	@Override
	public ShaderProgram loadShader(String vsFilePath)
	{
		if(!Game.isSupportOpenGL20)
			return null;
		
		String fsProcess = vsFilePath.substring(0, vsFilePath.length()-7);
		fsProcess += "fs.glsl";
		ShaderProgram shader = new ShaderProgram(Gdx.files.internal(vsFilePath),
				Gdx.files.internal(fsProcess));
		
		if(shader.isCompiled())
			return shader;
		else
			throw new GdxRuntimeException("Cannot compile the shader");
	}
	
	@Override
	public StillModel loadModel(String filepath) {
		
		return ModelLoaderRegistry.loadStillModel(Gdx.files.internal(filepath));
	}
	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TextureRegion loadTextureRegion(Texture t) {
		// TODO Auto-generated method stub
		return loadTextureRegion(t, new Rectangle(0, 0, t.getWidth(), t.getHeight()))
				;
	}
	/**
	 * @return the isLoaded
	 */
	protected boolean isLoaded() {
		return isLoaded;
	}
	/**
	 * @param isLoaded the isLoaded to set
	 */
	protected void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
}
