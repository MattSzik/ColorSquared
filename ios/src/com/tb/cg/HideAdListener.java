package com.tb.cg;

import com.tb.ui.ListenerManager.ListenerType;

public class HideAdListener implements com.tb.ui.Listener {
    IOSLauncher base;
 
    @Override
    public void call() {
        this.base.hideAd();
    }
 
    public void setBase(IOSLauncher base) {
        this.base = base;
    }
 
    @Override
    public ListenerType type() {
        return ListenerType.HIDEAD;
    }
}