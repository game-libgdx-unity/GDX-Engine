package com.gdxengine.framework.algorithm.astar;

public class MapTest implements IMap
{
    private int[][] layout = new int[][]
    {
        { 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, }, 
        { 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, },
        { 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, }, 
        { 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, }, 
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
        { 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, }, 
        { 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, }, 
        { 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, }, 
        { 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, }, 
        { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, }, 
    };

  
    /* (non-Javadoc)
     * @see com.gdxengine.framework.althosim.astar.IMap#getWidth()
     */
    @Override
    public int getWidth()
    {
        return layout[0].length;
    }
    /* (non-Javadoc)
     * @see com.gdxengine.framework.althosim.astar.IMap#getHeight()
     */
    @Override
    public int getHeight()
    {
	return layout.length;
    }


    /// <summary>
    /// Returns the tile index for the given cell.
    /// </summary>
    /* (non-Javadoc)
     * @see com.gdxengine.framework.althosim.astar.IMap#GetIndex(int, int)
     */
    @Override
    public int getIndex(int cellX, int cellY)
    {
        if (cellX < 0 || cellX > getWidth() - 1 || cellY < 0 || cellY > getHeight() - 1)
            return 0;

        return layout[cellY][ cellX];
    }
    @Override
    public int getTileWidth() {
	// TODO Auto-generated method stub
	return 32;
    }
    @Override
    public int getTileHeight() {
	// TODO Auto-generated method stub
	return 32;
    }
}
