package com.gdxengine.framework;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;

public class DefaultGameSetting extends BaseGameSetting {
	@Override
	public void loadSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveSetting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifySetting(int key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getSetting(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveSetting(Serializable data) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(Gdx.files.external(savefile).file(),false);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(data);

		} catch (Throwable e) {
		} finally {
			try {
				if (oos != null) oos.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
			}
		}
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public <T> T loadSetting(Class<T> classType) {
		FileInputStream fos = null;
		ObjectInputStream oos = null;
		try {
			fos = new FileInputStream(Gdx.files.external(savefile).file());
			oos = new ObjectInputStream(fos);
			return	(T) oos.readObject();

		} catch (Throwable e) {
			return null;
		} finally {
			try {
				if (oos != null) oos.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	public void saveSetting(Externalizable dataObject) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(Gdx.files.external(savefile).file(),true);
			oos = new ObjectOutputStream(fos);
			dataObject.writeExternal(oos);

		} catch (Throwable e) {
		} finally {
			try {
				if (oos != null) oos.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public void loadSetting(Externalizable dataObject) {
		FileInputStream fos = null;
		ObjectInputStream oos = null;
		try {
			fos = new FileInputStream(Gdx.files.external(savefile).file());
			oos = new ObjectInputStream(fos);
			dataObject.readExternal(oos);

		} catch (Throwable e) {

		} finally {
			try {
				if (oos != null) oos.close();
				if (fos != null) fos.close();
			} catch (IOException e) {
			}
		}
	}

}
