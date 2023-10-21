package dev.answer.yichunzkcx.activity;

import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import dev.answer.yichunzkcx.R;
import dev.answer.yichunzkcx.util.ProtocolUtil;

public class SplashActivity extends BaseActivity{
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MaterialTextView text = findViewById(R.id.splash_protocol_text);
        String context = ProtocolUtil.readTextFromAssets(this,"/protocol/zh/PrivacyPolicy.txt");
        text.setText(ProtocolUtil.parseProtocol(context, this));
        
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
