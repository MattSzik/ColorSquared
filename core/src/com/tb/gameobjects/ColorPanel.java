package com.tb.gameobjects;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class ColorPanel {
	private ArrayList <ColorGrid> cgList;
	private float g1x;
	Vector2 midPointX;
	
	public ColorPanel(Vector2 midPointX){ //Make sure midPointX is fed a Y value that leaves g6 on top of the screen.
		cgList = new ArrayList<ColorGrid>();
		this.midPointX = midPointX;
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -225), 25f,false));//Top left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -225), 25f,false));//Top right
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -150), 25f,false));//Mid left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -150), 25f,false));//Mid right
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -75), 25f,false));//Bottom left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -75), 25f,false));//Bottom right
	}
	
	public boolean isMovingOffScreen(){
		return cgList.get(5).getList().get(8).getY() >=midPointX.y*2;
	}
	
	public void update(float delta){
		for(ColorGrid g: cgList){
			g.update(delta);
		}
	}
	
	public void movePanel(){
		for(ColorGrid g: cgList){
			g.moveDown();
		}
	}
	
	public ArrayList<ColorGrid> getColorGridList(){
		return cgList;
	}
	
	public boolean isOffScreen(){
		return cgList.get(0).getList().get(0).getY() >= 300;
		
	}
	
	public void restart(){
		cgList.clear();
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -225), 25f,false));//Top left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -225), 25f,false));//Top right
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -150), 25f,false));//Mid left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -150), 25f,false));//Mid right
		cgList.add(new ColorGrid(new Vector2(midPointX.x-38, -75), 25f,false));//Bottom left
		cgList.add(new ColorGrid(new Vector2(midPointX.x+37, -75), 25f,false));//Bottom right
	}
	
}
