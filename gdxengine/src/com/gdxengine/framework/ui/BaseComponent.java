package com.gdxengine.framework.ui;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.CollectionItem;
import com.gdxengine.framework.DrawableGameComponent;
import com.gdxengine.framework.GameComponent;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.UIComponent;

public abstract class BaseComponent extends CollectionItem implements UIComponent {

	public final Vector2 position = new Vector2();
	public final Vector2 size = new Vector2();
	public final Rectangle bound = new Rectangle();

	public BaseComponent() {
		// TODO Auto-generated constructor stub
	}	
	/* (non-Javadoc)
	 * @see com.gdxengine.framework.ui2d.UIConponent#getContent()
	 */
	@Override
	public abstract Object getContent();
	/* (non-Javadoc)
	 * @see com.gdxengine.framework.ui2d.UIConponent#getBackground()
	 */
	@Override
	public abstract Object getBackground();
	/* (non-Javadoc)
	 * @see com.gdxengine.framework.ui2d.UIConponent#setupBound()
	 */
	@Override
	public abstract void setupLayoutContent();
	/* (non-Javadoc)
	 * @see com.gdxengine.framework.ui2d.UIConponent#getBound()
	 */
	@Override
	public abstract Rectangle getBound();
}
