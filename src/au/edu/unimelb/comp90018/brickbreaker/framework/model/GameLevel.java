/**
 * 
 */
package au.edu.unimelb.comp90018.brickbreaker.framework.model;

/**
 * @author achaves
 *
 */
public class GameLevel {

	private Brick[][] bricks;
	private Paddle paddle;
	private Ball ball;
	private int speed;
	private static int ROWS = 6;
	
	public GameLevel(){
		bricks = new Brick[ROWS][ROWS];
	}

	public void addBrick(int row, int column, Brick brick){
		bricks[row][column] = brick;
	}
	public Brick[][] getBricks() {
		return bricks;
	}

	public void setBricks(Brick[][] bricks) {
		this.bricks = bricks;
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

	@Override
	public boolean equals(Object o) {
		GameLevel gl = (GameLevel)o;
		
		for (int i = 0; i < ROWS; i++){
			for (int j = 0; j < ROWS; j++){				
				if (gl.getBricks()[i][j]!=null && this.bricks[i][j]==null){
					return false;
				}
				if (gl.getBricks()[i][j]==null && this.bricks[i][j]!=null){
					return false;
				}				
				if (gl.getBricks()[i][j]!=null && this.bricks[i][j]!=null && !gl.getBricks()[i][j].equals(this.bricks[i][j]))
					return false;
			}
		}
		if (!gl.getBall().equals(this.ball))
			return false;
		if (!gl.getPaddle().equals(this.paddle))
			return false;
		return true;
	}

}
