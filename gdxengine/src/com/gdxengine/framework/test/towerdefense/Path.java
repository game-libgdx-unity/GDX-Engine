package com.gdxengine.framework.test.towerdefense;

import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.test.towerdefense.Monster.Direction;

public class Path {

    Direction direction;
    public float x;
    public float y;

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
