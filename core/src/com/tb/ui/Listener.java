package com.tb.ui;

//MOBPUB CODE
import com.tb.ui.ListenerManager.ListenerType;
    
public interface Listener {
    public abstract void call();
    public abstract ListenerType type();
}