package com.tb.cg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tb.cghelpers.ActionResolver;
import com.tb.cghelpers.AssetLoader;
import com.tb.screens.GameScreen;
import com.tb.ui.Listener;
import com.tb.ui.ListenerManager;

public class ColorGame extends Game {
	SpriteBatch batch;
	public static ListenerManager listenerManager;
	GameScreen myScreen;
	boolean adIsShowing;
	public ActionResolver actionResolver;
	
	public ColorGame(ActionResolver actionResolver){
		this.actionResolver = actionResolver; //Used to implement platform-specific code.
		listenerManager = new ListenerManager();
	}
	
	
	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
		AssetLoader.load();
		setScreen(new GameScreen(this));
		adIsShowing = false;
	}
	
	@Override
	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}
	
    public void addListener(Listener l) {
        listenerManager.add(l);
    }
    
    
}