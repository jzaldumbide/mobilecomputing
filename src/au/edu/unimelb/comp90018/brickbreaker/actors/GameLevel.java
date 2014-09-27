/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.actors;

import java.util.ArrayList;
import java.util.List;


/**
 * @author achaves
 *
 */
public class GameLevel {

	private List<BrickAdapter> bricks;
	private Paddle paddle;
	private Ball ball;
	private int speed;
	
	public GameLevel(){
		bricks = new ArrayList<BrickAdapter>();
	}

	public void addBrick(BrickAdapter brick){
		bricks.add(brick);
	}
	public List<BrickAdapter> getBricks() {
		return bricks;
	}

	public Paddle getPaddle() {
		return paddle;
	}

	public void setPaddle(Paddle paddle) {
		this.paddle = paddle;
	}

	public Ball getBall() {
		return ball;
	}

	public void setBall(Ball ball) {
		this.ball = ball;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
