package com.gdxengine.framework.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Input.Keys;
import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.interfaces.IGameService;

public abstract class MenuManager<T extends IMenuItem> extends ObjectCollection<T> {
	
	protected int activeMenuItemIndex = 0;
	
	boolean needStop = false;
	float timer = 0f;
	
	public MenuManager(IGameService services,ArrayList<T> objectCollection)
	{
		super(services);
		
		this.objectCollection = objectCollection;
	}
	
	public MenuManager(IGameService services)
	{
		this(services, new ArrayList<T>());
	}
	
	public void addMenuItem(T item)
	{
		objectCollection.add(item);
	}

	@Override
	public void update(float gameTime) {
		
		if(needStop)
		{
			timer += gameTime;
			
			if(timer > 0.3f)
			{
				timer = 0f;
				needStop = false;
			}
			else
			{
				return;
			}
		}
		
		int baseIndex = activeMenuItemIndex;
		if(Utils.isKeyDown(Keys.DOWN))
		{
			baseIndex++;
			
			if(baseIndex >= objectCollection.size())
			{
				baseIndex = objectCollection.size()-1;
			}
			changeSelectedMenuIndex(baseIndex);
			needStop =true;
		}
		if(Utils.isKeyDown(Keys.UP))
		{
			baseIndex--;
			
			if(baseIndex < 0)
			{
				baseIndex = 0;
			}
			changeSelectedMenuIndex(baseIndex);
			needStop =true;
		}
		if(Utils.isKeyDown(Keys.ENTER))
		{
			//active when ENTER is pressed
			objectCollection.get(activeMenuItemIndex).onClick();
		}
	}
	
	/* abstract render method for derived menuManager class should be implement to customize render.
	 */
	@Override
	public abstract void render(float gameTime);
	
	public void changeSelectedMenuIndex(int newIndex)
	{
		objectCollection.get(activeMenuItemIndex).setSelect(false);
		activeMenuItemIndex = newIndex;
		objectCollection.get(activeMenuItemIndex).setSelect(true);
	}
}
