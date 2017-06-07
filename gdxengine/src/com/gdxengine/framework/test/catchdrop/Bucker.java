package com.gdxengine.framework.test.catchdrop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.DefaultSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.interfaces.IGameService;

public class Bucker extends DefaultSprite {

	public Bucker(IGameService services, TextureRegion region) {
		super(services, region);
		
	}

	//attribute for player game state
	public int score = 0;
	//store reference to DropCollection service.
	DropCollection drops;

	
	@Override
	public void initialize() {
		score = 0;
		setX(Gdx.graphics.getWidth()/2-getRegionWidth()/2f);
		setY(Gdx.graphics.getHeight()-getRegionHeight());
		
		drops = getGameService().getService(DropCollection.class);
	}
	@Override
	public void render(float gameTime) {
		super.render(gameTime);
		
		getGameService().drawText(Asset.bigFont, "Score: " + score, 300, getGameService().getWindowSize().y - 60);
	}
	@Override
	public void update(float gameTime) {

		float oldXPosition = getX();
		
		if(Utils.isKeyDown(Keys.RIGHT))
		{
			setX(getX() + 10) ;
		}
		
		if(Utils.isKeyDown(Keys.LEFT))
		{
			setX(getX() - 10);
		}
		
		final Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        getGameService().getCamera().unproject(touchPos);
        setX(touchPos.x - 64 / 2);
        //if bucker out of screen bound, restore its position
        if(getX() < 0 || getX() > Gdx.graphics.getWidth() - getRegionWidth() )
		{
			setX(oldXPosition);
		}
		
		for(Drop drop : drops)
		if(getBoundingRectangle().overlaps(drop.getBound()))
		{
			score++;
			drop.resetPosition();
		}
	}
}
