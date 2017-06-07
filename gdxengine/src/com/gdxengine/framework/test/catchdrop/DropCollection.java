package com.gdxengine.framework.test.catchdrop;

import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.interfaces.IActionPerformer;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IService;

public class DropCollection extends ObjectCollection<Drop> implements IService {

	private BaseSimpleAction action;
	public DropCollection(IGameService services) {
		super(services);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void initialize() {


		//create a action that will perform after each 5 seconds
		action = new BaseSimpleAction(false, 5f) {
			
			@Override
			public void onActionPerformance() {
				addItem(new Drop());
			}
		};
	}
	@Override
	public void update(float gameTime) {
	    action.update(gameTime);
	    super.update(gameTime);
	}
	@Override
	public void render(float gameTime) {
		for(Drop b : objectCollection)
		{
			if(b.isVisible())
			{
				getGameService().drawTextureRegion(Asset.dropTexture, b.position.x, b.position.y);
			}
		}
	}
	
}
