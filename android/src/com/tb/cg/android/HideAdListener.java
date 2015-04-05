package com.tb.cg.android;

import com.tb.ui.ListenerManager.ListenerType;

public class HideAdListener implements com.tb.ui.Listener {
    AndroidLauncher base;
 
    @Override
    public void call() {
        this.base.hideAd();
    }
 
    public void setBase(AndroidLauncher base) {
        this.base = base;
    }
 
    @Override
    public ListenerType type() {
        return ListenerType.HIDEAD;
    }
}
