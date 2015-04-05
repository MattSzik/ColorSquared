package com.tb.gameobjects;


import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.Color;
	


public class ColorSelector {
	public static final Color brown = new Color((139f/255),(69f/255),(19f/255),1);
	public static final Color pink = new Color(1,(20f/255),(147f/255),1);
	public static final Color orange = new Color(1, 127f/255,0,1);
	public static final Color green = new Color(0,100f/255,0,1);
	public static final Color red = new Color(200f/255,0,0,1);
	public static final Color honeydew = new Color(240/255f, 1, 240/255f, 1);
	public static final Color azure = new Color(240/255f, 1,1,1);
	public static final Color lavender = new Color(230f/255,230f/255,250f/255,1);
	public static final Color lightcyan = new Color(224f/255,1,1,1);
	public static final Color slategray = new Color(119f/255,136f/255,153f/255,1);
	public static final Color gray = new Color(170f/255,170f/255,170f/255,1);
	private static ArrayList <Color> possibleColors;
	
	private static Random r;
	private static int s;
	public ColorSelector(Random r){
		this.r=r;
		possibleColors = new ArrayList<Color> ();
		possibleColors.add(red);
		possibleColors.add(orange);
		possibleColors.add(Color.YELLOW);
		possibleColors.add(green);
		possibleColors.add(Color.BLUE);
		possibleColors.add(Color.PURPLE);
		possibleColors.add(brown);
		possibleColors.add(pink);
		possibleColors.add(Color.WHITE);
	}
	
	public static Color getColor(){
		s=r.nextInt(9);
		switch (s){
			case 0: return red;
			case 1: return orange;
			case 2: return Color.YELLOW;
			case 3: return green;
			case 4: return Color.BLUE;
			case 5: return Color.PURPLE;
			case 6: return brown; 
			case 7: return Color.WHITE;
			case 8: return pink; 
			default: return null;
		}	
	}
	
	public static Color getColor(int i){
		s=r.nextInt(possibleColors.size());
		Color returnme = possibleColors.remove(r.nextInt(possibleColors.size()));
		if(possibleColors.isEmpty()){
			possibleColors.add(red);
			possibleColors.add(orange);
			possibleColors.add(Color.YELLOW);
			possibleColors.add(green);
			possibleColors.add(Color.BLUE);
			possibleColors.add(Color.PURPLE);
			possibleColors.add(brown);
			possibleColors.add(pink);
			possibleColors.add(Color.WHITE);
		}
		return returnme;
	}
	
	public static String getColorString(){
		s=r.nextInt(9);
		switch (s){
			case 0: return "red";
			case 1: return "orange";
			case 2: return "yellow";
			case 3: return "green";
			case 4: return "blue";
			case 5: return "purple";
			case 6: return "brown";
			case 7: return "white";
			case 8: return "pink";
			default: return null;
			}
		}
	
	public static String getColorString(Color c){
		if(c.equals(red))
			return "red";
		if(c.equals(orange))
			return "orange";
		if(c.equals(Color.YELLOW))
			return "yellow";
		if(c.equals(green))
			return "green";
		if(c.equals(Color.BLUE))
			return "blue";
		if(c.equals(Color.PURPLE))
			return "purple";
		if(c.equals(Color.WHITE))
			return "white";
		if(c.equals(brown))
			return "brown";
		if(c.equals(pink))
			return "pink";
		//Black is used for "bomb" mode.
		if(c.equals(Color.BLACK))
			return "nothing";
		else return null;
		}
	
	}

