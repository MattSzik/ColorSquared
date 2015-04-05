package com.tb.gameworld;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.tb.cg.ColorGame;
import com.tb.cghelpers.AssetLoader;
import com.tb.gameobjects.ColorGrid;
import com.tb.gameobjects.ColorLine;
import com.tb.gameobjects.ColorPanel;
import com.tb.gameobjects.ColorSelector;
import com.tb.gameobjects.GameOverPanel;
import com.tb.gameobjects.GameTimer;
import com.tb.ui.ListenerManager.ListenerType;
import com.tb.ui.SimpleButton;

public class GameWorld {
	private ColorGrid grid1, grid2;
	private Vector2 midPoint, offScreen;
	private ColorSelector cs;
	private Color currentColor, textColor;
	private int score, i, currentHS, screenShake;
	private GameTimer timer;
	private GameState currentState;
	private GameOverPanel gop;
	private ColorPanel cPanel;
	private Random r;
	private SimpleButton retry, menu, classic, fours, blitz, wild;
	private GameMode currentMode;
	private boolean isMuted, isRestarted, backPressed, difficultyVisible, moreVisible;
	private ColorGame game;

	public enum GameState {
		MENU, RUNNING, GAMEOVER
	}

	// Game modes.
	public enum GameMode {
		NONE, NORMAL, FOURBYFOUR, BLITZ, WILD, BOMB, RAINBOW
	}

	public GameWorld(Vector2 midPoint, ColorGame game) {
		this.game = game;
		currentMode = GameMode.NONE;
		currentState = GameState.MENU;
		this.midPoint = midPoint;
		offScreen = new Vector2(midPoint.x + 160, midPoint.y);
		grid1 = new ColorGrid(midPoint, 40, false);
		grid2 = new ColorGrid(offScreen, 40, false);
		cs = new ColorSelector(new Random());
		setCurrentColor(grid1);
		score = 0;
		i = 0;
		timer = new GameTimer(46, 0);
		timer.stop();
		isRestarted = false;
		screenShake = 0;
		gop = new GameOverPanel(offScreen.x);
		cPanel = new ColorPanel(new Vector2(midPoint.x, midPoint.y));
		retry = new SimpleButton(gop.getRX(), midPoint.y + 5, 40, 15);
		menu = new SimpleButton(gop.getMX(), midPoint.y + 5, 40, 15);
		classic = new SimpleButton(midPoint.x-50, 30, 50,20);
		fours = new SimpleButton(midPoint.x+20, 30, 50,20);
		blitz = new SimpleButton(midPoint.x-50, 55, 50,20);
		wild = new SimpleButton(midPoint.x+20, 55, 50,20);
		currentHS = 0;
		difficultyVisible = false;
		isMuted = false;
		moreVisible = false;
		backPressed = false;
		r = new Random();
	}

	public void update(float delta) {
		cPanel.update(delta);
		switch (currentState) {
		case MENU:
			updateMenu(delta);
			break;
		case RUNNING:
		case GAMEOVER:
			updateRunning(delta);
			break;
		default:
			break;
		}
		
		if(cPanel.isMovingOffScreen() && (!isRunning() || backPressed)){
			if(!isRestarted){
			restart(getMode(), getTimer().getSpeed());
			isRestarted = true;
			}
			setDifficultyVisible(false);
			if(currentMode==GameMode.NONE){
				goToMenu();
			}
		}	
		if(cPanel.isOffScreen()){
			cPanel.restart();
			isRestarted = false;
		}
	}

	public void updateMenu(float delta) {
		
		//TODO: Move menus, choices, etc.
	}

