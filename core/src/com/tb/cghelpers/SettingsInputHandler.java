package com.tb.cghelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.tb.cg.ColorGame;
import com.tb.screens.GameScreen;
import com.tb.ui.SimpleButton;

public class SettingsInputHandler implements InputProcessor {
	
	ColorGame game;
	private static SimpleButton facebookButton, twitterButton, rateButton, emailButton, backButton;
	private static float scaleFactorX, scaleFactorY;
	
	public SettingsInputHandler(ColorGame game, float scaleFactorX, float scaleFactorY, float screenWidth){
		this.game=game;
		emailButton = new SimpleButton(0,18, screenWidth, 24);
		facebookButton = new SimpleButton(0,42, screenWidth, 22);
		twitterButton = new SimpleButton(0,62, screenWidth, 22);
		rateButton = new SimpleButton(0,100, screenWidth, 22);
		backButton = new SimpleButton(0, 0, 18, 18);
		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.BACKSPACE){
			game.setScreen(new GameScreen(game));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		screenX = scaleX(screenX);
		screenY = scaleY(screenY);
		if(facebookButton.isTouchDown(screenX, screenY)){
			Gdx.net.openURI("https://www.facebook.com/pages/TimeBot-Games/570118746449949?ref=tn_tnmn");
		} 
		if(twitterButton.isTouchDown(screenX, screenY)){
			Gdx.net.openURI("https://twitter.com/TimeBotApps");
		}
		if(emailButton.isTouchDown(screenX, screenY)){
			Gdx.net.openURI("mailto:timebotgames@gmail.com");
		}
		if(rateButton.isTouchDown(screenX, screenY)){
			System.out.println("Rate pressed.");
			Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.tb.cg.android&hl=en");
			//TODO: If Android, open Play Store url. If IOS, open App Store url. Will be implemented postlaunch
			
		}
		if(backButton.isTouchDown(screenX, screenY)){
			game.setScreen(new GameScreen(game));
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private static int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

}
