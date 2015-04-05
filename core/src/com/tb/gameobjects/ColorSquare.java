package com.tb.gameobjects;




import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ColorSquare {
	private Color color;
	private float width, height;
	private Rectangle bounds;
	private Vector2 position;
	private boolean isPressed;
	
	public ColorSquare(Color color, float height, float width, float x, float y){
		this.color = color;
		this.height = height;
		this.width = width;
		this.position = new Vector2(x,y);
		bounds = new Rectangle(x,y,width, height);
		isPressed = false;
	}
	
	
	public Vector2 getPosition(){
		return position;
	}
	
	public void setPosition(Vector2 p){
		position = p;
		bounds.setX(p.x);
		bounds.setY(p.y);
	}
	
	public boolean onClick(Color clickColor){
		if(color.equals(clickColor)){
			return true;
		} else return false;
	}
	
	public Color getColor(){
		return color;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public boolean isClicked(int screenX, int screenY) {
		return bounds.contains(screenX, screenY);
	}

	public boolean isTouchDown(int screenX, int screenY) {

		if (bounds.contains(screenX, screenY)) {
			isPressed = true;
			return true;
		}
		return false;
	}
	
	public Rectangle getBounds(){
		return bounds;
	}

	public boolean isTouchUp(int screenX, int screenY) {
		
		// It only counts as a touchUp if the button is in a pressed state.
		if (bounds.contains(screenX, screenY) && isPressed) {
			isPressed = false;
			//AssetLoader.flap.play();
			return true;
		}
		
		// Whenever a finger is released, we will cancel any presses.
		isPressed = false;
		return false;
	}
	
	public float getY(){
		return position.y;
	}
}

