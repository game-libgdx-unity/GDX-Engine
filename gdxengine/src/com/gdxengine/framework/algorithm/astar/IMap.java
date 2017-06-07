package com.gdxengine.framework.algorithm.astar;
/**
 * Interface for a tiled map that could be used with the A* algorithm
 * @author Vinh
 *
 */
public interface IMap {

    /**
     * Get the width of tile on tiled map
     * 
     * @return
     */
    public abstract int getTileWidth();

    /**
     * Get the height of tile on tiled map
     * 
     * @return
     */
    public abstract int getTileHeight();

    /**
     * Get the width of tiled map
     * 
     * @return
     */
    public abstract int getWidth();

    /**
     * get the height of tiled map
     * 
     * @return
     */
    public abstract int getHeight();

    /**
     * Returns the tile index for the given cell.
     */
    public abstract int getIndex(int cellX, int cellY);

}