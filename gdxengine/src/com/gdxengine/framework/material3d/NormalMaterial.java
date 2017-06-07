package com.gdxengine.framework.material3d;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class NormalMaterial extends BaseMaterial {
	
	Texture normalMap;

	public NormalMaterial(Texture texture, Vector2 uv,Texture normalMap) {
		super(texture, uv);
		
		this.normalMap = normalMap;
		// TODO Auto-generated constructor stub
	}

}
