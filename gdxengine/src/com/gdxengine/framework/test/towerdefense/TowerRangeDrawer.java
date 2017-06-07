package com.gdxengine.framework.test.towerdefense;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TowerRangeDrawer {
    ShapeRenderer render = new ShapeRenderer();
    private int range, x, y;
    public boolean visible = false;
    
    public TowerRangeDrawer()
    {
	
    }
    
    public void draw(Camera camera){
	render.setProjectionMatrix(camera.combined);
	render.setColor(new Color(0f, 1f, 0f, .1f));
	render.begin(ShapeType.FilledCircle);
	render.filledCircle(x, y, range, 30);
	render.end();
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
