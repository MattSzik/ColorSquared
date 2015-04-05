package com.tb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.tb.cg.ColorGame;
import com.tb.cghelpers.InputHandler;
import com.tb.gameworld.GameRenderer;
import com.tb.gameworld.GameWorld;
//import com.tb.gameworld.GameRenderer;
//import com.tb.gameworld.GameWorld;
//import com.tb.zbHelpers.InputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;
    float screenWidth, screenHeight, gameWidth, gameHeight;
    
    // This is the constructor, not the class declaration
    public GameScreen(ColorGame game) {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        gameWidth = 136;
        gameHeight = screenHeight / (screenWidth / gameWidth);
        Vector2 midPoint = new Vector2(gameWidth/2, gameHeight/2);
        world = new GameWorld(midPoint, game);

        Gdx.input.setInputProcessor(new InputHandler(game, this, world, screenWidth / gameWidth, 
        		screenHeight / gameHeight));
        renderer = new GameRenderer(world, gameHeight, gameWidth, midPoint);

    }

    @Override
    public void render(float delta) {
        runTime += delta;
      //  world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // Leave blank
    }
    
    public float getScreenHeight(){
    	return screenHeight;
    }
    
    public GameWorld getWorld(){
    	return world;
    }
}