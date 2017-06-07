package com.gdxengine.framework.material3d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class BaseMaterial {

	public BaseMaterial(Texture texture, Vector2 uv)
	{
		setTexture(texture);
		setUV(uv);
	}
	
	private Vector2 uv;
	private Texture texture;
	
	public Vector2 getUV() {
		return uv;
	}
	public void setUV(Vector2 uv) {
		this.uv = uv;
	}
	public Texture getTexture() {
		return texture;
	}
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
	
	
}