	public void updateRunning(float delta) {
		if (delta > .15)
			delta = .15f;
		timer.update(delta);
		grid1.update(delta);
		grid2.update(delta);
		if (isGameOver()) {
			if (i < 20) {
				// Shake the screen
				if (i % 2 == 0) {
					grid1.setPosition(3);
					grid2.setPosition(3);
					screenShake = 3;
					i++;
				} else {
					grid1.setPosition(-3);
					grid2.setPosition(-3);
					screenShake = -3;
					i++;
				}
			}
			// Brief pause
			else if (50 > i && i >= 20) {
				i++;
			}
			// Move grid offscreen, gameover panel onscreen.
			// TODO: Move gameover panel onscreen.
			else if (i == 50) {
				screenShake -= 5;
				if (gop.getGOX() > midPoint.x - 45) {
					gop.update(5);
					retry.setPosition(retry.getX() - 5, retry.getY());
					menu.setPosition(menu.getX() - 5, menu.getY());
				} else {
					gop.update(gop.getGOX() - midPoint.x + 45);
					retry = new SimpleButton(gop.getGOX(), midPoint.y + 5, 40,
							15);
					menu = new SimpleButton(gop.getMX(), midPoint.y + 5, 40, 15);
				}
				if (score % 2 == 0){
					grid1.setPosition(-5);
				}
				else{
					grid2.setPosition(-5);
				}
			}
		}
		// Loss by time out.
		if (timer.isOffScreen() && currentState != GameState.GAMEOVER) {
			ColorGame.listenerManager.call(ListenerType.SHOWAD);
			if(!isMuted())
			AssetLoader.wrong.play();
			gameOver();
		}

		// Stop moving grids in the center.
		if(currentMode != GameMode.FOURBYFOUR){
		if (currentState != GameState.GAMEOVER && !grid1.isClicked()
				&& !grid1.isStopped()
				&& grid1.getList().get(0).getPosition().x < midPoint.x - 20) {
			grid1.setPosition(-(int) (grid1.getList().get(0).getPosition().x - (midPoint.x - 20)));
			grid1.stopGrid();
			if (score != 0)
				timer.restart();

			grid2.reset(midPoint.x + 160);
		}
		if (currentState != GameState.GAMEOVER && !grid2.isClicked()
				&& !grid2.isStopped()
				&& grid2.getList().get(0).getPosition().x <= midPoint.x - 20) {
			grid2.setPosition(-(int) (grid2.getList().get(0).getPosition().x - (midPoint.x - 20)));
			grid2.stopGrid();
			timer.restart();
			grid1.reset(midPoint.x + 160);
		}
		}
		else{
			if (currentState != GameState.GAMEOVER && !grid1.isClicked()
					&& !grid1.isStopped()
					&& grid1.getList().get(9).getPosition().x < midPoint.x) {
				grid1.setPosition(-(int) (grid1.getList().get(9).getPosition().x - (midPoint.x-30)));
				grid1.stopGrid();
				if (score != 0)
					timer.restart();

				grid2.reset(midPoint.x + 160);

			}
			if (currentState != GameState.GAMEOVER && !grid2.isClicked()
					&& !grid2.isStopped()
					&& grid2.getList().get(9).getPosition().x <= midPoint.x) {
				grid2.setPosition(-(int) (grid2.getList().get(9).getPosition().x - (midPoint.x-30)));
				grid2.stopGrid();
				timer.restart();

				grid1.reset(midPoint.x + 160);
		}
		}
	}

	public ColorGrid getGrid1() {
		return grid1;
	}

	public ColorGrid getGrid2() {
		return grid2;
	}

	public Vector2 getMidpoint() {
		return midPoint;
	}

	public Color getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(ColorGrid currentGrid) {
		if(currentMode != GameMode.BOMB)
		do {
			currentColor = cs.getColor();
		} while (!currentGrid.isInGrid(currentColor));
		else
			if(r.nextInt(9)==9)
				currentColor = Color.BLACK; //The "bomb," will instruct user to push nothing.
			else
				currentColor = cs.getColor();
		//If playing Wild mode or current grid is a "bomb", make color random.
		if(currentMode == GameMode.WILD || currentColor == Color.BLACK) 
			textColor = cs.getColor();
		else //Otherwise, match the current color.
			textColor = currentColor;
	}

	public void incrementScore() {
		score += 1;
	}

	public int getScore() {
		return score;
	}

	public void goToMenu() {
		currentState = GameState.MENU;
	}

