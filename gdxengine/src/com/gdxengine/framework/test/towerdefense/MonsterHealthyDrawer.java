package com.gdxengine.framework.test.towerdefense;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MonsterHealthyDrawer {
    
    public static final int BAR_WIDTH = 30;
    public static final int BAR_HEIGHT = 2;
    
    ShapeRenderer render = new ShapeRenderer();
    
    public MonsterHealthyDrawer()
    {
    }
    
    public void draw(ArrayList<Monster> monsters, Camera camera){
	render.setProjectionMatrix(camera.combined);
	render.begin(ShapeType.FilledRectangle);
	for(Monster m : monsters){
	    //draw only the damaged monster
	    if(m.getHealthyRate() < 1f){
		render.setColor(Color.RED);
		render.filledRect(m.getX() + 5, m.getY() + m.getHeight() + 5, BAR_WIDTH, BAR_HEIGHT);
		render.setColor(Color.GREEN);
		render.filledRect(m.getX() + 5, m.getY() + m.getHeight() + 5, BAR_WIDTH * m.getHealthyRate(), BAR_HEIGHT);
	    }
	}
	render.end();
    }
}
