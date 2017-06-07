package com.gdxengine.framework.algorithm.astar;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.AssetLoader;

public class TestAStar implements ApplicationListener {

    IMap map = new MapTest();

    Pathfinder pathfinder;
    
    @Override
    public void create() {
	pathfinder = new Pathfinder(map);
	//get the path from 0,0 to 9,9 on MapTest
        ArrayList<Vector2> path = pathfinder.findPath(new Vector2(0, 0), new Vector2(9, 9));

        for (Vector2 point : path)
        {
            Gdx.app.log("Point: ",point.toString());
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void render() {
	Gdx.gl.glClearColor(1, 1, 1, 1);
	Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
