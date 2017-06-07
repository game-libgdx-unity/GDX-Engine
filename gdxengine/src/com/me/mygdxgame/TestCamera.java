package com.me.mygdxgame;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.Game;

public class TestCamera implements ApplicationListener {

        static final int TARGET_WIDTH  = 800;
        static final int TARGET_HEIGHT = 480;
        
        static final int WORLD_WIDTH  = 1024;
        static final int WORLD_HEIGHT = 1024;
        
        private Vector3 start_position = new Vector3(0,0,0);

        private OrthographicCamera      cam;
        private Texture                         texture;
        private Mesh                            mesh;
        private BitmapFont	font;
        private float                           rotationSpeed;
        private SpriteBatch batch;
		private float scale;
		private float xScale;
		private float yScale;
        @Override
        public void create() {
        	
                rotationSpeed = 0.5f;
                mesh = new Mesh(true, 4, 6,
                                new VertexAttribute(VertexAttributes.Usage.Position, 3,"attr_Position"),
                                new VertexAttribute(Usage.TextureCoordinates, 2, "attr_texCoords"));
                texture = new Texture(Gdx.files.internal("data/Jellyfish.jpg"));
                mesh.setVertices(new float[] { 
                                 -1024f, -1024f, 0, 0, 1,
                                  1024f, -1024f, 0, 1, 1,
                                  1024f,  1024f, 0, 1, 0,
                                 -1024f,  1024f, 0, 0, 0
                });
                mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });

                cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());            
                xScale = Gdx.graphics.getWidth()/(float)TARGET_WIDTH;
                yScale = Gdx.graphics.getHeight()/(float)TARGET_HEIGHT;
                font = loadBitmapFont("default.fnt", "default.png");
                scale = Math.min(xScale, yScale);
                cam.zoom = 1/scale;
              //  cam.project(start_position);
                cam.position.set(0, 0, 0);
                batch = new SpriteBatch();
        }
        
        public Vector2 getMouseScreen(){
        	float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();
            
            Vector3 mouseOnWindows = new Vector3(x, y, 0);
            cam.unproject(mouseOnWindows);

            return new Vector2(mouseOnWindows.x, mouseOnWindows.y);
        }

        @Override
        public void render() {
                handleInput();
                GL10 gl = Gdx.graphics.getGL10();
                
                // Camera --------------------- /
                gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
                
                cam.update();
                cam.apply(gl);

                // Texturing --------------------- /
                gl.glActiveTexture(GL10.GL_TEXTURE0);
                gl.glEnable(GL10.GL_TEXTURE_2D);
                texture.bind();
                
                mesh.render(GL10.GL_TRIANGLES);
                
                Vector2 mouseOnScreen = getMouseScreen();

                batch.begin();
                font.draw(batch, "Mouse X: " + mouseOnScreen.x + " Mouse Y: "+mouseOnScreen.y, 0, font.getCapHeight());
                
//                x /= scale;
//                y /= scale;
//                
//                font.draw(batch, "Mouse X: " + x + " Mouse Y: "+y, 0, font.getCapHeight() * 2 + 10);
                batch.end();
        }
        
        public BitmapFont loadBitmapFont(String fileFont,String fileImage)
    	{
    		BitmapFont font = new BitmapFont(Gdx.files.internal(fileFont), Gdx.files.internal(fileImage), false);
    		return font;
    	}

        private void handleInput() {
                if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
                        cam.zoom += 0.02;
                }
                if(Gdx.input.isKeyPressed(Input.Keys.X)) {
                        cam.zoom -= 0.02;
                }
                if(Gdx.input.isKeyPressed(Input.Keys.A)) {
                        if (cam.position.x > -WORLD_WIDTH/2)
                                cam.translate(-10, 0, 0);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.D)) {
                        if (cam.position.x < WORLD_WIDTH/2)
                                cam.translate(10, 0, 0);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.S)) {
                        if (cam.position.y > -WORLD_HEIGHT/2)
                                cam.translate(0, -10, 0);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                        if (cam.position.y < WORLD_HEIGHT/2)
                                cam.translate(0, 10, 0);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.C)) {
                        cam.rotate(-rotationSpeed, 0, 0, 1);
                }
                if(Gdx.input.isKeyPressed(Input.Keys.V)) {
                        cam.rotate(rotationSpeed, 0, 0, 1);
                }
        }

        @Override
        public void resize(int width, int height) {
                // TODO Auto-generated method stub
        }

        @Override
        public void resume() {
                // TODO Auto-generated method stub
        }

        @Override
        public void dispose() {
                // TODO Auto-generated method stub
        }

        @Override
        public void pause() {
                // TODO Auto-generated method stub
        }
}