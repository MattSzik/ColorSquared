package com.tb.cghelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.tb.cg.ColorGame;
import com.tb.gameobjects.ColorGrid;
import com.tb.gameworld.GameWorld;
import com.tb.gameworld.GameWorld.GameMode;
import com.tb.screens.GameScreen;
import com.tb.screens.SettingsScreen;
import com.tb.ui.ListenerManager.ListenerType;
import com.tb.ui.SimpleButton;

public class InputHandler implements InputProcessor {
	private static GameWorld myWorld;
	private static ColorGame game;
	private static ColorGrid grid1, grid2;
	private static Vector2 midPoint;
	private SimpleButton easyButton, medButton, hardButton, muteButton, 
	settingsButton, achButton, leaderButton, moreButton;
	//Misc buttons
	
	
	private static float scaleFactorX;
	private static float scaleFactorY;
	
	public InputHandler(ColorGame game, GameScreen screen, GameWorld myWorld, float scaleFactorX,
			float scaleFactorY){
		this.myWorld=myWorld;
		this.game = game;
		this.scaleFactorX = scaleFactorX;
		this.scaleFactorY = scaleFactorY;
		this.myWorld = myWorld;
		this.grid1 = myWorld.getGrid1();
		this.grid2 = myWorld.getGrid2();
		midPoint = myWorld.getMidpoint();
		easyButton = new SimpleButton(midPoint.x-60, 130, 40, 30);
		medButton = new SimpleButton(midPoint.x-20, 150, 40, 30);
		hardButton = new SimpleButton(midPoint.x+20, 130, 40, 30);
		muteButton = new SimpleButton(midPoint.x*2-40, midPoint.y*2-20, 20, 20);
		settingsButton = new SimpleButton(20, midPoint.y*2-20,20,20);
		achButton = new SimpleButton(75, midPoint.y*2-20,20,20);
		leaderButton = new SimpleButton(45, midPoint.y*2-20,25,20);
		moreButton = new SimpleButton(midPoint.x-15,85, 15,15);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.BACK || keycode == Keys.BACKSPACE){
			ColorGame.listenerManager.call(ListenerType.HIDEAD);
			if(!myWorld.isMenu()){
			myWorld.pressBack();
			myWorld.setCurrentMode(GameMode.NONE);
			myWorld.getTimer().stop();
			if(!myWorld.isMuted())
				AssetLoader.menu.play();
			myWorld.getCPanel().movePanel();
			if(myWorld.isGameOver())
				ColorGame.listenerManager.call(ListenerType.HIDEAD);
			}
			else{
				if(myWorld.difficultyVisible())
					myWorld.setDifficultyVisible(false);
				else
				Gdx.app.exit();
			}
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
		if(myWorld.isMenu()){
			if(muteButton.isTouchDown(screenX, screenY)){
				myWorld.toggleMute();
			}
			if(settingsButton.isTouchDown(screenX, screenY)){
				game.setScreen(new SettingsScreen(game));
			}
			if(achButton.isTouchDown(screenX, screenY)){
				game.actionResolver.getAchievementsGPGS();
			}
			if(leaderButton.isTouchDown(screenX, screenY)){
				game.actionResolver.getLeaderboardGPGS();
				myWorld.setCurrentMode(GameMode.NONE);
				myWorld.setDifficultyVisible(false);
			}
			if(myWorld.getClassicButton().isTouchDown(screenX, screenY)){
				myWorld.setCurrentMode(GameMode.NORMAL);
				myWorld.setDifficultyVisible(true);
			}
			if(myWorld.getFoursButton().isTouchDown(screenX, screenY)){
				myWorld.setCurrentMode(GameMode.FOURBYFOUR);
				myWorld.setDifficultyVisible(true);
			}
			if(myWorld.getBlitzButton().isTouchDown(screenX, screenY)){
				myWorld.setDifficultyVisible(true);
				myWorld.setCurrentMode(GameMode.BLITZ);
			}
			if(myWorld.getWildButton().isTouchDown(screenX, screenY)){
				myWorld.setDifficultyVisible(true);
				myWorld.setCurrentMode(GameMode.WILD);
			}
			
			if(moreButton.isTouchDown(screenX, screenY)){
				System.out.println("More pressed.");
				myWorld.viewMore();
				System.out.println(myWorld.moreVisible());
			}
			
			if(myWorld.difficultyVisible()){
				if(easyButton.isTouchDown(screenX, screenY)){
					
						if(myWorld.getMode() == GameMode.NORMAL)
							game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCA");
						if(myWorld.getMode() == GameMode.FOURBYFOUR)
							game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCw");
						if(myWorld.getMode() == GameMode.WILD)
							game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEQ");
					if(!myWorld.isMuted())
					AssetLoader.menu.play();
					if(myWorld.getMode() !=GameMode.BLITZ)
						myWorld.getTimer().setSpeed(136); //1s
					else{
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDg");
						myWorld.getTimer().setSpeed(4.5f); //30s
					}
						myWorld.getCPanel().movePanel();
				}
				if(medButton.isTouchDown(screenX, screenY)){
					if(!myWorld.isMuted())
					AssetLoader.menu.play();
					if(myWorld.getMode() == GameMode.NORMAL)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCQ");
					if(myWorld.getMode() == GameMode.FOURBYFOUR)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDA");
					if(myWorld.getMode() == GameMode.WILD)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEg");
					if(myWorld.getMode() !=GameMode.BLITZ)
						myWorld.getTimer().setSpeed(170); //.8s
					else{
						myWorld.getTimer().setSpeed(6.8f); //20s
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDw");
						}
						myWorld.getCPanel().movePanel();
				}
				if(hardButton.isTouchDown(screenX,screenY)){ 
					if(!myWorld.isMuted())
					AssetLoader.menu.play();
					if(myWorld.getMode() == GameMode.NORMAL)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCg");
					if(myWorld.getMode() == GameMode.FOURBYFOUR)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDQ");
					if(myWorld.getMode() == GameMode.WILD)
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEw");
					if(myWorld.getMode() !=GameMode.BLITZ)
						myWorld.getTimer().setSpeed(227); //0.6s
					else{
						myWorld.getTimer().setSpeed(13.6f); //10s
						game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEA");
					}
						myWorld.getCPanel().movePanel();
				}
			}
		}
			
	
		
		
		if(myWorld.isRunning() || myWorld.isGameOver()){
		grid1 = myWorld.getGrid1();
		grid2 = myWorld.getGrid2();
		if(!myWorld.isGameOver()){
		for(int i =0; i< grid1.getList().size(); i++){
			
			if(grid1.getList().get(i).isClicked(screenX, screenY))
				if(grid1.getList().get(i).onClick(myWorld.getCurrentColor())){
					if(!myWorld.isMuted())
					AssetLoader.correct.play(.3f);
					myWorld.incrementScore();
					if(myWorld.getMode() != GameMode.BLITZ)
					myWorld.getTimer().reset();
					myWorld.getTimer().stop();
					myWorld.getGrid1().onClick();
					myWorld.getGrid1().moveGrid();
					myWorld.setCurrentColor(myWorld.getGrid2());
					myWorld.getGrid2().moveGrid();
				} else{
					//Game over via choice.
					ColorGame.listenerManager.call(ListenerType.SHOWAD);
					myWorld.getGrid1().onClick();
					if(!myWorld.isMuted())
					AssetLoader.wrong.play();
					myWorld.gameOver();
				}
		}
		for(int i =0; i< grid2.getList().size(); i++){
			if(grid2.getList().get(i).isClicked(screenX, screenY))
				if(myWorld.getGrid2().getList().get(i).onClick(myWorld.getCurrentColor())){
					if(!myWorld.isMuted())
					AssetLoader.correct.play(.3f);
					myWorld.incrementScore();
					if(myWorld.getMode()!= GameMode.BLITZ)
					myWorld.getTimer().reset();
					myWorld.getTimer().stop();
					myWorld.getGrid2().onClick();
					myWorld.getGrid2().moveGrid();
					myWorld.setCurrentColor(myWorld.getGrid1());
					myWorld.getGrid1().moveGrid();
				} else{
					//Game over via wrong choice
					ColorGame.listenerManager.call(ListenerType.SHOWAD);
					myWorld.getGrid2().onClick();
					if(!myWorld.isMuted())
					AssetLoader.wrong.play();
					myWorld.gameOver();
				}
		}
		}
		if(myWorld.isGameOver()){
			if(myWorld.getMenuButton().isClicked(screenX, screenY)){
				ColorGame.listenerManager.call(ListenerType.HIDEAD);
				myWorld.setCurrentMode(GameMode.NONE);
				if(!myWorld.isMuted())
				AssetLoader.menu.play();
				myWorld.getCPanel().movePanel();
			}
			
			if(myWorld.getRetryButton().isClicked(screenX, screenY)){
				ColorGame.listenerManager.call(ListenerType.HIDEAD);
				if(!myWorld.isMuted())
				AssetLoader.menu.play();
				myWorld.getCPanel().movePanel();
				//TODO: Use the same timer speed myWorld currently has.
			}
		}}
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

