package com.tb.cg;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.coregraphics.CGSize;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.bindings.mopub.MPAdView;
import org.robovm.bindings.mopub.MPAdViewDelegateAdapter;
import org.robovm.bindings.mopub.MPConstants;
import org.robovm.bindings.mopub.sample.MPAdViewController;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
 
public class IOSLauncher extends IOSApplication.Delegate {
    private static CGSize AD_SIZE;
    private static String AD_ID;
    private boolean adLoaded = false;
    private UIViewController rootViewController;
    private MPAdViewController adViewController;
    private MPAdView banner;
 
    @Override
    protected IOSApplication createApplication() {
        // Create the listeners
        ShowAdListener show = new ShowAdListener();
        HideAdListener hide = new HideAdListener();
        show.setBase(this);
        hide.setBase(this);
 
        // Create the LibGDX app and add the listeners
        ColorGame app = new ColorGame(new IOSActionResolver());
        app.addListener(show);
        app.addListener(hide);
 
        // Run the app
        IOSApplicationConfiguration config = new IOSApplicationConfiguration();
        config.orientationLandscape = false; // Change to suit you!
        config.orientationPortrait = true; // Change to suit you!
        return new IOSApplication(app, config);
    }
 
    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
 
    public boolean didFinishLaunching (UIApplication application, NSDictionary launchOptions) {
        super.didFinishLaunching(application, launchOptions);
        AD_SIZE = MPConstants.MOPUB_BANNER_SIZE;
        AD_ID = "2f2b732b8b3344978ca81c03607bad6b"; // Your MoPub Ad ID
 
        // We need a view controller to see ads.
        rootViewController = application.getKeyWindow().getRootViewController();
 
        // Create a MoPub ad. In this case a banner, but you can make it any size you want.
        banner = new MPAdView(AD_ID, AD_SIZE);
        // Let’s calculate our banner size. We need to do this because the resolution of a retina and normal device is different.
        float bannerWidth = (float) UIScreen.getMainScreen().getBounds().size().width();
        float bannerHeight = (float) (bannerWidth / AD_SIZE.width() * AD_SIZE.height());
 
        // Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
        // Position banner on the top.
        banner.setFrame(new CGRect((UIScreen.getMainScreen().getBounds().size().width()/2)-AD_SIZE.width()/2, 0, bannerWidth, bannerHeight));
        // Position banner on the bottom.
        // banner.setFrame(new CGRect(0, UIScreen.getMainScreen().getBounds().size().height() – bannerHeight, bannerWidth, bannerHeight));
        // Let’s color the background for testing, so we can see if it is positioned correctly, even if no ad is loaded yet.
//      banner.setBackgroundColor(new UIColor(0, 0, 1, 1)); // Remove this after testing.
        // We use our custom ad view controller to notify for orientation changes.
        adViewController = new MPAdViewController(banner);
 
        // The delegate for the banner. It is required to override getViewController() to get ads.
        MPAdViewDelegateAdapter bannerDelegate = new MPAdViewDelegateAdapter() {
            @Override
            public UIViewController getViewController () {
                return adViewController;
            }
        };
 
        banner.setDelegate(bannerDelegate);
        // Add banner to our view controller.
        adViewController.getView().addSubview(banner);
 
        // We add the ad view controller to our root view controller.
        rootViewController.addChildViewController(adViewController);
        rootViewController.getView().addSubview(adViewController.getView());
 
        if(!adLoaded) {
            banner.loadAd();
        adLoaded = true;
        }
 
        // Create a standard UIWindow at screen size, add the view controller and show it.
        application.getKeyWindow().setRootViewController(rootViewController);
        application.getKeyWindow().addSubview(rootViewController.getView());
        application.getKeyWindow().makeKeyAndVisible();
        return false;
    }
 
    // Ads
    public void showAd() {
        System.out.println("Showing Ad");
        if(!adLoaded) {
            banner.loadAd();
            adLoaded = true;
        }
        double bannerWidth = UIScreen.getMainScreen().getBounds().size().width();
        double bannerHeight = (bannerWidth / AD_SIZE.width() * AD_SIZE.height());
        // Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
        // Position banner on the top.
        banner.setFrame(new CGRect(
            ((UIScreen.getMainScreen().getBounds().size().width()/2) - AD_SIZE.width() / 2),
            0,
            bannerWidth,
            bannerHeight)
    );
    }
 
    public void hideAd() {
        System.out.println("Hiding Ad");
        double bannerWidth = UIScreen.getMainScreen().getBounds().size().width();
        double bannerHeight = bannerWidth / AD_SIZE.width() * AD_SIZE.height();
 
        // Let’s set the frame (bounds) of our banner view. Remember on iOS view coordinates have their origin top-left.
        // Position banner above the top.
        banner.setFrame(new CGRect(0, -bannerHeight, bannerWidth, bannerHeight));
    }
}

class IOSActionResolver implements com.tb.cghelpers.ActionResolver{

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboardGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}
	
}