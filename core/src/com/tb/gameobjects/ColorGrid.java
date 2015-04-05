package com.tb.gameobjects;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ColorGrid {
	private ArrayList<ColorSquare> x;
	private ArrayList<Color> colors;
	private ColorSelector cs;
	private Random r;
	private Vector2 midPoint;
	private float velocityX, velocityY;
	private boolean isClicked, isStopped, isFours;
	private float size;
	public ColorGrid(Vector2 midPoint, float size, boolean isFours){
		r = new Random();
		cs = new ColorSelector(r);
		x = new ArrayList<ColorSquare>();
		velocityX = 0;
		this.isFours = isFours;
		colors = new ArrayList<Color>();
		refill();
		this.midPoint = midPoint;
		this.size = size;
		if(!isFours){
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20f,midPoint.y+10f)); //5
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20f,midPoint.y+10-size)); //2
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20f,midPoint.y+10+size)); //8
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20-size,midPoint.y+10f)); //4
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20+size,midPoint.y+10f)); //6
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20-size,midPoint.y+10-size)); //1
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20+size,midPoint.y+10-size)); //3
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20-size,midPoint.y+10+size)); //7
		x.add(new ColorSquare(colors.remove(0),size,size,midPoint.x-20+size,midPoint.y+10+size)); //9
		}
		else{
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-(2*size)-90,midPoint.y-2*size+35)); //1
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-size-90,midPoint.y-2*size+35)); //2
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90,midPoint.y-2*size+35)); //3
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90+size,midPoint.y-2*size+35)); //4
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-2*size,midPoint.y-size+35)); //5
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-size,midPoint.y-size+35)); //6
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90,midPoint.y-size+35)); //7
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90+size,midPoint.y-size+35)); //8
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-2*size,midPoint.y+35)); //9
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-size,midPoint.y+35)); //10
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90,midPoint.y+35)); //11
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90+size,midPoint.y+35)); //12
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-2*size,midPoint.y+size+35)); //13
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90-size,midPoint.y+size+35)); //14
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90,midPoint.y+size+35)); //15
			x.add(new ColorSquare(cs.getColor(),size,size,midPoint.x-90+size,midPoint.y+size+35)); //16
		}
		velocityY = 0;
		refill();
		isClicked = false;
		isStopped = false;
	}
	
	public ArrayList<ColorSquare> getList(){
		return x;
	}
	
	public void update(float delta){
		for(ColorSquare c: x){
			c.setPosition(new Vector2(c.getPosition().x+(velocityX*delta), c.getPosition().y+(velocityY*delta)));
		}
	}
	
	public boolean isInGrid(Color c){
		for(int i = 0;i<x.size(); i++){
			if(x.get(i).getColor().equals(c))
			return true;
		}
		return false;
	}
	
	public boolean isOffScreen(){
		if(x.get(15).equals(null))
		return x.get(8).getPosition().x < 0 - x.get(8).getWidth() ||
				x.get(8).getPosition().x > 136 + x.get(8).getWidth();
		else
			return x.get(15).getPosition().x < 0 - x.get(15).getWidth() ||
					x.get(15).getPosition().x > 136 + x.get(15).getWidth();	
	}

	public ColorSelector getSelector(){
		return cs;
	}
	
	public void reset(float newX){
		newX +=20;
		isClicked = false;
		isStopped = false;
		velocityX=0;
		x.clear();
		if(!isFours){
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20f,midPoint.y+10f)); //5
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20f,midPoint.y+10-size)); //2
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20f,midPoint.y+10+size)); //8
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20-size,midPoint.y+10f)); //4
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20+size,midPoint.y+10f)); //6
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20-size,midPoint.y+10-size)); //1
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20+size,midPoint.y+10-size)); //3
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20-size,midPoint.y+10+size)); //7
		x.add(new ColorSquare(colors.remove(0),size,size,newX-20+size,midPoint.y+10+size)); //9
		}
		else{
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-(2*size),midPoint.y-2*size+35)); //1
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-size,midPoint.y-2*size+35)); //2
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20,midPoint.y-2*size+35)); //3
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20+size,midPoint.y-2*size+35)); //4
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-2*size,midPoint.y-size+35)); //5
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-size,midPoint.y-size+35)); //6
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20,midPoint.y-size+35)); //7
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20+size,midPoint.y-size+35)); //8
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-2*size,midPoint.y+35)); //9
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-size,midPoint.y+35)); //10
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20,midPoint.y+35)); //11
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20+size,midPoint.y+35)); //12
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-2*size,midPoint.y+size+35)); //13
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20-size,midPoint.y+size+35)); //14
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20,midPoint.y+size+35)); //15
			x.add(new ColorSquare(cs.getColor(),size,size,newX-20+size,midPoint.y+size+35)); //16
		}
		refill();
	}
	
	public void moveGrid(){
		velocityX = -700;
	}
	
	public void moveDown(){
		velocityY = 400;
	}
	
	public void stopGrid(){
		velocityX = 0;
		isStopped = true;
		
	}
	
	public boolean isClicked(){
		return isClicked;
	}
	
	public void onClick(){
		isClicked = true;
	}
	
	public boolean isStopped(){
		return isStopped;
	}
	
	public void setPosition(int add){
		for(ColorSquare c: x){
			c.setPosition(new Vector2(c.getPosition().x+add, c.getPosition().y));
		}
	}
	public void setPositionY(int add){
		for(ColorSquare c: x){
			c.setPosition(new Vector2(c.getPosition().x, c.getPosition().y+add));
		}
	}
	
	public void refill(){
		colors.add(ColorSelector.red);
		colors.add(ColorSelector.orange);
		colors.add(Color.YELLOW);
		colors.add(ColorSelector.green);
		colors.add(Color.BLUE);
		colors.add(Color.PURPLE);
		colors.add(ColorSelector.brown);
		colors.add(ColorSelector.pink);
		colors.add(Color.WHITE);
		Collections.shuffle(colors, r);
	}
	
}
