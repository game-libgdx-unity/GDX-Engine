package com.gdxengine.framework.test.towerdefense;

import java.util.ArrayDeque;
import java.util.Queue;

import javax.rmi.CORBA.Util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.Animation;
import com.gdxengine.framework.AnimationPlayer;
import com.gdxengine.framework.DrawableGameComponent;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.action.BaseSimpleAction;
import com.gdxengine.framework.interfaces.IActionPerformer;
import com.gdxengine.framework.interfaces.IGameService;

public abstract class Monster extends DrawableGameComponent {

    public enum Direction {
	Up(3), Down(0), Left(1), Right(2);

	private int i;

	private Direction(int i) {
	    this.i = i;
	}

	public int getIndex() {
	    return i;
	}

	public void setIndex(int i) {
	    this.i = i;
	}
    }

    public static final int REGION_WIDTH = 40;
    public static final int REGION_HEIGHT = 50;

    float x;
    float y;
    final Vector2 velocity = new Vector2();
    TextureRegion region;
    Direction direction;
    float speed = 2f;
    float slowRate = 1f;
    int bonus = 10;
    public int hitPoint = 100;
    int MaxHitPoint = 100;
    Color color;
    ArrayDeque<Path> path;
    Path target;

    AnimationPlayer player;
    
    public abstract void onFinishedPath();
    public abstract void onDead();
    
    public float getCenterX(){
	return getX() + REGION_WIDTH/2f;
    }
    public float getCenterY(){
	return getY() + REGION_HEIGHT/2f;
    }
    
    IActionPerformer restoreSpeed = new BaseSimpleAction(false, 1f) {
        
        @Override
        public void onActionPerformance() {
        	setColor(Color.WHITE);
        	setSlowRate(1f);
        }
    };
    
    public IActionPerformer getActionRestoreSpeed()
    {
	return restoreSpeed;
    }

    public Monster(IGameService services, Texture texture, int index) {
	super(services);
	
	color = Color.WHITE;
	
	hitPoint = MaxHitPoint = GameSpecs.hitPoint[index];
	bonus = GameSpecs.bonus[index];
	speed = GameSpecs.speed[index];
	
	this.region = new TextureRegion(texture);
	region.setRegion(0, 0, REGION_WIDTH, REGION_HEIGHT);
	this.path = new ArrayDeque<Path>(TowerDefenseScene.path);
	target = path.pop();
	x = target.x;
	y = target.y;
	direction = target.getDirection();
	move(direction);
	player = new AnimationPlayer(TowerDefenseScene.monsterAnimation) {

	    @Override
	    public void onFrameChanged() {
		region.setRegion(frameIndex * REGION_WIDTH, REGION_HEIGHT * direction.getIndex(), REGION_WIDTH,
			REGION_HEIGHT);
	    }

	    @Override
	    public void onAnimationChanged() {
	    }

	    @Override
	    public void onFinishedLooping() {
		// TODO Auto-generated method stub
		
	    }
	};
    }

    public Rectangle getBound(){
	Rectangle.tmp.set(x, y, REGION_WIDTH, REGION_HEIGHT);
	return Rectangle.tmp;
    }
    
    boolean changedDir = false;
    
    @Override
    public void update(float gameTime) {
	
	if(slowRate < 1f)
	    restoreSpeed.update(gameTime);
	
	player.updateAnimation(gameTime);
	x += velocity.x;
	y += velocity.y;
	
	if(x < 0 || y < 0)
	{
	    setDead(true);
		onFinishedPath();
	}
	
	if(Utils.distance(x , y , target.x, target.y) < speed + 1 )
	{
	    if(path.peek() != null)
	    {
		x = target.x;
		y = target.y;
		direction = target.getDirection();
		move(direction);
		target = path.pop();
	    }
	    else
	    {
		setDead(true);
		onFinishedPath();
		
	    }
	}
    }

    @Override
    public void render(float gameTime) {
	
	services.getSpriteBatch().setColor(this.getColor());
	services.drawTextureRegion(region, x, y);
	services.getSpriteBatch().setColor(Color.WHITE);
    }
    
    public boolean isAlive(){
	
	if(isDead())
	    return false;
	
	if(hitPoint <= 0)
	{
	    setDead(true);
	    onDead();
	    return false;
	}
	
	return true;
    }

    public void move(Direction dir) {
	if (direction.getIndex() != dir.getIndex()) {
	    this.direction = dir;
	}
	
	switch (dir) {
	case Down:
	    velocity.set(0, -speed * slowRate);
	    break;
	case Up:
	    velocity.set(0, speed * slowRate);
	    break;
	case Left:
	    velocity.set(-speed * slowRate, 0);
	    break;
	case Right:
	    velocity.set(speed * slowRate, 0);
	    break;
	}
    }
    
    public float getHealthyRate(){
	return ((float)hitPoint) / ((float)MaxHitPoint);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSlowRate() {
        return slowRate;
    }

    public void setSlowRate(float slowRate) {
        this.slowRate = slowRate;
        move(direction);
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayDeque<Path> getPath() {
        return path;
    }

    public void setPath(ArrayDeque<Path> path) {
        this.path = path;
    }
    public float getHeight() {
	// TODO Auto-generated method stub
	return region.getRegionHeight();
    }

}
