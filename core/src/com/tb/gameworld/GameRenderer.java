package com.tb.gameworld;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tb.cghelpers.AssetLoader;
import com.tb.gameobjects.ColorGrid;
import com.tb.gameobjects.ColorLine;
import com.tb.gameobjects.ColorPanel;
import com.tb.gameobjects.ColorSelector;
import com.tb.gameobjects.ColorSquare;
import com.tb.gameworld.GameWorld.GameMode;

public class GameRenderer {
	private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private float gameHeight, gameWidth;
    private ArrayList<ColorSquare> grid1, grid2;
    private ColorSelector cs;
    private Vector2 midPoint;
    private ColorPanel cPanel;
    private ColorLine cLine1, cLine2, cLine3, cLine4;
    // Game Objects
    //TO-DO: Transparent square when squares are pressed. Make it a dark gray, that way it darkens any color.
    
    public GameRenderer(GameWorld world, float gameHeight, float gameWidth, Vector2 midPoint){
    	myWorld = world;
    	cam = new OrthographicCamera();
    	cam.setToOrtho(true);
    	cam.viewportHeight = gameHeight;
    	cam.viewportWidth=gameWidth;
    	cam.position.set(cam.viewportWidth * .5f,cam.viewportHeight * .5f, 0f);
    	cam.update();
    	batcher = new SpriteBatch();
    	batcher.setProjectionMatrix(cam.combined);
    	shapeRenderer = new ShapeRenderer();
    	shapeRenderer.setProjectionMatrix(cam.combined);
    	
    	this.gameHeight = gameHeight;
    	this.gameWidth = gameWidth;
    	grid1 = myWorld.getGrid1().getList();
    	grid2 = myWorld.getGrid2().getList();
    	this.midPoint = midPoint;
    	cPanel = myWorld.getCPanel();
    	cs = myWorld.getGrid1().getSelector();
    	cLine1 = new ColorLine(0,0,midPoint.y,80);
    	cLine2 = new ColorLine(0,midPoint.y*-2,midPoint.y, 80);
    	cLine3 = new ColorLine(midPoint.x*2-20,0,midPoint.y,-80);
    	cLine4 = new ColorLine(midPoint.x*2-20,2*midPoint.y,midPoint.y, -80);
    }
    
    public void render(float delta, float runTime){
    	grid1 = myWorld.getGrid1().getList();
    	grid2 = myWorld.getGrid2().getList();
    	myWorld.update(delta);
    	Gdx.gl.glClearColor(0,0,0,1);
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	shapeRenderer.begin(ShapeType.Filled);
    	
    	//Draw background
    	if(!myWorld.isGameOver()){
    	shapeRenderer.setColor(ColorSelector.slategray);
    	}
    	else{
    		shapeRenderer.setColor(.5f,0,0,1);
    	}
    	shapeRenderer.rect(0,0,gameHeight, gameHeight);
    	shapeRenderer.end();
    	
    	cPanel= myWorld.getCPanel();
    	//TO-DO: Change to update when states are fleshed out.
    	if(myWorld.isMenu()){
    	cLine1.update(delta);
    	cLine2.update(delta);
    	cLine3.update(delta);
    	cLine4.update(delta);
    	if(cLine1.isOffScreen())
    		cLine1.reset((midPoint.y*-2)+(cLine1.y-midPoint.y*2));
    	if(cLine2.isOffScreen())
    		cLine2.reset(midPoint.y*-2+(cLine2.y-midPoint.y*2));
    	if(cLine3.isOffScreen())
    		cLine3.reset(midPoint.y*2-(midPoint.y*-2-cLine3.y));
    	if(cLine4.isOffScreen())
    		cLine4.reset(midPoint.y*2-(midPoint.y*-2-cLine4.y));
    	drawMenu();
    	}
    	if(myWorld.isGameOver()){
    		drawGameOver();
    	}
    	//Draw the grid.
    	if(myWorld.isRunning() || myWorld.isGameOver()){
        	//Draw text above the grid
        	drawInstruction();
        	drawScore();
    	shapeRenderer.begin(ShapeType.Filled);
        drawGrid(grid1);
    	drawGrid(grid2);	
    	shapeRenderer.end();
    	}
    	shapeRenderer.begin(ShapeType.Filled);
    	for(ColorGrid cg: cPanel.getColorGridList()){
    		drawGrid(cg.getList());
    	}
    	shapeRenderer.end();
    	
   }
    	

    
    public void drawInstruction(){
    	int length = ("Press ".length() + cs.getColorString(myWorld.getCurrentColor()).length());
    	batcher.begin();
    	AssetLoader.font.setColor(myWorld.getTextColor());
    	AssetLoader.font.draw(batcher, "press " + cs.getColorString(myWorld.getCurrentColor()),
    			midPoint.x-(5*length)+myWorld.getScreenShake(), midPoint.y-50);
    	batcher.draw(AssetLoader.timer, myWorld.getTimer().getPosition(), 0, gameWidth, 2.5f);
    	batcher.end();
    }
    
    public void drawScore(){
    	batcher.begin();
    	AssetLoader.font.setColor(Color.WHITE);
    	int length = (""+myWorld.getScore()).length();
    	AssetLoader.font.draw(batcher, ""+myWorld.getScore(), midPoint.x-(4*length)+myWorld.getScreenShake(), midPoint.y-80);
    	batcher.end();
    }
    
