/**
 * 
 */
package com.gdxengine.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

/**
 * 
 * @author Vinh
 *
 */
public class ImageComponent extends Image {

	/**
	 * 
	 */
	public ImageComponent() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param patch
	 */
	public ImageComponent(NinePatch patch) {
		super(patch);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param region
	 */
	public ImageComponent(TextureRegion region) {
		super(region);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param texture
	 */
	public ImageComponent(Texture texture) {
		super(texture);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param drawable
	 */
	public ImageComponent(Drawable drawable) {
		super(drawable);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param skin
	 * @param drawableName
	 */
	public ImageComponent(Skin skin, String drawableName) {
		super(skin, drawableName);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param drawable
	 * @param scaling
	 */
	public ImageComponent(Drawable drawable, Scaling scaling) {
		super(drawable, scaling);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param drawable
	 * @param scaling
	 * @param align
	 */
	public ImageComponent(Drawable drawable, Scaling scaling, int align) {
		super(drawable, scaling, align);
		// TODO Auto-generated constructor stub
	}

}