	public void gameOver() {
		currentState = GameState.GAMEOVER;
		//Ask user to rate after 40 plays.
		if(AssetLoader.isForty())
			game.actionResolver.askForRating();
		AssetLoader.incrementPlays();
		game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQAQ"); //Achievement for tapping one square.
		timer.stop();
		//Update high score if needed.
		if(score > AssetLoader.getHighScore(currentMode, timer.getSpeed()))
		AssetLoader.setHighScore(currentMode, timer.getSpeed(), score);
		//Achievement handling.
		if(timer.getSpeed() == 136 && score >= 100)
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQAg"); //Easy master
		if(timer.getSpeed() == 170 && score >= 75) //Medium Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQAw");
		if(timer.getSpeed() == 227 && currentMode == GameMode.NORMAL && score >= 50) //Classic Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQBA");
		if(timer.getSpeed() == 227 && currentMode == GameMode.FOURBYFOUR && score >= 30) //4x4 Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQBQ");
		if(timer.getSpeed() == 13.6f && currentMode == GameMode.BLITZ && score >= 25) //Blitz Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQBg");
		if(timer.getSpeed() == 227 && currentMode == GameMode.WILD && score >= 50) //Wild Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQBw");
		if(timer.getSpeed() == 227 && score >=75) //Hard Master
			game.actionResolver.unlockAchievementGPGS("CgkIreP2hdQEEAIQFA");
		currentHS = AssetLoader.getHighScore(currentMode, timer.getSpeed());
	
		//Submit score to Google Leaderboard.
		if(timer.getSpeed() == 136 && currentMode == GameMode.NORMAL){
			game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCA"); //Classic easy
			game.actionResolver.submitScoreGPGS(currentHS);
		}
		if(timer.getSpeed() == 170 && currentMode == GameMode.NORMAL){
			game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCQ"); //Classic medium
			game.actionResolver.submitScoreGPGS(currentHS);
		}
		if(timer.getSpeed() == 227 && currentMode == GameMode.NORMAL){ //Classic hard
			game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCg");
			game.actionResolver.submitScoreGPGS(currentHS);
		}
			
			if(timer.getSpeed() == 136 && currentMode == GameMode.FOURBYFOUR){
				game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQCw"); //4x4 easy
				game.actionResolver.submitScoreGPGS(currentHS);
			}
			if(timer.getSpeed() == 170 && currentMode == GameMode.FOURBYFOUR){
				game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDA"); //4x4 medium
				game.actionResolver.submitScoreGPGS(currentHS);
			}
				if(timer.getSpeed() == 227 && currentMode == GameMode.FOURBYFOUR){ //4x4 hard
				game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDQ");
				game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 4.5f && currentMode == GameMode.BLITZ){
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDg"); //Blitz easy
					game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 6.8f && currentMode == GameMode.BLITZ){
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQDw"); //Blitz medium
					game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 13.6f && currentMode == GameMode.BLITZ){ //Blitz hard
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEA");
					game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 136 && currentMode == GameMode.WILD){
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEQ"); //Wild easy
					game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 170 && currentMode == GameMode.WILD){
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEg"); //Wild medium
					game.actionResolver.submitScoreGPGS(currentHS);
				}
				if(timer.getSpeed() == 227 && currentMode == GameMode.WILD){ //Wild hard
					game.actionResolver.setCurrentLB("CgkIreP2hdQEEAIQEw");
					game.actionResolver.submitScoreGPGS(currentHS);
				}
	}

	public boolean isGameOver() {
		return currentState == GameState.GAMEOVER;
	}

	public boolean isMenu() {
		return currentState == GameState.MENU;
	}

	public boolean isRunning() {
		return currentState == GameState.RUNNING;
	}

	public void start() {
		currentState = GameState.RUNNING;
	}

	public void restart(GameMode myMode, float timerSpeed) {
		currentState = GameState.RUNNING;
		textColor = Color.WHITE;
		score = 0;
		if(myMode != GameMode.FOURBYFOUR){
		grid1 = new ColorGrid(midPoint, 40,false);
		grid2 = new ColorGrid(offScreen, 40,false);
		}
		else{
			grid1 = new ColorGrid(midPoint, 30, true);
			grid2 = new ColorGrid(midPoint, 30, true);
		}
		setCurrentColor(grid1);
		i = 0;
		timer = new GameTimer(timerSpeed, 0);
		timer.stop();
		screenShake = 0;
		gop = new GameOverPanel(offScreen.x);
		retry = new SimpleButton(gop.getRX(), midPoint.y + 5, 40, 15);
		menu = new SimpleButton(gop.getMX(), midPoint.y + 5, 40, 15);
		backPressed = false;
		// TODO: Randomize background color?
	}

	public GameTimer getTimer() {
		return timer;
	}

	public float getScreenShake() {
		return screenShake;
	}

	public GameOverPanel getGOP() {
		return gop;
	}

	public SimpleButton getMenuButton() {
		return menu;
	}

	public SimpleButton getRetryButton() {
		return retry;
	}
	
	public SimpleButton getClassicButton(){
		return classic;
	}
	
	public SimpleButton getFoursButton(){
		return fours;
	}
	
	public SimpleButton getBlitzButton(){
		return blitz;
	}
	
	public SimpleButton getWildButton(){
		return wild;
	}
	
	public GameMode getMode(){
		return currentMode;
	}
	
	public void setCurrentMode(GameMode mode){
		currentMode = mode;
	}
	
	public ColorPanel getCPanel(){
		return cPanel;
	}
	
	public Color getTextColor(){
		return textColor;
	}
	
	public void setDifficultyVisible(boolean b){
		difficultyVisible = b;
	}
	
	public boolean difficultyVisible(){
		return difficultyVisible;
	}
	
	public int getCurrentHS(){
		return currentHS;
	}
	
	public void toggleMute(){
		if(isMuted)
			isMuted=false;
		else
			isMuted=true;
	}
	
	public boolean isMuted(){
		return isMuted;
	}
	
	public void pressBack(){
		backPressed = true;
	}
	
	public void viewMore(){
		if(!moreVisible)
			moreVisible = true;
		else
			moreVisible = false;
	}
	
	public boolean moreVisible(){
		return moreVisible;
	}

}
