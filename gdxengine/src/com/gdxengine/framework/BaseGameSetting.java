package com.gdxengine.framework;

import java.io.Externalizable;
import java.io.Serializable;

/**
 * create, load, get and modify setting of game easily
 * You can save your game level, game score, etc... by settings in this class
 * 
 * @author Vinh
 *
 */
public abstract class BaseGameSetting {
	
	public static String savefile = ".gamedata";
	/**
	 * Make game enable sound or not
	 */
	public static boolean isPlaySound = true;
	/**
	 * Make game enable audio or not
	 */
	public static boolean isPlayAudio = true;
	
	/**
	 * Load game setting information from file that have saved in hard disk
	 */
	public abstract void loadSetting();
	/**
	 * Save game setting information to file that have saved in hard disk or the file will be create
	 * if it doesn't exists.
	 */
	public abstract void saveSetting();
	/**
	 * Modify the value of a game setting, by its key.
	 * @param key Key (Index) of setting.
	 * @param value New value of setting. it will override the old value.
	 */
	public abstract void modifySetting(int key, Object value);
	/**
	 * Get value of game setting by their key (Index)
	 * @param key Key(Index) of game setting
	 * @return the Value
	 */
	public abstract Object getSetting(int key);
	
	/**save game setting information to file that have saved in hard disk or the file will be create
	 * save Serializable data
	 * @param data the Data
	 */
	public void saveSetting(Serializable data) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Load game setting information from file that have saved in hard disk
	 * load from Serializabled Data
	 * @param <T> the type of saved game data
	 */
	public abstract <T> T loadSetting(Class<T> classType);
	/**save game setting information to file that have saved in hard disk or the file will be create
	 * save Serializable data
	 * @param data the Data
	 */
	public void saveSetting(Externalizable  dataObject) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Load game setting information from file that have saved in hard disk
	 * load from Serializabled Data
	 * @param <T> the type of saved game data
	 */
	public abstract void loadSetting(Externalizable  dataObject);

}
