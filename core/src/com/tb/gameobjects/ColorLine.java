package com.tb.gameobjects;

import com.badlogic.gdx.Gdx;

public class ColorLine {
	public float y;
	public int velocityY;
	public float midPoint;
	
	public ColorLine(float x, float y, float midPoint, int velocityY){
		this.y=y;
		this.velocityY=velocityY;
		this.midPoint = midPoint;
	}
	
	
	public void update(float delta){
		y += velocityY*delta;
	}
	
	public void reset(float newY){
		y=newY;
	}
	
	public boolean isOffScreen(){
		return y<-2*midPoint ||
				y>midPoint*2;
	}
	
	public float getY(){
		return y;
	}
}
