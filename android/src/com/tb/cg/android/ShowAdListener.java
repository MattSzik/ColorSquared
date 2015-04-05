package com.tb.cg.android;

import com.tb.ui.ListenerManager.ListenerType;

public class ShowAdListener implements com.tb.ui.Listener {
    AndroidLauncher base;
 
    @Override
    public void call() {
        this.base.showAd();
    }
 
    public void setBase(AndroidLauncher base) {
        this.base = base;
    }
 
    @Override
    public ListenerType type() {
        return ListenerType.SHOWAD;
    }
}