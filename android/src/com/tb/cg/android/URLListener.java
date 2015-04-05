package com.tb.cg.android;

import com.tb.ui.ListenerManager.ListenerType;

public class URLListener implements com.tb.ui.Listener {

	AndroidLauncher base;
	String url;
	
	@Override
	public void call() {
		this.base.openURL(url);
	}

	public void setBase(AndroidLauncher base) {
        this.base = base;
    }
	
	@Override
	public ListenerType type() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setURL(String url){
		this.url = url;
	}

}
