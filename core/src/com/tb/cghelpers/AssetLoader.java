package com.tb.cghelpers;
//Class responsible for loading all files when the game is opened.
//Assets are made public for ease of use.


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.tb.gameworld.GameWorld.GameMode;

public class AssetLoader {
	public static BitmapFont font, smallfont, smallestfont, settingsfont, settingssmall;
	private static Preferences prefs;
	public static Texture timer, colorline, muteon, muteoff, settings, achievements, leaderboard, email, facebook, star,
	twitter, back, back2, link;
	public static TextureRegion muteonRegion, muteoffRegion, settingsRegion, leaderboardRegion;
	public static Sound correct, wrong, menu;
	public static FreeTypeFontGenerator generator, settingsgenerator;
	public static FreeTypeFontParameter parameter, settingsparameter;
	
	public static void load(){
		//Generate fonts in 2 sizes
		generator = new FreeTypeFontGenerator(Gdx.files.internal("data/font.ttf"));
		parameter = new FreeTypeFontParameter();
		settingsgenerator = new FreeTypeFontGenerator(Gdx.files.internal("data/settingstext.otf"));
		settingsparameter = new FreeTypeFontParameter();
		parameter.flip = true;
		settingsparameter.flip = true;
		parameter.size = 136;
		parameter.characters = "qwertyuiopasdfghjklzxcvbnm1234567890:!'";
		settingsparameter.size = 136;
		generator.scaleForPixelHeight(Gdx.graphics.getWidth());
		font = generator.generateFont(parameter);
		font.setScale(.125f,.125f);
		smallfont = generator.generateFont(parameter);
		smallfont.setScale(.075f,.075f);
		smallestfont = generator.generateFont(parameter);
		smallestfont.setScale(.06f, .06f);
		settingsfont = settingsgenerator.generateFont(settingsparameter);
		settingsfont.setScale(.075f, .075f);
		settingsfont.setColor(Color.BLACK);
		settingssmall = settingsgenerator.generateFont(settingsparameter);
		settingssmall.setScale(.05f,.05f);
		settingssmall.setColor(Color.BLACK);
		
		link = new Texture(Gdx.files.internal("data/link.png"));
		back = new Texture(Gdx.files.internal("data/back.png"));
		back2 = new Texture(Gdx.files.internal("data/back2.png"));
		twitter = new Texture(Gdx.files.internal("data/twitter.png"));
		star = new Texture(Gdx.files.internal("data/star.jpg"));
		email = new Texture(Gdx.files.internal("data/email.png"));
		facebook = new Texture(Gdx.files.internal("data/fb.png"));
		timer = new Texture(Gdx.files.internal("data/timer.png"));
		colorline = new Texture(Gdx.files.internal("data/colorline.png"));
		muteon = new Texture(Gdx.files.internal("data/muteon.png"));
		muteonRegion = new TextureRegion(muteon);
		muteonRegion.flip(false, true);
		muteoff = new Texture(Gdx.files.internal("data/muteoff.png"));
		muteoffRegion = new TextureRegion(muteoff);
		muteoffRegion.flip(false,true);
		settings = new Texture(Gdx.files.internal("data/gear.jpg"));
		settingsRegion = new TextureRegion(settings, -20, -20);
		leaderboard = new Texture(Gdx.files.internal("data/leaderboard.png"));
		leaderboardRegion = new TextureRegion(leaderboard);
		leaderboardRegion.flip(false,true);
		achievements = new Texture(Gdx.files.internal("data/achievements.png"));
		correct = Gdx.audio.newSound(Gdx.files.internal("data/correct.wav"));
		wrong = Gdx.audio.newSound(Gdx.files.internal("data/wrong.wav"));
		menu = Gdx.audio.newSound(Gdx.files.internal("data/menu.wav"));
		prefs = Gdx.app.getPreferences("Color Game");
		if(!prefs.contains("highclassic1")){
			prefs.putInteger("highclassic1", 0);
			prefs.putInteger("highclassic2", 0);
			prefs.putInteger("highclassic3", 0);
			prefs.putInteger("highfours1", 0);
			prefs.putInteger("highfours2", 0);
			prefs.putInteger("highfours3", 0);
			prefs.putInteger("highblitz1", 0);
			prefs.putInteger("highblitz2", 0);
			prefs.putInteger("highblitz3", 0);
			prefs.putInteger("highwild1", 0);
			prefs.putInteger("highwild2", 0);
			prefs.putInteger("highwild3", 0);
			prefs.putInteger("plays", 0);
		}
	}
	
