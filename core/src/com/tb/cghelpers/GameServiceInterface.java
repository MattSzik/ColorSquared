package com.tb.cghelpers;

public interface GameServiceInterface {
	 
    public void login();
    public void logOut();
    public void rateGame();
    public boolean isSignedIn();

    public void submitScore(int score);
    public void unlockAchievements(String achievementID);

    //gets the scores/achievements and displays them threw googles default widget
    public void showScores();
    public void showAchievements();
}