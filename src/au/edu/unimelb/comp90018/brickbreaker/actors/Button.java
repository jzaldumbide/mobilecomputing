package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.GameObject;


public class Button extends GameObject {
		
	public enum ButtonSize {
	    SMALL_SQUARE(16,16),
	    MEDIUM_SQUARE(32,32),
	    LARGE_SQUARE(48,48),
	    XLARGE_SQUARE(64,64),
	    SMALL_RECTANGLE(16,8),
	    MEDIUM_RECTNGLE(32,16),
	    LARGE_RECTANGLE(64,32),
	    XLARGE_RECTANGLE(160,32);
	    
	    private int buttonWidth; 
		private int buttonHeight;

        private ButtonSize(int width, int height) {
                this.setButtonWidth(width);
                this.setButtonHeight(height);
        }

		public int getButtonWidth() {
			return buttonWidth;
		}

		public void setButtonWidth(int buttonWidth) {
			this.buttonWidth = buttonWidth;
		}

		public int getButtonHeight() {
			return buttonHeight;
		}

		public void setButtonHeight(int buttonHeight) {
			this.buttonHeight = buttonHeight;
		}
	    
	}	

	
	public Button(float x, float y, ButtonSize size) {
		super(x, y, size.getButtonWidth(), size.getButtonHeight());
	}

	public void update(float deltaTime) {

	}
}
