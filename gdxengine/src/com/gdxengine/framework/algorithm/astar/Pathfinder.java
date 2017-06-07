package com.gdxengine.framework.algorithm.astar;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Pathfinder {
    // Stores an array of the walkable search nodes.
    private SearchNode[][] searchNodes;

    // The width of the map.
    private int levelWidth;
    // The height of the map.
    private int levelHeight;

    // Holds search nodes that are avaliable to search.
    private ArrayList<SearchNode> openList = new ArrayList<SearchNode>();
    // Holds the nodes that have already been searched.
    private ArrayList<SearchNode> closedList = new ArrayList<SearchNode>();

    IMap map;

    public Pathfinder(IMap map) {
	levelWidth = map.getWidth();
	levelHeight = map.getHeight();
	this.map = map;
	InitializeSearchNodes(map);
    }

    /**
     * Returns an estimate of the distance between two points. (H)
     * 
     * @param point1
     * @param point2
     * @return
     */
    private float Heuristic(Vector2 point1, Vector2 point2) {
	return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }

    /**
     * Splits our level up into a grid of nodes.
     * 
     * @param map
     */
    private void InitializeSearchNodes(IMap map) {
	searchNodes = new SearchNode[levelWidth][levelHeight];

	// For each of the tiles in our map, we
	// will create a search node for it.
	for (int x = 0; x < levelWidth; x++) {
	    for (int y = 0; y < levelHeight; y++) {
		// Create a search node to represent this tile.
		SearchNode node = new SearchNode();
		node.Position = new Vector2(x, y);

		// Our enemies can only walk on grass tiles.
		node.Walkable = map.getIndex(x, y) == 0;

		// We only want to store nodes
		// that can be walked on.
		if (node.Walkable == true) {
		    node.Neighbors = new SearchNode[4];
		    searchNodes[x][y] = node;
		}
	    }
	}

	/**
	 * Now for each of the search nodes, we will connect it to each of its
	 * neighbours.
	 */
	for (int x = 0; x < levelWidth; x++) {
	    for (int y = 0; y < levelHeight; y++) {
		SearchNode node = searchNodes[x][y];

		// We only want to look at the nodes that
		// our enemies can walk on.
		if (node == null || node.Walkable == false) {
		    continue;
		}

		// An array of all of the possible neighbors this
		// node could have. (We will ignore diagonals for now.)
		Vector2[] neighbors = new Vector2[] { new Vector2(x, y - 1), // The
									     // node
									     // above
									     // the
									     // current
									     // node
			new Vector2(x, y + 1), // The node below the current
					       // node.
			new Vector2(x - 1, y), // The node left of the current
					       // node.
			new Vector2(x + 1, y), // The node right of the current
					       // node
		};

		// We loop through each of the possible neighbors
		for (int i = 0; i < neighbors.length; i++) {
		    Vector2 position = neighbors[i];

		    // We need to make sure this neighbour is part of the level.
		    if (position.x < 0 || position.x > levelWidth - 1
			    || position.y < 0 || position.y > levelHeight - 1) {
			continue;
		    }

		    SearchNode neighbor = searchNodes[(int) position.x][(int) position.y];

		    // We will only bother keeping a reference
		    // to the nodes that can be walked on.
		    if (neighbor == null || neighbor.Walkable == false) {
			continue;
		    }

		    // Store a reference to the neighbor.
		    node.Neighbors[i] = neighbor;
		}
	    }
	}
    }

    /**
     * Resets the state of the search nodes.
     */
    private void ResetSearchNodes() {
	openList.clear();
	closedList.clear();

	for (int x = 0; x < levelWidth; x++) {
	    for (int y = 0; y < levelHeight; y++) {
		SearchNode node = searchNodes[x][y];

		if (node == null) {
		    continue;
		}

		node.InOpenList = false;
		node.InClosedList = false;

		node.DistanceTraveled = Float.MAX_VALUE;
		node.DistanceToGoal = Float.MAX_VALUE;
	    }
	}
    }

    /**
     * Use the parent field of the search nodes to trace a path from the end
     * node to the start node.
     * 
     * @param startNode
     * @param endNode
     * @return
     */
    private ArrayList<Vector2> FindFinalPath(SearchNode startNode,
	    SearchNode endNode) {
	closedList.add(endNode);

	SearchNode parentTile = endNode.Parent;

	// Trace back through the nodes using the parent fields
	// to find the best path.
	while (parentTile != startNode) {
	    closedList.add(parentTile);
	    parentTile = parentTile.Parent;
	}

	ArrayList<Vector2> finalPath = new ArrayList<Vector2>();

	// Reverse the path and transform into world space.
	for (int i = closedList.size() - 1; i >= 0; i--) {
	    finalPath.add(new Vector2(closedList.get(i).Position.x
		    * map.getTileWidth() + map.getTileWidth() / 2, (map
		    .getWidth() - closedList.get(i).Position.y)
		    * map.getTileHeight() + map.getTileHeight() / 2));
	}

	return finalPath;
    }

    /**
     * Returns the node with the smallest distance to goal.
     * 
     * @return
     */
    private SearchNode FindBestNode() {
	SearchNode currentTile = openList.get(0);

	float smallestDistanceToGoal = Float.MAX_VALUE;

	// Find the closest node to the goal.
	for (int i = 0; i < openList.size(); i++) {
	    if (openList.get(i).DistanceToGoal < smallestDistanceToGoal) {
		currentTile = openList.get(i);
		smallestDistanceToGoal = currentTile.DistanceToGoal;
	    }
	}
	return currentTile;
    }

    /**
     * Finds the optimal path from one point to another.
     * 
     * @param startPoint
     * @param endPoint
     * @return
     */
    public ArrayList<Vector2> findPath(Vector2 startPoint, Vector2 endPoint) {
	// Only try to find a path if the start and end points are different.
	if (startPoint == endPoint) {
	    return new ArrayList<Vector2>();
	}

	// ///////////////////////////////////////////////////////////////////
	// Step 1 : clear the Open and Closed Lists and reset each node’s F
	// and G values in case they are still set from the last
	// time we tried to find a path.
	// ///////////////////////////////////////////////////////////////////
	ResetSearchNodes();

	// Store references to the start and end nodes for convenience.
	SearchNode startNode = searchNodes[(int) startPoint.x][(int) startPoint.y];
	SearchNode endNode = searchNodes[(int) endPoint.x][(int) endPoint.y];

	// ///////////////////////////////////////////////////////////////////
	// Step 2 : Set the start node’s G value to 0 and its F value to the
	// estimated distance between the start node and goal node
	// (this is where our H function comes in) and add it to the
	// Open ArrayList.
	// ///////////////////////////////////////////////////////////////////
	startNode.InOpenList = true;

	startNode.DistanceToGoal = Heuristic(startPoint, endPoint);
	startNode.DistanceTraveled = 0;

	openList.add(startNode);

	// ///////////////////////////////////////////////////////////////////
	// Setp 3 : While there are still nodes to look at in the Open list :
	// ///////////////////////////////////////////////////////////////////
	while (openList.size() > 0) {
	    // ///////////////////////////////////////////////////////////////
	    // a) : Loop through the Open ArrayList and find the node that
	    // has the smallest F value.
	    // ///////////////////////////////////////////////////////////////
	    SearchNode currentNode = FindBestNode();

	    // ///////////////////////////////////////////////////////////////
	    // b) : If the Open ArrayList empty or no node can be found,
	    // no path can be found so the algorithm terminates.
	    // ///////////////////////////////////////////////////////////////
	    if (currentNode == null) {
		break;
	    }

	    // ///////////////////////////////////////////////////////////////
	    // c) : If the Active Node is the goal node, we will
	    // find and return the final path.
	    // ///////////////////////////////////////////////////////////////
	    if (currentNode == endNode) {
		// Trace our path back to the start.
		return FindFinalPath(startNode, endNode);
	    }

	    // ///////////////////////////////////////////////////////////////
	    // d) : Else, for each of the Active Node’s neighbours :
	    // ///////////////////////////////////////////////////////////////
	    for (int i = 0; i < currentNode.Neighbors.length; i++) {
		SearchNode neighbor = currentNode.Neighbors[i];

		// ////////////////////////////////////////////////
		// i) : Make sure that the neighbouring node can
		// be walked across.
		// ////////////////////////////////////////////////
		if (neighbor == null || neighbor.Walkable == false) {
		    continue;
		}

		// ////////////////////////////////////////////////
		// ii) Calculate a new G value for the neighbouring node.
		// ////////////////////////////////////////////////
		float distanceTraveled = currentNode.DistanceTraveled + 1;

		// An estimate of the distance from this node to the end node.
		float heuristic = Heuristic(neighbor.Position, endPoint);

		// ////////////////////////////////////////////////
		// iii) If the neighbouring node is not in either the Open
		// ArrayList or the Closed ArrayList :
		// ////////////////////////////////////////////////
		if (neighbor.InOpenList == false
			&& neighbor.InClosedList == false) {
		    // (1) Set the neighbouring node’s G value to the G value we
		    // just calculated.
		    neighbor.DistanceTraveled = distanceTraveled;
		    // (2) Set the neighbouring node’s F value to the new G
		    // value + the estimated
		    // distance between the neighbouring node and goal node.
		    neighbor.DistanceToGoal = distanceTraveled + heuristic;
		    // (3) Set the neighbouring node’s Parent property to point
		    // at the Active Node.
		    neighbor.Parent = currentNode;
		    // (4) add the neighbouring node to the Open ArrayList.
		    neighbor.InOpenList = true;
		    openList.add(neighbor);
		}
		// ////////////////////////////////////////////////
		// iv) Else if the neighbouring node is in either the Open
		// ArrayList or the Closed ArrayList :
		// ////////////////////////////////////////////////
		else if (neighbor.InOpenList || neighbor.InClosedList) {
		    // (1) If our new G value is less than the neighbouring
		    // node’s G value, we basically do exactly the same
		    // steps as if the nodes are not in the Open and
		    // Closed Lists except we do not need to add this node
		    // the Open ArrayList again.
		    if (neighbor.DistanceTraveled > distanceTraveled) {
			neighbor.DistanceTraveled = distanceTraveled;
			neighbor.DistanceToGoal = distanceTraveled + heuristic;

			neighbor.Parent = currentNode;
		    }
		}
	    }

	    // ///////////////////////////////////////////////////////////////
	    // e) remove the Active Node from the Open ArrayList and add it to
	    // the
	    // Closed ArrayList
	    // ///////////////////////////////////////////////////////////////
	    openList.remove(currentNode);
	    currentNode.InClosedList = true;
	}

	// No path could be found.
	return new ArrayList<Vector2>();
    }
}