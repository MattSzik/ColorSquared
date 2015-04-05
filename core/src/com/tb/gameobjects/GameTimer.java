package com.tb.gameobjects;


public class GameTimer{
	private float timerSpeed;
	private float originalSpeed;
	private float positionX;
	
	public GameTimer(float timerSpeed, float positionX){
		this.timerSpeed = timerSpeed;
		this.originalSpeed = timerSpeed;
		this.positionX = positionX;
		stop();
	}
	public boolean isOffScreen(){
		if(positionX<=-136)
			return true;
		else
			return false;
	}
	
	public void update(float delta){
		positionX -=timerSpeed*(delta);
	}
	
	public float getPosition(){
		return positionX;
	}
	
	public void reset(){
		positionX=0;
	}
	
	public void restart(){
		timerSpeed = originalSpeed;
	}
	
	public void stop(){
		timerSpeed=0;
	}
	
	public float getSpeed(){
		return originalSpeed;
	}
	
	public void setSpeed(float timerSpeed){
		this.timerSpeed = timerSpeed;
		originalSpeed = timerSpeed;
		stop();
	}
	
}
