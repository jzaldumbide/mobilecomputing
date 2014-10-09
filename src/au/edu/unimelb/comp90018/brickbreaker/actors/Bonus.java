package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;

import com.badlogic.gdx.math.Vector2;

public class Bonus extends DynamicGameObject {

	private BonusType type;
	
	public enum BonusType {
	    COIN(20,20),
	    VIRUS(20,20),
	    EXTRA_LIFE(20,20);
	    
	    private int bonusWidth; 
		private int bonusHeight;

        private BonusType(int width, int height) {
                this.setBonusWidth(width);
                this.setBonusHeight(height);
        }

		public int getBonusWidth() {
			return bonusWidth;
		}

		public void setBonusWidth(int bonusWidth) {
			this.bonusWidth = bonusWidth;
		}
		
		public int getBonusHeight() {
			return bonusHeight;
		}

		public void setBonusHeight(int bonusHeight) {
			this.bonusHeight = bonusHeight;
		}
	}
	
	public Bonus(float x, float y, BonusType type, Vector2 velocity) {
		super(x, y, type.getBonusWidth(), type.getBonusHeight());
		this.velocity.set(velocity);
		this.setType(type);
	}

	public void update(float deltaTime) {

		position.add(0, -9.8f * deltaTime * velocity.y);
		bounds.y = position.y - bounds.height / 2;
		
		if (position.y < 0)
			pulverize();
		
	}

	public void pulverize() {
		//disapear
		position.y = -10000;
		bounds.y = position.y - bounds.height / 2;
	}

	public BonusType getType() {
		return type;
	}

	public void setType(BonusType type) {
		this.type = type;
	}
}
