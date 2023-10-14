package dev.answer.yichunzkcx;

import android.content.Context;
import dev.answer.yichunzkcx.clash.GlobalApplication;

public class MainApplication extends GlobalApplication {
    
    private static Context mContext;
    
    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: Implement this method
        mContext = getApplicationContext();
    }
    
    @Override
    protected void attachBaseContext(Context arg0) {
        super.attachBaseContext(arg0);
        // TODO: Implement this method
    }
    
    public static Context getContext() {
    	return mContext;
    }
    
}
