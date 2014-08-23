package au.edu.unimelb.comp90018.brickbreaker.framework.util;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Defines own characteristics of an Actor.
 * 
 * @author Diego
 *
 */
public class ActorAttribute {

	private float density;
	private float friction;
	private float restitution;
	private float positionX;
	private float positionY;
	private float linearVelocityX;
	private float linearVelocityY;
	private boolean isDying;
	private World world;
	private Vector2 scale;

	
	public ActorAttribute() {
		super();
	}
	
	
	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getRestitution() {
		return restitution;
	}

	public void setRestitution(float restitution) {
		this.restitution = restitution;
	}

	public float getPositionX() {
		return positionX;
	}

	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}

	public float getPositionY() {
		return positionY;
	}

	public void setPositionY(float positionY) {
		this.positionY = positionY;
	}

	public float getLinearVelocityX() {
		return linearVelocityX;
	}

	public void setLinearVelocityX(float linearVelocityX) {
		this.linearVelocityX = linearVelocityX;
	}

	public float getLinearVelocityY() {
		return linearVelocityY;
	}

	public void setLinearVelocityY(float linearVelocityY) {
		this.linearVelocityY = linearVelocityY;
	}

	public boolean isDying() {
		return isDying;
	}

	public void setDying(boolean isDying) {
		this.isDying = isDying;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Vector2 getScale() {
		return scale;
	}

	public void setScale(Vector2 scale) {
		this.scale = scale;
	}

}
