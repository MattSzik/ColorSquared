package com.tb.cg;

import com.tb.ui.ListenerManager.ListenerType;

public class ShowAdListener implements com.tb.ui.Listener {
    IOSLauncher base;
 
    @Override
    public void call() {
        this.base.showAd();
    }
 
    public void setBase(IOSLauncher base) {
        this.base = base;
    }
 
    @Override
    public ListenerType type() {
        return ListenerType.SHOWAD;
    }
}