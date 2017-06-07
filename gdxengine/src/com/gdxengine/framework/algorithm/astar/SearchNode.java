package com.gdxengine.framework.algorithm.astar;

import com.badlogic.gdx.math.Vector2;

public class SearchNode
{
    /**
     * Location on the map
     */
    public Vector2 Position;
    /**
     * If true, this tile can be walked on.
     */
    public boolean Walkable;

    /**
     * This contains references to the for nodes surrounding  this tile (Up, Down, Left, Right).
     */
    public SearchNode[] Neighbors;
    /**
     * A reference to the node that transfered this node to the open list. This will be used to trace our path back from the goal node to the start node
     */
    public SearchNode Parent;

    /**
     * Provides an easy way to check if this node is in the open list.
     */
    public boolean InOpenList;
    /**
     * Provides an easy way to check if this node
     * is in the closed list.
     */
    public boolean InClosedList;

    /**
    * The approximate distance from the start node to the
    * goal node if the path goes through this node. (F)
    */
    public float DistanceToGoal;
    /**
     *  Distance traveled from the spawn point. (G)
     */
    public float DistanceTraveled;
}