	public static void dispose(){
		font.dispose();
		smallfont.dispose();
		smallestfont.dispose();
		settingsfont.dispose();
		timer.dispose();
		back2.dispose();
		correct.dispose();
		leaderboard.dispose();
		achievements.dispose();
		settings.dispose();
		muteon.dispose();
		muteoff.dispose();
		wrong.dispose();
		menu.dispose();
		generator.dispose();
		colorline.dispose();
	}
	
	public static boolean isForty(){
		if(prefs.contains("plays"))
			if(prefs.getInteger("plays") ==40)
				return true;
		return false;
	}
	
	public static void incrementPlays(){
		prefs.putInteger("plays", prefs.getInteger("plays")+1);
		prefs.flush();
	}
	
	public static void ask(){
		prefs.putBoolean("wasAsked", true);
	}
	public static int getHighScore(GameMode mode, float difficulty){
		if(mode == GameMode.NORMAL)	{
			if(difficulty == 136)
				return prefs.getInteger("highclassic1");
			if(difficulty == 170)
				return prefs.getInteger("highclassic2");
			if(difficulty == 227)
				return prefs.getInteger("highclassic3");
			else return -1;
		}
		
		if(mode == GameMode.FOURBYFOUR){
			if(difficulty == 136)
				return prefs.getInteger("highfours1");
			if(difficulty == 170)
				return prefs.getInteger("highfours2");
			if(difficulty == 227)
				return prefs.getInteger("highfours3");
			else return -1;
		}
		
		if(mode == GameMode.BLITZ){
			if(difficulty == 4.5f)
				return prefs.getInteger("highblitz1");
			if(difficulty == 6.8f)
				return prefs.getInteger("highblitz2");
			if(difficulty == 13.6f)
				return prefs.getInteger("highblitz3");
			else return -1;
		}
		
		if(mode == GameMode.WILD){
			if(difficulty == 136)
				return prefs.getInteger("highwild1");
			if(difficulty == 170)
				return prefs.getInteger("highwild2");
			if(difficulty == 227)
				return prefs.getInteger("highwild3");
			else return -1;
		}
		else return -1;
	}
	
	//Based on current mode/difficulty, update the highscore.
	public static void setHighScore(GameMode mode, float difficulty, int val){
		if(mode == GameMode.NORMAL){
			if(difficulty == 136){
				prefs.putInteger("highclassic1", val);
			}
			if(difficulty == 170)
				prefs.putInteger("highclassic2", val);
			if(difficulty == 227)
				prefs.putInteger("highclassic3", val);
		} 
		if(mode == GameMode.FOURBYFOUR){
			if(difficulty == 136)
				prefs.putInteger("highfours1", val);
			if(difficulty == 170)
				prefs.putInteger("highfours2", val);
			if(difficulty == 227)
				prefs.putInteger("highfours3", val);
		}
		if(mode == GameMode.BLITZ){
			if(difficulty == 4.5f)
				prefs.putInteger("highblitz1", val);
			if(difficulty == 6.8f)
				prefs.putInteger("highblitz2", val);
			if(difficulty == 13.6f)
				prefs.putInteger("highblitz3", val);
		}
		if(mode == GameMode.WILD){
			if(difficulty == 136)
				prefs.putInteger("highwild1", val);
			if(difficulty == 170)
				prefs.putInteger("highwild2", val);
			if(difficulty == 227)
				prefs.putInteger("highwild3", val);
		}
		prefs.flush();
	}
}
