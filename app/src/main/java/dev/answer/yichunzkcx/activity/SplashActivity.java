package dev.answer.yichunzkcx.activity;

import android.os.Bundle;
import dev.answer.yichunzkcx.R;

public class SplashActivity extends BaseActivity{
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // TODO: Implement this method
        
    }
    
  @Override
  public boolean isHasToolbar() {
    // TODO: Implement this method
    return true;
  }

  @Override
  public String getActivityName() {
    // TODO: Implement this method
    return "隐私协议";
  }

  @Override
  public int getActivityLayout() {
    // TODO: Implement this method
    return R.layout.activity_splash;
  }
}
