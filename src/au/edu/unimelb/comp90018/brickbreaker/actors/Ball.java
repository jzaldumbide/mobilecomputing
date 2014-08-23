package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.BrickBreakerGame;
import au.edu.unimelb.comp90018.brickbreaker.framework.Collideable;
import au.edu.unimelb.comp90018.brickbreaker.framework.util.ActorAttribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.scenes.scene2d.Actor;


/**
 * 
 * @author Diego
 *
 */
@Deprecated
public class Ball extends Actor implements Collideable {

	private ShapeRenderer shapeRenderer;
	/**
	 * A fixture uses exactly one shape to which it adds material properties, such as
	 * density, friction, and restitution. The shape defined in a fixture is then attached to a 
	 * body by adding the fixture to it.
	 */
		
	private Fixture fixture;
	private Vector2 worldToScreen;
	private ActorAttribute attributes;
	
	private boolean isDying = false;
	
	/* Ball default attributes */
	private float initialPosX = 10f;
	private float initialPosY = 20f;
	
	
	public Ball(ActorAttribute attributes) {
		
		this.attributes = attributes;
		shapeRenderer = new ShapeRenderer();
		worldToScreen = attributes.getScale();

		/* TODO: Must be replaced with a image or sprite */
		setColor(Color.WHITE);
		setSize(20, 20);		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(initialPosX, initialPosY);
		
		Body body = attributes.getWorld().createBody(bodyDef);
		/* TODO: Must be replaced with a image or sprite*/
		CircleShape circle = new CircleShape();
		circle.setRadius(1);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = attributes.getDensity();
		fixtureDef.friction = attributes.getFriction();
		fixtureDef.restitution = attributes.getRestitution();
		body.setLinearVelocity(attributes.getLinearVelocityX(), attributes.getLinearVelocityY());
		body.setUserData(this);
		fixture = body.createFixture(fixtureDef);

		circle.dispose();
	}

	@Override
	public float getX() {
		return fixture.getBody().getPosition().x;
	}

	@Override
	public float getY() {
		return fixture.getBody().getPosition().y;
	}

	public void act(float delta) {
		if (getY() * worldToScreen.x + getHeight() < 0) {
			isDying = true;
		}

	}

	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();

		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
		shapeRenderer.translate(getX() * worldToScreen.x, getY() * worldToScreen.y, 0);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.circle(0, 0, getHeight() / 2);
		shapeRenderer.end();
		batch.begin();
	}
	
	@Override
	public float getHeight() {
		return super.getHeight() * BrickBreakerGame.scale.y;
	}

	public void reset() {
		isDying = false;
		fixture.getBody().setTransform(initialPosX, initialPosY, 0);
		fixture.getBody().setLinearVelocity(getAttributes().getLinearVelocityX(), getAttributes().getLinearVelocityY());
	}

	//Comes from Collidable
	
	@Override
	public void contact(Collideable other) {
		other.playHitSound();
	}

	@Override
	public boolean isDying() {
		return isDying;
	}

	@Override
	public void playHitSound() {

	}
	
	public ActorAttribute getAttributes() {
		return attributes;
	}
	
	
}