    public void drawGameOver(){
    	batcher.begin();
    	AssetLoader.font.setColor(Color.WHITE);
    	AssetLoader.font.draw(batcher, "game over", myWorld.getGOP().getGOX(), midPoint.y-40);
    	AssetLoader.smallfont.draw(batcher, "score: " + myWorld.getScore(), myWorld.getGOP().getSX(), midPoint.y-20);
    	AssetLoader.smallfont.draw(batcher, "high score: " + myWorld.getCurrentHS(), myWorld.getGOP().getSX(), midPoint.y-10);
    	AssetLoader.smallfont.draw(batcher, "retry", myWorld.getGOP().getSX(), midPoint.y+10);
    	
    	//Dimensions for buttons. Use as reference
    	shapeRenderer.setColor(Color.WHITE);
    	AssetLoader.smallfont.draw(batcher, "menu", myWorld.getGOP().getMX(), midPoint.y+10);
    	batcher.end();
    }
    
    public void drawMenu(){
    	batcher.begin();
    	batcher.draw(AssetLoader.colorline, 0,cLine1.getY(), 20, midPoint.y*2);
    	batcher.draw(AssetLoader.colorline, 0, cLine2.getY(), 20, midPoint.y*2);
    	batcher.draw(AssetLoader.colorline, midPoint.x*2-20,cLine3.getY(), 20, midPoint.y*2);
    	batcher.draw(AssetLoader.colorline, midPoint.x*2-20, cLine4.getY(), 20, midPoint.y*2);
    	if(!myWorld.moreVisible()){
    	AssetLoader.smallfont.draw(batcher, "classic", midPoint.x-45, 40);
    	AssetLoader.smallfont.draw(batcher, "4x4", midPoint.x+18, 40);
    	AssetLoader.smallfont.draw(batcher, "blitz", midPoint.x-45, 65);
    	AssetLoader.smallfont.draw(batcher, "wild", midPoint.x+18, 65);
    	AssetLoader.smallfont.draw(batcher, "more", midPoint.x-13, 90);
    	}
    	else{
    		AssetLoader.smallfont.draw(batcher, "rainbow", midPoint.x-45, 40);
        	AssetLoader.smallfont.draw(batcher, "bomb", midPoint.x+18, 40);
        	AssetLoader.smallfont.draw(batcher, "mode3", midPoint.x-45, 65);
        	AssetLoader.smallfont.draw(batcher, "mode4", midPoint.x+18, 65);
        	AssetLoader.smallfont.draw(batcher, "back", midPoint.x-13, 90);	
    	}
    	AssetLoader.font.draw(batcher, "color", midPoint.x-25, 15);
    	AssetLoader.smallfont.draw(batcher, "2", midPoint.x+25, 10);
    	if(myWorld.difficultyVisible()){
    			if(myWorld.getMode() == GameMode.NORMAL){
    			AssetLoader.smallestfont.draw(batcher, "classic:", midPoint.x-17, 110);
    			AssetLoader.smallestfont.draw(batcher, "hit the right color!", midPoint.x-42, 120);
    			}
    			if(myWorld.getMode() == GameMode.BLITZ){
    			AssetLoader.smallestfont.draw(batcher, "blitz:", midPoint.x-12, 110);
        		AssetLoader.smallestfont.draw(batcher, "go as fast as you can!", midPoint.x-45, 120);
    			}
    			if(myWorld.getMode() == GameMode.FOURBYFOUR){
    			AssetLoader.smallestfont.draw(batcher, "4x4:", midPoint.x-8, 110);
        		AssetLoader.smallestfont.draw(batcher, "more squares!", midPoint.x-29, 120);
    			}
    			if(myWorld.getMode() == GameMode.WILD){
    			AssetLoader.smallestfont.draw(batcher, "wild:", midPoint.x-10, 110);
        		AssetLoader.smallestfont.draw(batcher, "don't get tricked!", midPoint.x-37, 120);
    			}
    		
    		AssetLoader.smallfont.draw(batcher, "easy", midPoint.x-45, 140);
    		AssetLoader.smallfont.draw(batcher, "medium", midPoint.x-21, 160);
    		AssetLoader.smallfont.draw(batcher, "hard", midPoint.x+18, 140);
    	}
    	

    	if(!myWorld.isMuted()){
    	batcher.draw(AssetLoader.muteoffRegion, midPoint.x*2-40, midPoint.y*2-20, 20, 20);
    	}
    	else
    		batcher.draw(AssetLoader.muteonRegion, midPoint.x*2-40, midPoint.y*2-20, 20, 20);
    	batcher.draw(AssetLoader.settings, 25, midPoint.y*2-20, 20,20);
    	batcher.draw(AssetLoader.leaderboardRegion, 50,midPoint.y*2-21, 19, 19);
    	batcher.draw(AssetLoader.achievements, 77,midPoint.y*2-17,15,15);
    	batcher.end();
    }
    
    public void drawGrid(ArrayList<ColorSquare> cg){
    	cPanel = myWorld.getCPanel();
    	for(ColorSquare c: cg){
		shapeRenderer.setColor(c.getColor());
		shapeRenderer.rect(c.getPosition().x, c.getPosition().y,
				c.getWidth(),c.getHeight());
		//Draw the grid lines
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.line(c.getPosition().x, c.getPosition().y, 
				c.getPosition().x, c.getPosition().y+c.getHeight());
		
		shapeRenderer.line(c.getPosition().x, c.getPosition().y, 
				c.getPosition().x+c.getWidth(), c.getPosition().y);
		
		shapeRenderer.line(c.getPosition().x, c.getPosition().y+c.getHeight(), 
				c.getPosition().x+c.getWidth(), c.getPosition().y+c.getHeight());
		
		shapeRenderer.line(c.getPosition().x+c.getWidth(), c.getPosition().y, 
				c.getPosition().x+c.getWidth(), c.getPosition().y+c.getHeight());
	}
    }
}
