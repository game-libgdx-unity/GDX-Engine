package com.gdxengine.framework.scene;

import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.IntArray;
import com.gdxengine.framework.interfaces.IService;
/**
 * The same as TileMapRenderer from libgdx, the class implements IService
 * so it could be treat like as game service
 * @author Vinh
 *
 */
public class TileRenderer extends TileMapRenderer implements IService{

	public TileRenderer(int[][][] map, TileAtlas atlas, int tileWidth,
			int tileHeight, float unitsPerTileX, float unitsPerTileY,
			IntArray blendedTiles, int tilesPerBlockX, int tilesPerBlockY,
			ShaderProgram shader) {
		super(map, atlas, tileWidth, tileHeight, unitsPerTileX, unitsPerTileY,
				blendedTiles, tilesPerBlockX, tilesPerBlockY, shader);
		// TODO Auto-generated constructor stub
	}

	public TileRenderer(int[][][] map, TileAtlas atlas, int tileWidth,
			int tileHeight, float unitsPerTileX, float unitsPerTileY,
			IntArray blendedTiles, int tilesPerBlockX, int tilesPerBlockY) {
		super(map, atlas, tileWidth, tileHeight, unitsPerTileX, unitsPerTileY,
				blendedTiles, tilesPerBlockX, tilesPerBlockY);
		// TODO Auto-generated constructor stub
	}

	public TileRenderer(TiledMap map, TileAtlas atlas, int tilesPerBlockX,
			int tilesPerBlockY, float unitsPerTileX, float unitsPerTileY,
			ShaderProgram shader) {
		super(map, atlas, tilesPerBlockX, tilesPerBlockY, unitsPerTileX, unitsPerTileY,
				shader);
		// TODO Auto-generated constructor stub
	}

	public TileRenderer(TiledMap map, TileAtlas atlas, int tilesPerBlockX,
			int tilesPerBlockY, float unitsPerTileX, float unitsPerTileY) {
		super(map, atlas, tilesPerBlockX, tilesPerBlockY, unitsPerTileX, unitsPerTileY);
		// TODO Auto-generated constructor stub
	}

	public TileRenderer(TiledMap map, TileAtlas atlas, int tilesPerBlockX,
			int tilesPerBlockY, ShaderProgram shader) {
		super(map, atlas, tilesPerBlockX, tilesPerBlockY, shader);
		// TODO Auto-generated constructor stub
	}

	public TileRenderer(TiledMap map, TileAtlas atlas, int tilesPerBlockX,
			int tilesPerBlockY) {
		super(map, atlas, tilesPerBlockX, tilesPerBlockY);
		// TODO Auto-generated constructor stub
	}

}
