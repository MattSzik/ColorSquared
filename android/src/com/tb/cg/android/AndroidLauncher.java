package com.tb.cg.android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.mopub.mobileads.MoPubView;
import com.tb.cg.ColorGame;
import com.tb.cg.android.GameHelper.GameHelperListener;


public class AndroidLauncher extends AndroidApplication implements com.tb.cghelpers.ActionResolver {
    private MoPubView mAdView;
    private ColorGame app;
    private GameHelper gameHelper;
    private boolean adLoaded = false;
    private boolean adShown = false;
    private String currentLB;
    public AlertDialog.Builder builder;
 
    @Override
    protected void onCreate (Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);
        gameHelper.setMaxAutoSignInAttempts(1);
        GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
        {
        @Override
        public void onSignInSucceeded()
        {
       
        }

        @Override
        public void onSignInFailed()
        {
       
        }
       
        };
        app = new ColorGame(this);

        // Setup window
        RelativeLayout layout = new RelativeLayout(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
 
        // Setup listeners
        ShowAdListener show = new ShowAdListener();
        HideAdListener hide = new HideAdListener();
        show.setBase(this);
        hide.setBase(this);
        app.addListener(show);
        app.addListener(hide);
 
        // Setup the 2 views. 1 for content, and 1 for ads
        //Old view spot
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        View appView = initializeForView(app, config);
        mAdView = new MoPubView(this);
        mAdView.setAdUnitId("2f2b732b8b3344978ca81c03607bad6b"); // Your MoPub Ad Unit ID
        
        // Setup the layout
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,         RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(appView);
        layout.addView(mAdView, adParams);
        // Set the content view to the layout
        gameHelper.setup(gameHelperListener);
        setContentView(layout);
        builder = new AlertDialog.Builder(this);
    }
 
    @Override
    public void onDestroy() {
        mAdView.destroy();
        super.onDestroy();
    }
    
	@Override
	public void onStart(){
		super.onStart();
		gameHelper.onStart(this);
	}

	@Override
	public void onStop(){
		super.onStop();
		gameHelper.onStop();
	}
 
    public void showAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(!adLoaded) {
                    mAdView.loadAd();
                    adLoaded = true;
                }
                if(!adShown) {
                    mAdView.setVisibility(View.VISIBLE);
                    adShown = true;
                }
            }
        });
    }
 
    public void hideAd() {
        if(adShown) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdView.setVisibility(View.GONE);
                    adShown = false;
                }
            });
        }
    }
    
    public void openURL(String URL){
    	Gdx.net.openURI(URL);
    	//TODO: Open url in Android browser.
    }

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return gameHelper.isSignedIn();
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent data) {
		super.onActivityResult(request, response, data);
		gameHelper.onActivityResult(request, response, data);
	}

	@Override
	public void loginGPGS() {
		try {
			runOnUiThread(new Runnable(){
				public void run() {
					gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception ex) {
		}
	}
	
	@Override
	public void logoutGPGS(){
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		if(gameHelper.isSignedIn())
		Games.Leaderboards.submitScore(gameHelper.getApiClient(), getCurrentLB(), score);
		// TODO Auto-generated method stub //CgkI7__Q5dUUEAIQCA         574wJUXEAIQBw
	}
	@Override
	public String getCurrentLB(){
		return currentLB;
	}
	@Override
	public void setCurrentLB(String lb){
		currentLB = lb;
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		if(gameHelper.isSignedIn())
		Games.Achievements.unlock(gameHelper.getApiClient(), achievementId);
	}

	@Override
	public void getLeaderboardGPGS() {
		if (gameHelper.isSignedIn()) {
		    startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()),100);
		  }
		  else if (!gameHelper.isConnecting()) {
		    loginGPGS();
		  }
	}

	
	@Override
	public void getAchievementsGPGS() {
		if (gameHelper.isSignedIn()) {
		    startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), 101);
		  }
		  else if (!gameHelper.isConnecting()) {
		    loginGPGS();
		  }
	}
	
	@Override
	public void askForRating(){
		
		this.runOnUiThread(new Runnable(){
		public void run(){
		
		builder.setMessage("Like our game? Leave us a review!")
			   .setTitle("Color Squared")
			   .setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	               // User clicked OK button
	        	   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tb.cg.android&hl=en"));
	        	   startActivity(browserIntent);
	           }
	       })
	       .setNegativeButton("No thank you.", new DialogInterface.OnClickListener(){
	    	  public void onClick(DialogInterface dialog, int id){
	    		  //Never ask again.
	    	  }
	       });
		AlertDialog dialog = builder.create();
		dialog.show();
		}
	
	});
}
}
