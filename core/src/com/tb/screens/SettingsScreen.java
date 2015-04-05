package com.tb.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.tb.cg.ColorGame;
import com.tb.cghelpers.SettingsInputHandler;
import com.tb.gameworld.GameWorld;
import com.tb.gameworld.SettingsRenderer;
//import com.tb.gameworld.SettingsRenderer;
//import com.tb.gameworld.SettingsWorld;
//import com.tb.zbHelpers.InputHandler;

public class SettingsScreen implements Screen {

    private GameWorld world;
    private SettingsRenderer renderer;
    private float runTime;
    float screenWidth, screenHeight, gameWidth, gameHeight;

    // This is the constructor, not the class declaration
    public SettingsScreen(ColorGame game) {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        gameWidth = 136;
        gameHeight = screenHeight / (screenWidth / gameWidth);
        Vector2 midPoint = new Vector2(gameWidth/2, gameHeight/2);

        Gdx.input.setInputProcessor(new SettingsInputHandler(game, screenWidth / gameWidth, 
        		screenHeight / gameHeight, screenWidth));
        renderer = new SettingsRenderer(gameHeight, gameWidth, midPoint);

    }

    @Override
    public void render(float delta) {
        runTime += delta;
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

}