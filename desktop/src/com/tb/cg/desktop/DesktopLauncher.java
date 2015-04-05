package com.tb.cg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tb.cg.ColorGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ColorSquared";
		config.width = 320;
		config.height = 480;
		new LwjglApplication(new ColorGame(new DesktopActionResolver()), config);
	}
	


}

class DesktopActionResolver implements com.tb.cghelpers.ActionResolver{


	@Override
	public boolean getSignedInGPGS() {
		System.out.println("getSignedin");
		return false;
	}

	@Override
	public void loginGPGS() {
		System.out.println("login");
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub
		System.out.println("submitscore");
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		System.out.println("Achievement unlock");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboardGPGS() {
		System.out.println("Get leaderboard");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievementsGPGS() {
		System.out.println("Get achievements.");
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logoutGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCurrentLB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentLB(String lb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void askForRating() {
		// TODO Auto-generated method stub
		
	}
}

