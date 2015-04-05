package com.tb.gameobjects;


public class GameOverPanel {
	private float GOX, SX, RX, MX;
	//Game Over, Score, High Score, Retry, and Menu, respectively.
	
	public GameOverPanel(float offScreen){
		GOX = offScreen;
		SX= offScreen;
		RX= offScreen - 30;
		MX= offScreen + 50;
	}
	
	public void update(float delta){
		GOX-=delta;
		SX-=delta;
		RX-=delta;
		MX-=delta;
	}
	
	public float getGOX(){
		return GOX;
	}
	
	public float getSX(){
		return SX;
	}
	
	public float getMX(){
		return MX;
	}
	
	public float getRX(){
		return RX;
	}
	
	public void restart(float offScreen){
		GOX = offScreen;
		SX= offScreen;
		RX= offScreen - 30;
		MX= offScreen + 50;
	}
}
