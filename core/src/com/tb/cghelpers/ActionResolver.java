package com.tb.cghelpers;

public interface ActionResolver {
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void logoutGPGS();
	  public void submitScoreGPGS(int score);
	  public void unlockAchievementGPGS(String achievementId);
	  public void getLeaderboardGPGS();
	  public void getAchievementsGPGS();
	  public String getCurrentLB();
	  public void setCurrentLB(String lb);
	  public void askForRating();
	  
	}